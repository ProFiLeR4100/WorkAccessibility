package com.aatech.WorkAccessibility.listeners;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.aatech.WorkAccessibility.helpers.AdditionalFunctions;
import com.aatech.WorkAccessibility.services.PostsService;

import org.androidannotations.annotations.Bean;

public class EndlessPostOnScrollListener extends EndlessRecyclerOnScrollListener {
    private String category;
    private String subCategory;
    private String filter = "all";
    private static AdditionalFunctions additionalFunctions = new AdditionalFunctions();

    private PostsService postsService = new PostsService();

    public EndlessPostOnScrollListener(StaggeredGridLayoutManager staggeredGridLayoutManager, String category, String subCategory) {
        super(staggeredGridLayoutManager);
        this.category = category;
        this.subCategory = subCategory;
    }

    public EndlessPostOnScrollListener(StaggeredGridLayoutManager staggeredGridLayoutManager, String category, String subCategory, String filter) {
        super(staggeredGridLayoutManager);
        this.category = category;
        this.subCategory = subCategory;
        this.filter = filter;
    }

    @Override
    public void onLoadMore(RecyclerView recyclerView, int current_page) {
        postsService.getPosts(recyclerView.getContext(), recyclerView, additionalFunctions.PostUrlFormatter(category, subCategory, filter, current_page));
    }
}
