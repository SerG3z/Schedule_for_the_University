package com.sample.drawer.fragments.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sample.drawer.R;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.util.VKUtil;


public class NewsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VKRequest request2 = VKApi.wall().get(VKParameters.from(R.integer.sibsutis_life_Id));

        request2.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onError(VKError error) {

            }

            @Override
            public void onComplete(VKResponse response) {
                VKList<VKApiPost> posts = (VKList<VKApiPost>) response.parsedModel;
                VKApiPost post = posts.get(0);
                Log.d("Post:", post.toString());
            }
        });
    }
}
