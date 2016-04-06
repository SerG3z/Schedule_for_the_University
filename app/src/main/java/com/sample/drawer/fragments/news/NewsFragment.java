package com.sample.drawer.fragments.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;
import com.sample.drawer.R;
import com.sample.drawer.adapter.NewsRecyclerViewAdapter;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiCommunityArray;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;

import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NewsFragment extends Fragment {

    @Bind(R.id.news_recycler)
    RecyclerView newsRecyclerView;
    private RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsObjectHolder> recyclerViewAdapter;
    private LinearLayoutManager layoutManager;
    private String nameGroup;
    private String urlIconGroup;
    private VKList<VKApiPost> posts;
    private int offset = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.info_news);
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

        layoutManager = new LinearLayoutManager(this.getActivity());
        newsRecyclerView.setLayoutManager(layoutManager);

        final VKRequest request = VKApi.groups().getById((VKParameters.from(VKApiConst.GROUP_ID, 34213539, VKApiConst.EXTENDED, 0)));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKApiCommunityArray communityArray = (VKApiCommunityArray) response.parsedModel;
                nameGroup = communityArray.get(0).name;
                urlIconGroup = communityArray.get(0).photo_100;

                final VKRequest request2 = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, -34213539, VKApiConst.EXTENDED, 1, VKApiConst.COUNT, 20));
                request2.setPreferredLang("ru");
                request2.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        posts = (VKList<VKApiPost>) response.parsedModel;
                        recyclerViewAdapter = new NewsRecyclerViewAdapter(posts, getContext(), nameGroup, urlIconGroup);
                        newsRecyclerView.setAdapter(recyclerViewAdapter);
                        newsRecyclerView.addOnScrollListener(createInfiniteScrollListener());
                    }
                });

            }
        });
    }

    private InfiniteScrollListener createInfiniteScrollListener() {
        return new InfiniteScrollListener(10, (LinearLayoutManager) layoutManager) {
            @Override
            public void onScrolledToEnd(int firstVisibleItemPosition) {
//                Toast.makeText(getActivity(), "refresh", Toast.LENGTH_SHORT).show();

                final VKRequest request = VKApi.groups().getById((VKParameters.from(VKApiConst.GROUP_ID, 34213539, VKApiConst.EXTENDED, 0)));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);

                        VKApiCommunityArray communityArray = (VKApiCommunityArray) response.parsedModel;
                        nameGroup = communityArray.get(0).name;
                        urlIconGroup = communityArray.get(0).photo_100;

                        final VKRequest request2 = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, -34213539, VKApiConst.EXTENDED, 1, VKApiConst.OFFSET, offset, VKApiConst.COUNT, 20));
                        offset += 20;
                        request2.setPreferredLang("ru");
                        request2.executeWithListener(new VKRequest.VKRequestListener() {
                            @Override
                            public void onComplete(VKResponse response) {
                                posts.addAll((Collection<? extends VKApiPost>) response.parsedModel);
                            }
                        });

                    }
                });

                refreshView(newsRecyclerView, new NewsRecyclerViewAdapter(posts, getContext(), nameGroup, urlIconGroup), firstVisibleItemPosition);
            }
        };
    }

}
