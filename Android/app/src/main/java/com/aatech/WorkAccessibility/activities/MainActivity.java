package com.aatech.WorkAccessibility.activities;

import android.content.Context;
import android.os.Debug;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.aatech.WorkAccessibility.R;
import com.aatech.WorkAccessibility.fragments.VacanciesFragment_;
import com.aatech.WorkAccessibility.models.User_;
import com.aatech.WorkAccessibility.services.VacancyRestService;
import com.aatech.WorkAccessibility.services.VacancyRestService_;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.annotations.sharedpreferences.SharedPref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.Map;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @ViewById
    FrameLayout containerFragment;

    @ViewById
    Toolbar toolbar;

    @RestService
    VacancyRestService vacancyRestService;

    Drawer drawerResult;
    DrawerBuilder drawerBuilder;
    boolean drawerOpened = false;

    @Pref
    User_ user;

    PrimaryDrawerItem dItemSignIn;
    PrimaryDrawerItem dItemSignUp;
    PrimaryDrawerItem dItemGames;
    PrimaryDrawerItem dItemNews;
    PrimaryDrawerItem dItemArticles;
    PrimaryDrawerItem dItemVideo;
    PrimaryDrawerItem dItemLive;
    PrimaryDrawerItem dItemSecrets;
    PrimaryDrawerItem dItemGallery;
    PrimaryDrawerItem dItemBlogs;
    PrimaryDrawerItem dItemPeople;
    PrimaryDrawerItem dItemFaq;
    PrimaryDrawerItem dItemSignOut;





    @AfterViews
    protected void init() {
//        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));

        // REGION : РАБОТАЕМ С ПОЛЬЗОВАТЕЛЕМ
        user = new User_(this);
        // ENDREGION : РАБОТАЕМ С ПОЛЬЗОВАТЕЛЕМ

        dItemSignIn = new PrimaryDrawerItem().withName(R.string.nav_drawer_item_sign_in).withIcon(FontAwesome.Icon.faw_sign_in).withSetSelected(false);
        dItemSignUp = new PrimaryDrawerItem().withName(R.string.nav_drawer_item_register).withIcon(FontAwesome.Icon.faw_user_plus);
        dItemGames = new PrimaryDrawerItem().withName(R.string.nav_drawer_item_games).withIcon(FontAwesome.Icon.faw_gamepad).withSetSelected(true);
        dItemNews = new PrimaryDrawerItem().withName(R.string.nav_drawer_item_news).withIcon(FontAwesome.Icon.faw_newspaper_o);
        dItemArticles = new PrimaryDrawerItem().withName(R.string.nav_drawer_item_articles).withIcon(FontAwesome.Icon.faw_file_text_o);
        dItemVideo = new PrimaryDrawerItem().withName(R.string.nav_drawer_item_video).withIcon(FontAwesome.Icon.faw_video_camera);
        dItemLive = new PrimaryDrawerItem().withName(R.string.nav_drawer_item_live).withIcon(FontAwesome.Icon.faw_dot_circle_o);
        dItemSecrets = new PrimaryDrawerItem().withName(R.string.nav_drawer_item_secrets).withIcon(FontAwesome.Icon.faw_user_secret);
        dItemGallery = new PrimaryDrawerItem().withName(R.string.nav_drawer_item_gallery).withIcon(FontAwesome.Icon.faw_camera);
        dItemBlogs = new PrimaryDrawerItem().withName(R.string.nav_drawer_item_blogs).withIcon(FontAwesome.Icon.faw_rss);
        dItemPeople = new PrimaryDrawerItem().withName(R.string.nav_drawer_item_people).withIcon(FontAwesome.Icon.faw_users);
        dItemFaq = new PrimaryDrawerItem().withName(R.string.nav_drawer_item_faq).withIcon(FontAwesome.Icon.faw_question);
        dItemSignOut = new PrimaryDrawerItem().withName(R.string.nav_drawer_item_sign_out).withIcon(FontAwesome.Icon.faw_sign_out);

//        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        // REGION : MATERIAL NAV DRAWER
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ProfileDrawerItem userDrawerProfile = new ProfileDrawerItem()
                .withEmail(user.email().get())
                .withName(user.nickname().get());
        if(user.loggedIn().get()) {
            userDrawerProfile.withIcon(FontAwesome.Icon.faw_user);
        } else {
            userDrawerProfile.withIcon(R.drawable.no_avatar);
//            userDrawerProfile.withIcon("https://pp.vk.me/c624124/v624124149/4f529/wIIOdtiC7B4.jpg");
        }


        drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withAccountHeader(
                new AccountHeaderBuilder()
                    .withActivity(this)
                    .withTranslucentStatusBar(true)
                    .withHeaderBackground(R.drawable.header)
                    .withProfileImagesClickable(true)
                    .withSelectionListEnabledForSingleProfile(false)
                        .addProfiles(
                                userDrawerProfile
                        )
                    .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                        @Override
                        public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                            return false;
                        }

                        @Override
                        public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                            return false;
                        }
                    }).build()
            );

        if(!user.loggedIn().get()) { // Если не авторизован, то показать кнопки авторизации и регистрации
            drawerBuilder.addDrawerItems(
                dItemSignIn,
                dItemSignUp,
                new DividerDrawerItem()
            );
        }

        drawerBuilder.addDrawerItems(
            dItemGames,
            dItemNews,
            dItemArticles,
            dItemVideo,
            dItemLive,
            dItemSecrets,
            dItemGallery,
            dItemBlogs,
            dItemPeople,
            dItemFaq
        );

        if(user.loggedIn().get()) { // Если авторизован, то показать кнопку выхода
            drawerBuilder.addDrawerItems(
                new DividerDrawerItem(),
                dItemSignOut
            );
        }
        drawerResult = drawerBuilder.build();
        drawerResult.setSelection(dItemGames);
        // ENDREGION : MATERIAL NAV DRAWER

        // REGION : MAIN FRAGMENT INIT
        fragmentManager = getSupportFragmentManager();
        VacanciesFragment_ categoriesFragment = new VacanciesFragment_();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.containerFragment, categoriesFragment);
        fragmentTransaction.commit();
        // ENDREGION : MAIN FRAGMENT INIT

        try {
            Map<String, Object> res = vacancyRestService.getAll();
        } catch (Exception ex) {
            Log.e("WorkAccessibility", ex.toString());
        }

    }

    //private void checkSingleton(Bundle savedInstanceState) {
    //}
    @Override
    public void onBackPressed(){
        if(drawerResult!=null) {
            if(drawerResult.isDrawerOpen()) {
                drawerResult.closeDrawer();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
