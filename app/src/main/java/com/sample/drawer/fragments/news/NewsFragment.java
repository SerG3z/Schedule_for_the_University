package com.sample.drawer.fragments.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.drawer.R;
import com.sample.drawer.adapter.NewsRecyclerViewAdapter;
import com.sample.drawer.adapter.TaskRecyclerViewAdapter;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NewsFragment extends Fragment {

    private RecyclerView.Adapter recyclerViewAdapter;

    @Bind(R.id.news_recycler)
    RecyclerView newsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        newsRecyclerView.setLayoutManager(layoutManager);

        VKRequest request2 = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, -34213539, VKApiConst.EXTENDED, 1, VKApiConst.COUNT, 10));
        request2.setPreferredLang("ru");
        request2.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKList<VKApiPost> posts = (VKList<VKApiPost>) response.parsedModel;
                recyclerViewAdapter = new NewsRecyclerViewAdapter(posts);
                newsRecyclerView.setAdapter(recyclerViewAdapter);
                for (VKApiPost post: posts) {
                    Log.d("Post : ", post.text);
                }
            }
        });




    }
}
