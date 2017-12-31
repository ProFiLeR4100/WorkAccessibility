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
import com.aatech.WorkAccessibility.models.Company;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyyViewHolder> {
    private List<Company> companies;
    private Context context;

    public CompanyAdapter(Context context, List<Company> companies){
        this.context = context;
        this.companies = companies;
    }

    public void addCompany(Company company) {
        companies.add(company);
        this.notifyDataSetChanged();
    }

    public void addCompanies(List<Company> companies) {
        this.companies.addAll(companies);
        this.notifyDataSetChanged();
    }

    public void clearCompanies() {
        this.companies.clear();
        this.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return companies !=null ? companies.size() : 0;
    }

    @Override
    public CompanyyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.company_holder, viewGroup, false);
        CompanyyViewHolder companyyViewHolder = new CompanyyViewHolder(v);
        return companyyViewHolder;
    }

    @Override
    public void onBindViewHolder(CompanyyViewHolder companyyViewHolder, int i) {
        Company company = companies.get(i);

        String description = company.getDescription();
        if(description!=null)
            description = description.replaceAll("\\n"," ");

        // Setting Text Data
        companyyViewHolder.categoryItemIndustry.setText(company.getIndustry());
        companyyViewHolder.categoryItemContactHuman.setText(company.getUsername());
        companyyViewHolder.categoryItemContactPhone.setText(company.getEmail());
        if(description!=null && description.trim().isEmpty()) {
            companyyViewHolder.categoryItemDescription.setVisibility(View.GONE);
            companyyViewHolder.categoryItemDescriptionIcon.setVisibility(View.GONE);
        } else if (description!=null) {
            companyyViewHolder.categoryItemDescription.setVisibility(View.VISIBLE);
            companyyViewHolder.categoryItemDescriptionIcon.setVisibility(View.VISIBLE);
            companyyViewHolder.categoryItemDescription.setText(description);
        } else {
            companyyViewHolder.categoryItemDescription.setVisibility(View.GONE);
            companyyViewHolder.categoryItemDescriptionIcon.setVisibility(View.GONE);
        }

        companyyViewHolder.categoryItemTitle.setText(company.getName());
        companyyViewHolder.categoryItemDate.setText(company.getDate_registered());

        // Setting Image Data
        companyyViewHolder.vacancyItemImagePlaceholder.setImageResource(R.drawable.loader);
        ((AnimationDrawable) companyyViewHolder.vacancyItemImagePlaceholder.getDrawable()).start();
        if (companies.get(i).getImage() != null) {
            String url = companies.get(i).getImage();
            final CompanyyViewHolder catItemVH = companyyViewHolder;
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
                    .into(companyyViewHolder.vacancyItemImage);

        } else {
            // make sure Glide doesn't load anything into this view until told otherwise
            Glide.clear(companyyViewHolder.vacancyItemImage);
            // remove the placeholder (optional); read comments below
            companyyViewHolder.vacancyItemImage.setImageDrawable(null);
            // stops the animation

            companyyViewHolder.vacancyItemImagePlaceholder.setVisibility(View.VISIBLE);
            companyyViewHolder.vacancyItemImage.setVisibility(View.INVISIBLE);

            AnimationDrawable animationDrawable = (AnimationDrawable) companyyViewHolder.vacancyItemImagePlaceholder.getDrawable();
            if(animationDrawable != null)
                animationDrawable.stop();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public static class CompanyyViewHolder extends RecyclerView.ViewHolder {
        final CardView cardView;
        final ImageView vacancyItemImage;
        final ImageView vacancyItemImagePlaceholder;
        final RelativeLayout roundedItemHeader;

        final TextView categoryItemIndustry;
        final TextView categoryItemContactHuman;
        final TextView categoryItemContactPhone;
        final TextView categoryItemDescription;
        final TextView categoryItemDescriptionIcon;
        final TextView categoryItemTitle;
        final TextView categoryItemDate;


        CompanyyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.CardContentView);
            categoryItemTitle = (TextView) itemView.findViewById(R.id.categoryItemTitle);
            categoryItemIndustry = (TextView) itemView.findViewById(R.id.categoryItemIndustry);
            categoryItemContactHuman = (TextView) itemView.findViewById(R.id.categoryItemContactHuman);
            categoryItemContactPhone = (TextView) itemView.findViewById(R.id.categoryItemContactPhone);
            categoryItemDescription = (TextView) itemView.findViewById(R.id.categoryItemDescription);
            categoryItemDescriptionIcon = (TextView) itemView.findViewById(R.id.categoryItemDescriptionIcon);
            vacancyItemImage = (ImageView)itemView.findViewById(R.id.vacancyItemImage);
            vacancyItemImagePlaceholder = (ImageView)itemView.findViewById(R.id.vacancyItemImagePlaceholder);
            roundedItemHeader = (RelativeLayout)itemView.findViewById(R.id.roundedItemHeader);
            categoryItemDate = (TextView) itemView.findViewById(R.id.categoryItemDate);
        }
    }
}