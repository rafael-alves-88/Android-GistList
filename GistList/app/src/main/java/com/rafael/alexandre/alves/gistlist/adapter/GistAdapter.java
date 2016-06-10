package com.rafael.alexandre.alves.gistlist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.rafael.alexandre.alves.gistlist.R;
import com.rafael.alexandre.alves.gistlist.model.Files;
import com.rafael.alexandre.alves.gistlist.model.Gist;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class GistAdapter extends RecyclerView.Adapter<GistAdapter.GistAdapterViewHolder> {

    private List<Gist> mGistList;
    private Context mContext;

    private final OnGistClickListener listener;

    public interface OnGistClickListener {
        void onGistClick(Gist item);
    }

    public GistAdapter(Context context, List<Gist> gistList, OnGistClickListener listener) {
        this.mContext = context;
        this.mGistList = gistList;
        this.listener = listener;
    }

    @Override
    public GistAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gist_list_row, parent, false);

        return new GistAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GistAdapterViewHolder holder, int position) {
        Gist gist = mGistList.get(position);

        if (gist.owner != null && gist.owner.avatarUrl != null) {
            Picasso.with(mContext).load(gist.owner.avatarUrl).into(holder.ivAvatar);
            holder.tvGistUser.setText(gist.owner.login);
        } else {
            Picasso.with(mContext).load(R.mipmap.ic_launcher).into(holder.ivAvatar);
            holder.tvGistUser.setText("N/A");
        }

        if (gist.files != null) {
            try {
                Map.Entry<String, Files> mapEntry = gist.files.entrySet().iterator().next();
                Files files = mapEntry.getValue();

                String type;
                if (files.type != null && files.type.length() > 0) {
                    type = files.type;
                } else {
                    type = "N/A";
                }
                holder.tvGistType.setText(type);

                String language;
                if (files.language != null && files.language.length() > 0) {
                    language = files.language;
                } else {
                    language = "N/A";
                }
                holder.tvGistLanguage.setText(language);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        holder.bind(mGistList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mGistList.size();
    }

    public static class GistAdapterViewHolder extends RecyclerView.ViewHolder {
        public RoundedImageView ivAvatar;
        public TextView tvGistUser;
        public TextView tvGistType;
        public TextView tvGistLanguage;

        public GistAdapterViewHolder(View itemView) {
            super(itemView);

            this.ivAvatar = (RoundedImageView) itemView.findViewById(R.id.ivAvatar);
            this.tvGistUser = (TextView) itemView.findViewById(R.id.tvGistUser);
            this.tvGistType = (TextView) itemView.findViewById(R.id.tvGistType);
            this.tvGistLanguage = (TextView) itemView.findViewById(R.id.tvGistLanguage);
        }

        public void bind(final Gist item, final OnGistClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onGistClick(item);
                }
            });
        }
    }
}
