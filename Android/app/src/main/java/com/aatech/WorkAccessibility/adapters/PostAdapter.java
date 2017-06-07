package com.aatech.WorkAccessibility.adapters;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aatech.WorkAccessibility.R;
import com.aatech.WorkAccessibility.models.Post;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.CategoryItemViewHolder> {
    private List<Post> posts;
    private Context context;

    public PostAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    public void addPost(Post post) {
        posts.add(post);
        this.notifyDataSetChanged();
    }

    public void addPosts(List<Post> posts) {
        this.posts.addAll(posts);
        this.notifyDataSetChanged();
    }

    public void clearPosts() {
        this.posts.clear();
        this.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return posts!=null ? posts.size() : 0;
    }

    @Override
    public CategoryItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vacancy_holder, viewGroup, false);
        CategoryItemViewHolder categoryViewHolder = new CategoryItemViewHolder(v);
        return categoryViewHolder;
    }
    @Override
    public void onBindViewHolder(CategoryItemViewHolder categoryItemViewHolder, int i) {

        // Setting Text Data
        categoryItemViewHolder.categoryItemTitle.setText(posts.get(i).title);
        categoryItemViewHolder.categoryItemDate.setText(posts.get(i).date);
//        categoryItemViewHolder.categoryItemShortText.setText(posts.get(i).shortText);

        // Setting Image Data
        categoryItemViewHolder.categoryItemImagePlaceholder.setImageResource(R.drawable.loader);
        ((AnimationDrawable)categoryItemViewHolder.categoryItemImagePlaceholder.getDrawable()).start();
        if (posts.get(i).imageURL != null) {
            String url = posts.get(i).imageURL;
            final CategoryItemViewHolder catItemVH = categoryItemViewHolder;
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.loader)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .crossFade()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Log.e("Glide Exception", "E:" + e.getMessage());
                            e.printStackTrace();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            catItemVH.categoryItemImagePlaceholder.setVisibility(View.INVISIBLE);
                            catItemVH.categoryItemImage.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(categoryItemViewHolder.categoryItemImage);

        } else {
            // make sure Glide doesn't load anything into this view until told otherwise
            Glide.clear(categoryItemViewHolder.categoryItemImage);
            // remove the placeholder (optional); read comments below
            categoryItemViewHolder.categoryItemImage.setImageDrawable(null);
            // stops the animation

            categoryItemViewHolder.categoryItemImagePlaceholder.setVisibility(View.VISIBLE);
            categoryItemViewHolder.categoryItemImage.setVisibility(View.INVISIBLE);

            AnimationDrawable animationDrawable = (AnimationDrawable)categoryItemViewHolder.categoryItemImagePlaceholder.getDrawable();
            if(animationDrawable != null)
                animationDrawable.stop();
        }

//        categoryItemViewHolder.roundedItemHeader.reInit();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public static class CategoryItemViewHolder extends RecyclerView.ViewHolder{
        final CardView cardView;
        final TextView categoryItemTitle;
//        final TextView categoryItemShortText;
        final TextView categoryItemDate;
        final ImageView categoryItemImage;
        final ImageView categoryItemImagePlaceholder;
        final RelativeLayout roundedItemHeader;

        CategoryItemViewHolder(View itemView){
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.CardContentView);
            categoryItemTitle = (TextView)itemView.findViewById(R.id.categoryItemTitle);
            categoryItemDate = (TextView)itemView.findViewById(R.id.categoryItemDate);
//            categoryItemShortText = (TextView)itemView.findViewById(R.id.categoryItemShortText);
            categoryItemImage = (ImageView)itemView.findViewById(R.id.categoryItemImage);
            categoryItemImagePlaceholder = (ImageView)itemView.findViewById(R.id.categoryItemImagePlaceholder);
            roundedItemHeader = (RelativeLayout)itemView.findViewById(R.id.roundedItemHeader);
        }
    }
}