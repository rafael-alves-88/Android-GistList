package com.rafael.alexandre.alves.gistlist.ui.detail.dialogFragments.content;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rafael.alexandre.alves.gistlist.R;
import com.rafael.alexandre.alves.gistlist.model.Gist;
import com.rafael.alexandre.alves.gistlist.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentFragment extends DialogFragment {

    @Bind(R.id.tvContent) TextView tvContent;

    public ContentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.gist_detail_content_fragment, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        if (bundle != null) {
            setContent(Utils.checkText(bundle.getString(Gist.CONTENT)));
        }

        return view;
    }

    private void setContent(String content) {
        tvContent.setText(content);
    }

    @OnClick(R.id.btnClose)
    public void btnCloseClick() {
        this.dismiss();
    }
}
