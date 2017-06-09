package com.aatech.WorkAccessibility.services;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.aatech.WorkAccessibility.adapters.VacancyAdapter;
import com.aatech.WorkAccessibility.fragments.VacanciesFragment;
import com.aatech.WorkAccessibility.models.Vacancy;

import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EBean(scope = EBean.Scope.Singleton)
public class PostsService {
    @RestService
    VacancyRestService vacancyRestService;
    private class DownloadPostsTask extends AsyncTask<String, Void, ArrayList<Vacancy>> {
        RecyclerView view;
        private Context context;

        DownloadPostsTask(Context context, RecyclerView view) {
            this.context = context;
            this.view = view;
        }

        protected ArrayList<Vacancy> doInBackground(String... urls) {
            ArrayList<Vacancy> result = new ArrayList<>();
            List<Map<String, Object>> items = vacancyRestService.getAll();

            for (Map<String, Object> item : items){
                Vacancy v = new Vacancy();
                v.setId(item.get("id")!=null ? item.get("id").toString() : "");
                v.setName(item.get("name")!=null ? item.get("name").toString() : "");
                v.setDescription(item.get("description")!=null ? item.get("description").toString() : "");
                v.setCompanyId(item.get("company_id")!=null ? item.get("company_id").toString() : "");
                v.setDateCreated(item.get("date_created")!=null ? item.get("date_created").toString() : "");
                v.setDateModified(item.get("date_modified")!=null ? item.get("date_modified").toString() : "");
                v.setUserId(item.get("user_id")!=null ? item.get("user_id").toString() : "");
                v.setMinSalary(item.get("min_salary")!=null ? item.get("min_salary").toString() : "");
                result.add(v);
            }

            return result;
        }

        protected void onPostExecute(ArrayList<Vacancy> result) {
            view.setLayoutManager(VacanciesFragment.vacanciesLayoutManager);
            VacancyAdapter adapter = (VacancyAdapter) view.getAdapter();
            if(view.getAdapter() == null) {
                adapter = new VacancyAdapter(context, result);
                view.setAdapter(adapter);
            } else {
                adapter.addVacancies(result);
            }
        }
    }

    public void getPosts(Context context, RecyclerView view, String url) {
        new DownloadPostsTask(context, view).execute(url);
    }
}
