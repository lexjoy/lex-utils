package me.lexjoy.widget.pager_tabhost;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.SparseArray;
import android.view.View;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import me.lexjoy.utils._assert.AssertUtils;
import me.lexjoy.view.ViewFinder;

public class PagerTabHostBuilder {

  /**
   * <pre>
   * Use
   *  android.R.id.tabhost    as tabhost id,
   *  android.R.id.tabcontent as invisible tabcontent id
   * </pre>
   * @see FragmentTabHost
   * @see ViewPager
   * @see android.R.id.tabhost
   * @see android.R.id.tabcontent
   */
  public PagerTabHostBuilder(Context context, int pagerId, FragmentManager fragmentManager, ViewFinder viewFinder) {
    // assert context != null;
    // assert fragmentManager != null;
    // assert viewFinder != null;
    ViewPager       pager   = viewFinder.findView(pagerId, ViewPager.class);
    FragmentTabHost tabHost = viewFinder.findView(android.R.id.tabhost , FragmentTabHost.class);

    AssertUtils.checkNotEmpty(pager  , "pager not found"  );
    AssertUtils.checkNotEmpty(tabHost, "tabHost not found");

    tabHost.setup(context, fragmentManager, android.R.id.tabcontent);
    this.tabHost = tabHost;
    this.pager   = pager;
    this.fragmentManager = fragmentManager;
    this.context = context;
    this.tabTitleMap = new SparseArray<>();
  }

  public PagerTabHostBuilder addTab(int tabId, int titleId) {
    return this.addTab(tabId, titleId, -1);
  }

  public PagerTabHostBuilder addTab(int tabId, int titleId, int iconId) {
    String tabTitle = this.context.getString(titleId);
    TabSpec tabSpec = this.tabHost.newTabSpec(tabTitle);

    if (this.hasCustomTabView()) {
      tabSpec.setIndicator(this.buildCustomTabView(tabId, titleId));
    } else if (iconId == -1) {
      tabSpec.setIndicator(tabTitle, this.context.getResources().getDrawable(iconId));
    } else {
      tabSpec.setIndicator(tabTitle);
    }
    this.tabTitleMap.put(tabId, tabTitle);
    this.tabHost.addTab(tabSpec, Fragment.class, null);
    return this;
  }

  public PagerTabHostBuilder setCurrentTab(int tabId) {
    int tabIndex = this.tabTitleMap.indexOfKey(tabId);
    this.tabHost.setCurrentTab(tabIndex);
    this.pager.setCurrentItem(tabIndex, false);
    return this;
  }

  public void Build(final FragmentFactory fragmentFactory) {
    this.tabHost.setOnTabChangedListener (new TabChangeHandler (this.pager, this.tabTitleMap));
    this.pager.setOnPageChangeListener   (new PageChangeHandler(this.tabHost));
    this.pager.setAdapter(new PageAdapter(this.fragmentManager, this.tabTitleMap, fragmentFactory));
  }

  protected View buildCustomTabView(int tabId, int titleId) {
    return null;
  }

  protected boolean hasCustomTabView() {
    return false;
  }

  protected Context getContext() {
    return this.context;
  }

  private FragmentTabHost tabHost;
  private ViewPager pager;
  private FragmentManager fragmentManager;
  private Context context;
  private SparseArray<String> tabTitleMap;

  private static class TabChangeHandler implements OnTabChangeListener {
    public TabChangeHandler(ViewPager pager, SparseArray<String> titleMap) {
      this.pager    = pager;
      this.titleMap = titleMap;
    }

    @Override
    public void onTabChanged(String tabId) {
      this.pager.setCurrentItem(this.titleMap.indexOfValue(tabId));
    }

    private SparseArray<String> titleMap;
    private ViewPager pager;
  }

  private static class PageChangeHandler implements OnPageChangeListener {
    public PageChangeHandler(FragmentTabHost tabHost) {
      this.tabHost = tabHost;
    }

    @Override
    public void onPageSelected(int position) {
      tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    private FragmentTabHost tabHost;
  };

  private static class PageAdapter extends FragmentPagerAdapter {
    public PageAdapter(FragmentManager fragmentManager,
        SparseArray<String> titleMap, FragmentFactory fragmentFactory) {
      super(fragmentManager);
      this.titleMap        = titleMap;
      this.fragmentFactory = fragmentFactory;
    }

    @Override
    public Fragment getItem(int position) {
      return this.fragmentFactory.getFragment(this.titleMap.keyAt(position));
    }

    @Override
    public int getCount() {
      return this.titleMap.size();
    }

    private SparseArray<String> titleMap;
    private FragmentFactory fragmentFactory;
  };

}
