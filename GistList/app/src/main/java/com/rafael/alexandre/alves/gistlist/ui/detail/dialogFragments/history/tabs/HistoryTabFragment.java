package com.rafael.alexandre.alves.gistlist.ui.detail.dialogFragments.history.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rafael.alexandre.alves.gistlist.R;
import com.rafael.alexandre.alves.gistlist.model.Gist;
import com.rafael.alexandre.alves.gistlist.model.History;
import com.rafael.alexandre.alves.gistlist.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HistoryTabFragment extends Fragment {

    @Bind(R.id.tvLogin) TextView tvLogin;
    @Bind(R.id.tvVersion) TextView tvVersion;
    @Bind(R.id.tvCommittedAt) TextView tvCommittedAt;
    @Bind(R.id.tvTotalChanges) TextView tvTotalChanges;
    @Bind(R.id.tvAdditions) TextView tvAdditions;
    @Bind(R.id.tvDeletions) TextView tvDeletions;

    public HistoryTabFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.gist_detail_history_tab_fragment, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            History history = (History) bundle.getSerializable(Gist.HISTORY);
            setHistory(history);
        }

        return view;
    }

    private void setHistory(History history) {
        String login = "";
        if (history.user != null) {
            login = history.user.login;
        }
        tvLogin.setText(Utils.checkText(login));
        tvVersion.setText(Utils.checkText(history.version));
        tvCommittedAt.setText(Utils.checkText(history.committed_at));
        tvTotalChanges.setText(String.valueOf(history.change_status.total));
        tvAdditions.setText(String.valueOf(history.change_status.additions));
        tvDeletions.setText(String.valueOf(history.change_status.deletions));
    }
}
