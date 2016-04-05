package com.sample.drawer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.sample.drawer.R;
import com.sample.drawer.fragments.schedule.ScheduleViewPagerFragment;
import com.sample.drawer.utils.Utils;
import com.vk.sdk.VKSdk;
import com.vk.sdk.util.VKUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.sample.drawer.utils.Utils.handlerOnClick;

public class MainActivity extends ActionBarActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private AccountHeader.Result headerResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MyThemeIndigoDrawer);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, ScheduleViewPagerFragment.newIntent()).commit();

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
//        headerResult = Utils.getAccountHeader(MainActivity.this, savedInstanceState);
        final Drawer.Result drawerResult = createCommonDrawer(MainActivity.this, toolbar, headerResult);
        drawerResult.setSelectionByIdentifier(1, false); // Set proper selection

        drawerResult.openDrawer();
        //vk SDK
        String[] otpe4atok = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        for (String str : otpe4atok) {
            Log.d("otpe4atok = ", str);
        }
    }

    public Drawer.Result createCommonDrawer(final ActionBarActivity activity, Toolbar toolbar, AccountHeader.Result headerResult) {

        Drawer.Result drawerResult = new Drawer()
                .withActivity(activity)
                .withHeader(R.layout.drawer_header)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.info_schedule).withIcon(GoogleMaterial.Icon.gmd_event_note).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.info_tasks).withIcon(GoogleMaterial.Icon.gmd_assignment_turned_in).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.info_news).withIcon(GoogleMaterial.Icon.gmd_dvr).withIdentifier(3)
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public boolean equals(Object o) {
                        return super.equals(o);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        hideSoftKeyboard(activity);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }
                })
                .build();
        drawerResult.setOnDrawerItemClickListener(handlerOnClick(drawerResult, activity));

        return drawerResult;
    }

    public void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            //
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_schedule_toolbar:
                Intent intent = new Intent(this, AddNewSchedule.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    //    @Override
//    public void onBackPressed() {
//        if (drawerResult.isDrawerOpen()) {
//            drawerResult.closeDrawer();
//        } else {
//            super.onBackPressed();
//        }
//    }
}
