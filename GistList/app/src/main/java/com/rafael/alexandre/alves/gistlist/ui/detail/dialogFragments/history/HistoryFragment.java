package com.rafael.alexandre.alves.gistlist.ui.detail.dialogFragments.history;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rafael.alexandre.alves.gistlist.R;
import com.rafael.alexandre.alves.gistlist.adapter.HistoryTabsAdapter;
import com.rafael.alexandre.alves.gistlist.model.Gist;
import com.rafael.alexandre.alves.gistlist.model.History;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryFragment extends DialogFragment {

    @Bind(R.id.vpHistory) ViewPager vpHistory;
    @Bind(R.id.tlHistory) TabLayout tlHistory;

    public HistoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.gist_detail_history_fragment, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            List<History> historyList = (List<History>) bundle.get(Gist.HISTORY_LIST);
            setHistory(historyList);
        }

        return view;
    }

    private void setHistory(List<History> historyList) {
        if (historyList != null && historyList.size() > 0) {
            int tabCount = 0;
            for (History history : historyList) {
                tabCount = tabCount + 1;
                tlHistory.addTab(tlHistory.newTab().setText(String.valueOf(tabCount)));
            }
            tlHistory.setTabGravity(TabLayout.GRAVITY_FILL);

            HistoryTabsAdapter tabsAdapter = new HistoryTabsAdapter(getChildFragmentManager(), historyList);
            vpHistory.setAdapter(tabsAdapter);
            vpHistory.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlHistory));

            tlHistory.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    vpHistory.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            vpHistory.setOffscreenPageLimit(historyList.size());
        }
    }

    @OnClick(R.id.btnClose)
    public void btnCloseClick() {
        this.dismiss();
    }
}
