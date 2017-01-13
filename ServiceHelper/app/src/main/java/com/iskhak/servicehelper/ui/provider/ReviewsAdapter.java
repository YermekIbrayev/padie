package com.iskhak.servicehelper.ui.provider;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.data.model.ReviewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder>{
    private List<ReviewModel> mReviews;

    @Inject
    public ReviewsAdapter(){
        mReviews= new ArrayList<>();
    }

    public void setReviews(List<ReviewModel> reviews){
        mReviews = reviews;
    }

    @Override
    public ReviewsAdapter.ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ReviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ReviewHolder holder, int position) {
        final ReviewModel review = mReviews.get(position);
        holder.setReview(review);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder{

        private final static String REVIEWS_COUNT_PREFIX = "Reviews: %d";

        @BindView(R.id.item_review_username)
        TextView usernameTV;
        @BindView(R.id.item_review_rating_bar)
        RatingBar ratingBar;
        @BindView(R.id.item_review_description)
        TextView descriptionTV;
        public ReviewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setReview(final ReviewModel review){
            usernameTV.setText(review.user().username());
            ratingBar.setRating(review.rating());
            descriptionTV.setText(review.description());
        }
    }

}
