package com.sample.drawer.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sample.drawer.R;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by serg on 02.04.16.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsObjectHolder> {

    private VKList<VKApiPost> vkApiPosts = new VKList<>();
    private Context context;
    private String nameGroup;
    private String urlIconGroup;
    private static final int TYPE_POST = 0;
    private static final int TYPE_REPOST = 1;

    public NewsRecyclerViewAdapter(VKList<VKApiPost> vkApiPosts, Context context, String nameGroup, String urlIconGroup) {
        this.vkApiPosts.addAll(vkApiPosts);
        this.context = context;
        this.nameGroup = nameGroup;
        this.urlIconGroup = urlIconGroup;
    }

    @Override
    public int getItemViewType(final int position) {
        if(vkApiPosts.get(position).copy_history.size() == 0) {
            return TYPE_POST;
        } else {
            return TYPE_REPOST;
        }
    }

    @Override
    public NewsObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsObjectHolder holder;
        if (viewType == TYPE_POST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            holder = new NewsObjectHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_repost, parent, false);
            holder = new NewsRepostViewHolder(view);
        }

        return holder;
    }

    private void showPost(NewsObjectHolder holder, VKApiPost post) {
        NewsObjectHolder newsObjectHolder = ((NewsObjectHolder) holder);
        Picasso.with(context)
                .load(urlIconGroup)
                .into(newsObjectHolder.imageGroupNews);

        newsObjectHolder.titleNews.setText(nameGroup);
        String date = timeFromUTCSecs(context, post.date);
        newsObjectHolder.timeNews.setText(date);
        String textPost = post.text;
        newsObjectHolder.textNews.setText(textPost);
        if (textPost.isEmpty())
        {
            newsObjectHolder.textNews.setVisibility(View.GONE);
        } else {
            newsObjectHolder.textNews.setVisibility(View.VISIBLE);
        }
        if (post.attachments.getCount() > 0) {
            String type = post.attachments.get(0).getType();
            if (type.equals(VKAttachments.TYPE_PHOTO) || type.equals(VKAttachments.TYPE_POSTED_PHOTO)) {
                VKAttachments attachments = post.attachments;
                Picasso.with(context)
                        .load(((VKApiPhoto) attachments.get(0)).photo_604)
                        .into(newsObjectHolder.imageNews);
            } else {
                newsObjectHolder.imageNews.setImageDrawable(null);
            }
        } else {
            newsObjectHolder.imageNews.setImageDrawable(null);
        }
    }

    @Override
    public void onBindViewHolder(NewsObjectHolder holder, int position) {

        if(getItemViewType(position) == TYPE_POST) {
            VKApiPost post = vkApiPosts.get(position);
            showPost(holder, post);
        } else {
            VKList<VKApiPost> reposts = new VKList<>();
            reposts.addAll(vkApiPosts.get(position).copy_history);
            VKApiPost repost = null;
            if (reposts.size() > 0) {
                repost = reposts.get(0);
            }
            if (repost != null) {
                final NewsRepostViewHolder repostHolder = ((NewsRepostViewHolder)holder);

                Picasso.with(context)
                        .load(urlIconGroup)
                        .into(repostHolder.imageGroupNewsRepost);
                VKApiPost post = vkApiPosts.get(position);
                repostHolder.titleNewsRepost.setText(nameGroup);
                String date2 = timeFromUTCSecs(context, post.date);
                repostHolder.timeNewsRepost.setText(date2);

                repostHolder.textNewsRepost.setText(post.text);
                if (post.text.isEmpty())
                {
                    repostHolder.textNewsRepost.setVisibility(View.GONE);
                } else {
                    repostHolder.textNewsRepost.setVisibility(View.VISIBLE);
                }

                if (post.attachments.getCount() > 0) {
                    String type = post.attachments.get(0).getType();
                    if (type.equals(VKAttachments.TYPE_PHOTO) || type.equals(VKAttachments.TYPE_POSTED_PHOTO)) {
                        VKAttachments attachments = post.attachments;
                        Picasso.with(context)
                                .load(((VKApiPhoto) attachments.get(0)).photo_604)
                                .into(((NewsRepostViewHolder)holder).imageNewsRepost);
                    } else {
                        repostHolder.imageNewsRepost.setImageDrawable(null);
                    }
                } else {
                    repostHolder.imageNewsRepost.setImageDrawable(null);
                }
                showPost(holder, repost);
            }
        }
    }

    private String timeFromUTCSecs(Context context, long secs) {
        return DateUtils.formatDateTime(context, secs * 1000,
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_NUMERIC_DATE);
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

    public class NewsRepostViewHolder extends NewsObjectHolder {

        @Bind(R.id.image_group_item_news_repost)
        ImageView imageGroupNewsRepost;

        @Bind(R.id.title_item_news_repost)
        TextView titleNewsRepost;

        @Bind(R.id.time_item_news_repost)
        TextView timeNewsRepost;

        @Bind(R.id.text_item_news_repost)
        TextView textNewsRepost;

        @Bind(R.id.image_item_news_repost)
        ImageView imageNewsRepost;

        public NewsRepostViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
