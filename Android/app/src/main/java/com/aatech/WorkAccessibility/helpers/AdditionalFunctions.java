package com.aatech.WorkAccessibility.helpers;

import android.content.Context;
import android.content.res.Configuration;

import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class AdditionalFunctions {
    public String getSizeName(Context context) {
        switch (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return "large";
            case 4: // Configuration.SCREENLAYOUT_SIZE_XLARGE is API >= 9
                return "xlarge";
            default:
                return "undefined";
        }
    }

    public String PostUrlFormatter(String category){
        return PostUrlFormatter(category,"","",1);
    }

    public String PostUrlFormatter(String category, String subCategory){
        return PostUrlFormatter(category,subCategory,"",1);
    }

    public String PostUrlFormatter(String category, String subCategory, String filter){
        return PostUrlFormatter(category,subCategory,filter,1);
    }

    public String PostUrlFormatter(String category, String subCategory, String filter, int page){
        String result = "http://stopgame.ru";
        result += "/" + category;

        boolean filterExists = false;
        switch (category) {
            case "blogs" :
                filterExists = true;
        }

        if(!subCategory.isEmpty())
            result += "/" + subCategory;

        if(filterExists && !filter.isEmpty()) {
            result += "/" + filter;
        }

        if(page > 1)
            result += "/page" + page;

        return result;
    }
}
