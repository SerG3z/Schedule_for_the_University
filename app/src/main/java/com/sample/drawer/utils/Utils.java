package com.sample.drawer.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.sample.drawer.R;
import com.sample.drawer.fragments.news.NewsFragment;
import com.sample.drawer.fragments.task.TaskFragment;
import com.sample.drawer.fragments.schedule.ScheduleViewPagerFragment;

public class Utils {
    public static final int ACCOUNTS_LOGOUT_ID = 110;

    public static Drawer.OnDrawerItemClickListener handlerOnClick(final Drawer.Result drawerResult, final ActionBarActivity activity) {
        return new Drawer.OnDrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {

                if (drawerItem != null) {

                    if (drawerItem.getIdentifier() == 1) {
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, ScheduleViewPagerFragment.newIntent()).commit();
                    } else if (drawerItem.getIdentifier() == 2) {
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, TaskFragment.newIntent()).commit();
                    } else if (drawerItem.getIdentifier() == 3) {
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new NewsFragment()).commit();
                    } else if (drawerItem.getIdentifier() == 70) {
                        // Rate App
                        try {
                            Intent int_rate = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getApplicationContext().getPackageName()));
                            int_rate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.getApplicationContext().startActivity(int_rate);
                        } catch (Exception e) {
                            //
                        }
                    }
                }
            }
        };
    }

//    public static AccountHeader.Result getAccountHeader(final ActionBarActivity activity, final Bundle savedInstanceState) {
//
//        // Create a few sample profile
//        final IProfile profile = new ProfileDrawerItem().withName("User Name").withEmail("useremail@gmail.com");
//        // Create the AccountHeader
//        return new AccountHeader()
//                .withActivity(activity)
//                .withHeaderBackground(R.drawable.header)
//                .withProfileImagesClickable(false)
//                .withProfileImagesVisible(false)
//                .addProfiles(
//                        profile,
//                        new ProfileSettingDrawerItem().withName("Выход").withIcon(new IconicsDrawable(activity, GoogleMaterial.Icon.gmd_exit_to_app).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(ACCOUNTS_LOGOUT_ID)
//                )
//                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
//                    @Override
//                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
//                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == ACCOUNTS_LOGOUT_ID) {
//                        }
//
//                        return false;
//                    }
//                })
//                .withSavedInstance(savedInstanceState)
//                .build();
//    }

}
