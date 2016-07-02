package iit.android.settings;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class CustomPageAdapter extends FragmentPagerAdapter {
	  private List<Fragment> fragments;
	  public CustomPageAdapter(FragmentManager fm, List<Fragment> fragments) {
	    super(fm);
	    this.fragments = fragments;
	  }

	  @Override 
	  public Fragment getItem(int position) {
	    return this.fragments.get(position);
	  }

	  @Override
	  public int getCount() {
	    return this.fragments.size();

	  }

	}

