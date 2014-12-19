package me.lexjoy.widget.pager_tabhost;

import android.support.v4.app.Fragment;

public interface FragmentFactory {

  Fragment getFragment(int tabId);

}
