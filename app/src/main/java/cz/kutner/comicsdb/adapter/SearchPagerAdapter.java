package cz.kutner.comicsdb.adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.fragment.AuthorFragment;
import cz.kutner.comicsdb.fragment.ComicsListFragment;
import cz.kutner.comicsdb.fragment.SeriesFragment;
import timber.log.Timber;

public class SearchPagerAdapter extends FragmentPagerAdapter {

    Intent intent;

    public SearchPagerAdapter(FragmentManager fm, Intent intent) {
        super(fm);
        this.intent = intent;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Timber.i(String.valueOf(position));
        switch (position) {
            case 0:
                fragment = ComicsListFragment.newInstance();
                break;
            case 1:
                fragment = SeriesFragment.newInstance();
                break;
            case 2:
                fragment = AuthorFragment.newInstance();
                break;
        }
        fragment.setArguments(intent.getExtras());
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String name = null;
        switch (position) {
            case 0:
                 name = ComicsDBApplication.getContext().getResources().getString(R.string.comics);
                break;
            case 1:
                name = ComicsDBApplication.getContext().getResources().getString(R.string.series);
                break;
            case 2:
                name = ComicsDBApplication.getContext().getResources().getString(R.string.author);
                break;
        }
        return name;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
