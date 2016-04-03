package com.sample.drawer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public NewsRecyclerViewAdapter(VKList<VKApiPost> vkApiPosts, Context context, String nameGroup, String urlIconGroup) {
        this.vkApiPosts.addAll(vkApiPosts);
        this.context = context;
        this.nameGroup = nameGroup;
        this.urlIconGroup = urlIconGroup;
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
        //TODO сохранять картунку один раз а не скачивать её для каждой новости
        Picasso.with(context)
                .load(urlIconGroup)
                .into(holder.imageGroupNews);

        holder.titleNews.setText(nameGroup);

        String date = timeFromUTCSecs(context, vkApiPosts.get(position).date);
        holder.timeNews.setText(date);

        holder.textNews.setText(vkApiPosts.get(position).text);
        if (vkApiPosts.get(position).attachments.getCount() > 0) {
            String type = vkApiPosts.get(position).attachments.get(0).getType();
            if (type.equals(VKAttachments.TYPE_PHOTO) || type.equals(VKAttachments.TYPE_POSTED_PHOTO)) {
                VKAttachments attachments = vkApiPosts.get(position).attachments;
                Picasso.with(context)
                        .load(((VKApiPhoto) attachments.get(0)).photo_604)
                        .into(holder.imageNews);
            }
        } else {
            holder.imageNews.setImageDrawable(null);
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


}
