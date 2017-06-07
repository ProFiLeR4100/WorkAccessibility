package com.aatech.WorkAccessibility.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.aatech.WorkAccessibility.R;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mykhailo on 12.03.2016.
 */
@SharedPref
public interface User {
    String email();
    String registrationDate();
    String lastVisit();
    boolean gender();
    String birthDate();
    String skype();
    String vk();
    String ok();
    String twitter();
    String steam();
    String pc();
    String location();
    String about();
    boolean loggedIn();
    String nickname();
    String avatar();
}
