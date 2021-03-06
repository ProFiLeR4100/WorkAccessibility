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
        Vacancy vacancy = vacancies.get(i);

        // Setting Text Data
        vacancyViewHolder.categoryItemSalary.setText(vacancy.getMin_salary() + "грн.");
        vacancyViewHolder.categoryItemIndustry.setText(vacancy.getCompany_name() + " (" + vacancy.getIndustry() + ")");
        vacancyViewHolder.categoryItemContactHuman.setText(vacancy.getUsername());
        vacancyViewHolder.categoryItemContactPhone.setText(vacancy.getEmail());
        vacancyViewHolder.categoryItemDescription.setText(vacancy.getDescription().replaceAll("\\n"," "));
        vacancyViewHolder.categoryItemDate.setText(vacancy.getDate_created());
        vacancyViewHolder.categoryItemTitle.setText(vacancy.getName());

        // Setting Image Data
        vacancyViewHolder.vacancyItemImagePlaceholder.setImageResource(R.drawable.loader);
        ((AnimationDrawable) vacancyViewHolder.vacancyItemImagePlaceholder.getDrawable()).start();
        if (vacancies.get(i).getImage() != null) {
            String url = vacancies.get(i).getImage();
            final VacancyViewHolder catItemVH = vacancyViewHolder;
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
                            catItemVH.vacancyItemImagePlaceholder.setVisibility(View.INVISIBLE);
                            catItemVH.vacancyItemImage.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(vacancyViewHolder.vacancyItemImage);

        } else {
            // make sure Glide doesn't load anything into this view until told otherwise
            Glide.clear(vacancyViewHolder.vacancyItemImage);
            // remove the placeholder (optional); read comments below
            vacancyViewHolder.vacancyItemImage.setImageDrawable(null);
            // stops the animation

            vacancyViewHolder.vacancyItemImagePlaceholder.setVisibility(View.VISIBLE);
            vacancyViewHolder.vacancyItemImage.setVisibility(View.INVISIBLE);

            AnimationDrawable animationDrawable = (AnimationDrawable) vacancyViewHolder.vacancyItemImagePlaceholder.getDrawable();
            if(animationDrawable != null)
                animationDrawable.stop();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public static class VacancyViewHolder extends RecyclerView.ViewHolder {
        final CardView cardView;
        final ImageView vacancyItemImage;
        final ImageView vacancyItemImagePlaceholder;
        final RelativeLayout roundedItemHeader;

        final TextView categoryItemSalary;
        final TextView categoryItemIndustry;
        final TextView categoryItemContactHuman;
        final TextView categoryItemContactPhone;
        final TextView categoryItemDescription;
        final TextView categoryItemTitle;
        final TextView categoryItemDate;

        VacancyViewHolder(View itemView){
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.CardContentView);
            categoryItemDate = (TextView) itemView.findViewById(R.id.categoryItemDate);
            categoryItemTitle = (TextView) itemView.findViewById(R.id.categoryItemTitle);
            categoryItemSalary = (TextView) itemView.findViewById(R.id.categoryItemSalary);
            categoryItemIndustry = (TextView) itemView.findViewById(R.id.categoryItemIndustry);
            categoryItemContactHuman = (TextView) itemView.findViewById(R.id.categoryItemContactHuman);
            categoryItemContactPhone = (TextView) itemView.findViewById(R.id.categoryItemContactPhone);
            categoryItemDescription = (TextView) itemView.findViewById(R.id.categoryItemDescription);
            vacancyItemImage = (ImageView)itemView.findViewById(R.id.vacancyItemImage);
            vacancyItemImagePlaceholder = (ImageView)itemView.findViewById(R.id.vacancyItemImagePlaceholder);
            roundedItemHeader = (RelativeLayout)itemView.findViewById(R.id.roundedItemHeader);
        }
    }
}