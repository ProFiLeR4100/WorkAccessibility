package com.aatech.WorkAccessibility.listeners;

// GITHUB: https://gist.github.com/ssinss/e06f12ef66c51252563e

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private boolean noItemsToLoad = false;

    private int visibleThreshold = 10; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    public EndlessRecyclerOnScrollListener(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        this.mStaggeredGridLayoutManager = staggeredGridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mStaggeredGridLayoutManager.getItemCount();


        int[] firstVisibleItems = null;
        firstVisibleItems = mStaggeredGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);
        if(firstVisibleItems != null && firstVisibleItems.length > 0) {
            firstVisibleItem = firstVisibleItems[0];
        }

//        firstVisibleItem = mStaggeredGridLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                if(totalItemCount - previousTotal < visibleThreshold) {
                    noItemsToLoad = true;
                }
                previousTotal = totalItemCount;
            }
        }
        if (!loading && !noItemsToLoad && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;

            onLoadMore(recyclerView, current_page);

            loading = true;
        }
    }

    public abstract void onLoadMore(RecyclerView eventHolder, int current_page);
}