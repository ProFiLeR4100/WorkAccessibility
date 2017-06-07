package com.aatech.WorkAccessibility.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.aatech.WorkAccessibility.R;
import com.aatech.WorkAccessibility.helpers.AdditionalFunctions;
import com.aatech.WorkAccessibility.listeners.EndlessPostOnScrollListener;
import com.aatech.WorkAccessibility.services.PostsService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.recycler_list)
public class VacanciesFragment extends Fragment {

    @Bean
    AdditionalFunctions additionalFunctions;
    @Bean
    PostsService postsService;
    @ViewById
    RecyclerView recyclerContentView;

    private static final String CATEGORY = "blogs";
    public static final StaggeredGridLayoutManager vacanciesLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

    public VacanciesFragment() {
        Bundle bundle = new Bundle();
        this.setArguments(bundle);
    }

    @AfterViews
    void calledAfterViewInjection() {
        postsService.getPosts(recyclerContentView.getContext(), recyclerContentView, additionalFunctions.PostUrlFormatter(CATEGORY));
        recyclerContentView.addOnScrollListener(new EndlessPostOnScrollListener(vacanciesLayoutManager, CATEGORY, "new/all"));
    }

}