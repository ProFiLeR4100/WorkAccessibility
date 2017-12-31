package com.aatech.WorkAccessibility.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.aatech.WorkAccessibility.R;
import com.aatech.WorkAccessibility.adapters.VacancyAdapter;
import com.aatech.WorkAccessibility.helpers.AdditionalFunctions;
import com.aatech.WorkAccessibility.listeners.EndlessRecyclerOnScrollListener;
import com.aatech.WorkAccessibility.models.Vacancy;
import com.aatech.WorkAccessibility.services.VacancyRestService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

@EFragment(R.layout.recycler_list)
public class SearchFragment extends Fragment {

    @Bean
    AdditionalFunctions additionalFunctions;
    @ViewById
    RecyclerView recyclerContentView;

    public StaggeredGridLayoutManager vacanciesLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

    public SearchFragment() {
        Bundle bundle = new Bundle();
        this.setArguments(bundle);
    }

    @AfterViews
    void calledAfterViewInjection() {
        getPosts(recyclerContentView.getContext(), recyclerContentView, 0);
        recyclerContentView.addOnScrollListener(new EndlessRecyclerOnScrollListener(vacanciesLayoutManager) {
            @Override
            public void onLoadMore(RecyclerView recyclerView, int current_page) {
                getPosts(recyclerView.getContext(), recyclerView, current_page);
            }
        });
    }

    @RestService
    VacancyRestService vacancyRestService;

    @Background
    public void getPosts(Context context, RecyclerView view, Integer page){
        updatePosts(context, view, vacancyRestService.getAll());
    }

    @UiThread
    public void updatePosts(Context context, RecyclerView view, List<Vacancy> asyncResult) {
        if(view.getLayoutManager()==null)
            view.setLayoutManager(vacanciesLayoutManager);
        VacancyAdapter adapter = (VacancyAdapter) view.getAdapter();
        if(view.getAdapter() == null) {
            adapter = new VacancyAdapter(context, asyncResult);
            view.setAdapter(adapter);
        } else {
            adapter.addVacancies(asyncResult);
        }
    }

}