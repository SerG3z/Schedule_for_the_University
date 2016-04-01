package com.sample.drawer.adapter;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.drawer.R;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by serg on 02.04.16.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsObjectHolder> {

    private VKList<VKApiPost> vkApiPosts = new VKList<>();

    public NewsRecyclerViewAdapter(VKList<VKApiPost> vkApiPosts) {
        this.vkApiPosts.addAll(vkApiPosts);
    }


    @Override
    public NewsObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsObjectHolder holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        holder = new NewsObjectHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsObjectHolder holder, int position) {
//        holder.imageGroupNews.setText(vkApiPosts.get(position).getDeadline());
        holder.titleNews.setText(vkApiPosts.get(position).post_type);
        holder.timeNews.setText(String.valueOf(vkApiPosts.get(position).date));
        holder.textNews.setText(vkApiPosts.get(position).text);
//        holder.imageNews.setImageBitmap(vkApiPosts.get(position).);
    }

    @Override
    public int getItemCount() {
        return vkApiPosts.size();
    }

    public class NewsObjectHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image_group_item_news)
        ImageView imageGroupNews;

        @Bind(R.id.title_item_news)
        TextView titleNews;

        @Bind(R.id.time_item_news)
        TextView timeNews;

        @Bind(R.id.text_item_news)
        TextView textNews;

        @Bind(R.id.image_item_news)
        ImageView imageNews;


        public NewsObjectHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}
