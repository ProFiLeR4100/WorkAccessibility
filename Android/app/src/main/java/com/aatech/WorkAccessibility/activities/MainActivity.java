package com.aatech.WorkAccessibility.activities;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aatech.WorkAccessibility.R;
import com.aatech.WorkAccessibility.fragments.AboutFragment_;
import com.aatech.WorkAccessibility.fragments.CompaniesFragment_;
import com.aatech.WorkAccessibility.fragments.FavouredVacanciesFragment_;
import com.aatech.WorkAccessibility.fragments.SearchFragment_;
import com.aatech.WorkAccessibility.fragments.VacanciesFragment_;
import com.aatech.WorkAccessibility.models.User_;
import com.aatech.WorkAccessibility.services.VacancyRestService;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
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
    PrimaryDrawerItem dItemFavouritedVacancies;
    PrimaryDrawerItem dItemVacancies;
    PrimaryDrawerItem dItemVacancySearch;
    PrimaryDrawerItem dItemCompanies;
    PrimaryDrawerItem dItemAbout;
    PrimaryDrawerItem dItemCloseDrawer;
    PrimaryDrawerItem dItemSignOut;

    Context ctx;

    public MainActivity() {
        dItemSignIn = generateMenuItem("Войти", FontAwesome.Icon.faw_sign_in);
        dItemSignUp = generateMenuItem("Зарегистрироваться", FontAwesome.Icon.faw_user_plus);
        dItemFavouritedVacancies = generateMenuItem("Избранные вакансии", FontAwesome.Icon.faw_star);
        dItemVacancies = generateMenuItem("Все вакансии", FontAwesome.Icon.faw_address_book, true);
        dItemVacancySearch = generateMenuItem("Поиск вакансий", FontAwesome.Icon.faw_search);
        dItemCompanies = generateMenuItem("Все компании", FontAwesome.Icon.faw_industry);
        dItemAbout = generateMenuItem("О приложении", FontAwesome.Icon.faw_info);
        dItemCloseDrawer = generateMenuItem("Закрыть меню", FontAwesome.Icon.faw_times).withSelectable(false);
        dItemSignOut = generateMenuItem("Выйти", FontAwesome.Icon.faw_sign_out);
        ctx = this;
    }

    @AfterViews
    protected void init() {
        // REGION : РАБОТАЕМ С ПОЛЬЗОВАТЕЛЕМ
//        user = new User_(this);
        // ENDREGION : РАБОТАЕМ С ПОЛЬЗОВАТЕЛЕМ

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
//                        .addProfiles(
//                                userDrawerProfile
//                        )
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
            dItemFavouritedVacancies,
            dItemVacancies,
            dItemVacancySearch,
            dItemCompanies,
            dItemAbout,
            dItemCloseDrawer
        );

        if(user.loggedIn().get()) { // Если авторизован, то показать кнопку выхода
            drawerBuilder.addDrawerItems(
                new DividerDrawerItem(),
                dItemSignOut
            );
        }

        drawerBuilder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                switch(i) {
                    case 4: // favour
                        fragmentManager = getSupportFragmentManager();
                        FavouredVacanciesFragment_ favouredVacanciesFragment = new FavouredVacanciesFragment_();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerFragment, favouredVacanciesFragment);
                        fragmentTransaction.commit();
                        break;
                    case 5: // all vacancies
                        fragmentManager = getSupportFragmentManager();
                        VacanciesFragment_ vacanciesFragment = new VacanciesFragment_();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerFragment, vacanciesFragment);
                        fragmentTransaction.commit();
                        break;
                    case 6: // search
                        fragmentManager = getSupportFragmentManager();
                        SearchFragment_ searchFragment = new SearchFragment_();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerFragment, searchFragment);
                        fragmentTransaction.commit();
                        break;
                    case 7: // companies
                        fragmentManager = getSupportFragmentManager();
                        CompaniesFragment_ companiesFragment = new CompaniesFragment_();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerFragment, companiesFragment);
                        fragmentTransaction.commit();
                        break;
                    case 8: // about
                        fragmentManager = getSupportFragmentManager();
                        AboutFragment_ aboutFragment = new AboutFragment_();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerFragment, aboutFragment);
                        fragmentTransaction.commit();
                        break;
                }

                return false;
            }
        });

        drawerResult = drawerBuilder.build();
        drawerResult.setSelection(dItemVacancies);
        // ENDREGION : MATERIAL NAV DRAWER
    }

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

    private PrimaryDrawerItem generateMenuItem(String name, IIcon icon) {
        return generateMenuItem(name, icon, false);
    }

    private PrimaryDrawerItem generateMenuItem(String name, IIcon icon, boolean selected) {
        return new PrimaryDrawerItem().withName(name).withIcon(icon).withSetSelected(selected);
    }
}
