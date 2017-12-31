package com.aatech.WorkAccessibility.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.aatech.WorkAccessibility.R;
import com.aatech.WorkAccessibility.adapters.CompanyAdapter;
import com.aatech.WorkAccessibility.adapters.VacancyAdapter;
import com.aatech.WorkAccessibility.helpers.AdditionalFunctions;
import com.aatech.WorkAccessibility.listeners.EndlessRecyclerOnScrollListener;
import com.aatech.WorkAccessibility.models.Company;
import com.aatech.WorkAccessibility.models.Vacancy;
import com.aatech.WorkAccessibility.services.CompanyRestService;
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
public class CompaniesFragment extends Fragment {

    @Bean
    AdditionalFunctions additionalFunctions;
    @ViewById
    RecyclerView recyclerContentView;

    public StaggeredGridLayoutManager vacanciesLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

    public CompaniesFragment() {
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
    CompanyRestService companyRestService;

    @Background
    public void getPosts(Context context, RecyclerView view, Integer page) {
        try {
            updatePosts(context, view, companyRestService.getAll());
        } catch (Exception ex) {
            Log.e("AsyncLoader", ex.toString());
        }
    }

    @UiThread
    public void updatePosts(Context context, RecyclerView view, List<Company> asyncResult) {
        if(view.getLayoutManager()==null)
            view.setLayoutManager(vacanciesLayoutManager);
        CompanyAdapter adapter = (CompanyAdapter) view.getAdapter();
        if(view.getAdapter() == null) {
            adapter = new CompanyAdapter(context, asyncResult);
            view.setAdapter(adapter);
        } else {
            adapter.addCompanies(asyncResult);
        }
    }

}