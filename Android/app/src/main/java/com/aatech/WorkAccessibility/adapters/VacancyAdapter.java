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
import com.aatech.WorkAccessibility.models.Vacancy;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class VacancyAdapter extends RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder> {
    private List<Vacancy> vacancies;
    private Context context;

    public VacancyAdapter(Context context, List<Vacancy> vacancies){
        this.context = context;
        this.vacancies = vacancies;
    }

    public void addVacancy(Vacancy vacancy) {
        vacancies.add(vacancy);
        this.notifyDataSetChanged();
    }

    public void addVacancies(List<Vacancy> vacancies) {
        this.vacancies.addAll(vacancies);
        this.notifyDataSetChanged();
    }

    public void clearVacancies() {
        this.vacancies.clear();
        this.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return vacancies !=null ? vacancies.size() : 0;
    }

    @Override
    public VacancyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vacancy_holder, viewGroup, false);
        VacancyViewHolder vacancyViewHolder = new VacancyViewHolder(v);
        return vacancyViewHolder;
    }

    @Override
    public void onBindViewHolder(VacancyViewHolder vacancyViewHolder, int i) {

        // Setting Text Data
//        vacancyViewHolder.categoryItemTitle.setText(vacancies.get(i).getName());
//        vacancyViewHolder.categoryItemDate.setText(vacancies.get(i).getDateCreated());
//        vacancyViewHolder.categoryItemShortText.setText(vacancies.get(i).shortText);

        // Setting Image Data
//        vacancyViewHolder.categoryItemImagePlaceholder.setImageResource(R.drawable.loader);
//        ((AnimationDrawable) vacancyViewHolder.categoryItemImagePlaceholder.getDrawable()).start();
//        if (vacancies.get(i).imageURL != null) {
//            String url = vacancies.get(i).imageURL;
//            final VacancyViewHolder catItemVH = vacancyViewHolder;
//            Glide.with(context)
//                    .load(url)
//                    .placeholder(R.drawable.loader)
//                    .bitmapTransform(new CropCircleTransformation(context))
//                    .crossFade()
//                    .listener(new RequestListener<String, GlideDrawable>() {
//                        @Override
//                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                            Log.e("Glide Exception", "E:" + e.getMessage());
//                            e.printStackTrace();
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                            catItemVH.categoryItemImagePlaceholder.setVisibility(View.INVISIBLE);
//                            catItemVH.categoryItemImage.setVisibility(View.VISIBLE);
//                            return false;
//                        }
//                    })
//                    .into(vacancyViewHolder.categoryItemImage);
//
//        } else {
//            // make sure Glide doesn't load anything into this view until told otherwise
//            Glide.clear(vacancyViewHolder.categoryItemImage);
//            // remove the placeholder (optional); read comments below
//            vacancyViewHolder.categoryItemImage.setImageDrawable(null);
//            // stops the animation
//
//            vacancyViewHolder.categoryItemImagePlaceholder.setVisibility(View.VISIBLE);
//            vacancyViewHolder.categoryItemImage.setVisibility(View.INVISIBLE);
//
//            AnimationDrawable animationDrawable = (AnimationDrawable) vacancyViewHolder.categoryItemImagePlaceholder.getDrawable();
//            if(animationDrawable != null)
//                animationDrawable.stop();
//        }

//        vacancyViewHolder.roundedItemHeader.reInit();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public static class VacancyViewHolder extends RecyclerView.ViewHolder {
        final CardView cardView;
        final TextView categoryItemTitle;
//        final TextView categoryItemShortText;
        final TextView categoryItemDate;
        final ImageView categoryItemImage;
        final ImageView categoryItemImagePlaceholder;
        final RelativeLayout roundedItemHeader;

        VacancyViewHolder(View itemView){
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