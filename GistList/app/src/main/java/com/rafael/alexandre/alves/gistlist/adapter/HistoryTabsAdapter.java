package com.rafael.alexandre.alves.gistlist.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rafael.alexandre.alves.gistlist.model.Gist;
import com.rafael.alexandre.alves.gistlist.model.History;
import com.rafael.alexandre.alves.gistlist.ui.detail.dialogFragments.history.tabs.HistoryTabFragment;

import java.util.List;

public class HistoryTabsAdapter extends FragmentStatePagerAdapter {

    private List<History> mHistoryListList;

    public HistoryTabsAdapter(FragmentManager fm, List<History> historyList) {
        super(fm);
        this.mHistoryListList = historyList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new HistoryTabFragment();

        Bundle args = new Bundle();
        args.putSerializable(Gist.HISTORY, mHistoryListList.get(position));
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return mHistoryListList.size();
    }
}