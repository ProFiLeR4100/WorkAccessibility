package com.aatech.WorkAccessibility.services;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.aatech.WorkAccessibility.adapters.PostAdapter;
import com.aatech.WorkAccessibility.fragments.VacanciesFragment;
import com.aatech.WorkAccessibility.models.Post;

import org.androidannotations.annotations.EBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

@EBean(scope = EBean.Scope.Singleton)
public class PostsService {
    private class DownloadPostsTask extends AsyncTask<String, Void, ArrayList<Post>> {
        RecyclerView view;
        private Context context;

        DownloadPostsTask(Context context, RecyclerView view) {
            this.context = context;
            this.view = view;
        }

        protected ArrayList<Post> doInBackground(String... urls) {
            Document webPage;

            try {
                webPage = Jsoup.connect(urls[0]).get();
            } catch (IOException ex) {
                Log.e("Error", ex.getMessage());
                ex.printStackTrace();
                return null;
            }

            ArrayList<Post> result = new ArrayList<>();

            for (Element el : webPage.select(".blog-block")){
                result.add(new Post(
                        el.select(".title > a").first().text(),
                        el.select(".user-name > a").first().text()+", "+el.select(".response-date").first().text(),
                        el.select(".topic_text").first().text().replace("[далее]",""),
                        el.select(".topic_text > .media img").first().attr("src")
                ));
            }

            return result;
        }

        protected void onPostExecute(ArrayList<Post> result) {
            view.setLayoutManager(VacanciesFragment.vacanciesLayoutManager);
            PostAdapter adapter = (PostAdapter) view.getAdapter();
            if(view.getAdapter() == null) {
                adapter = new PostAdapter(context, result);
                view.setAdapter(adapter);
            } else {
                adapter.addPosts(result);
            }
        }
    }

    public void getPosts(Context context, RecyclerView view, String url) {
        new DownloadPostsTask(context, view).execute(url);
    }
}
