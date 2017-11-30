package xyz.no21.appcommon.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import xyz.no21.appcommon.base.BaseFragment;

/**
 * Created by lin on 2017/6/29.
 * Email:L437943145@gmail.com
 * <p>
 * desc:
 */

public class CommonPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private List<PagerInfo> fragments = new ArrayList<>();


    public CommonPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addPager(Class<? extends BaseFragment> fragment, @Nullable Bundle argment, @Nullable String title) {
        fragments.add(new PagerInfo(fragment, argment, title));
    }


    @Override
    public Fragment getItem(int position) {
        try {
            BaseFragment fragment = fragments.get(position).fragment.newInstance();
            fragment.setArguments(fragments.get(position).argment);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).title;
    }


    private static class PagerInfo {
        Class<? extends BaseFragment> fragment;
        Bundle argment;
        String title;

        PagerInfo(Class<? extends BaseFragment> fragment, Bundle argment, String title) {
            this.fragment = fragment;
            this.argment = argment;
            this.title = title;
        }
    }
}
