package com.iskhak.servicehelper.ui.provider;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.data.model.Provider;
import com.iskhak.servicehelper.data.model.ReviewModel;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.helpers.DialogFactory;
import com.iskhak.servicehelper.ui.OrderSummaryActivity;
import com.iskhak.servicehelper.ui.base.BaseActivity;
import com.iskhak.servicehelper.ui.chooseprovider.ChooseProviderActivity;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProviderActivity extends BaseActivity implements ProviderMvpView {
    private final static String REVIEWS_COUNT_PREFIX = "Reviews: %d";
    private final static String PROVIDER_KEY = "provider";

    @Inject
    ReviewsAdapter mReviewsAdapter;

    @Inject
    ProviderPresenter mProviderPresenter;

    @BindView(R.id.provider_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.provider_company_name_tv)
    TextView companyNameTV;
    @BindView(R.id.provider_rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.provider_review_count_tv)
    TextView reviewCountTV;
    Provider mProvider;

    public static Intent getStartIntent(Context context, Provider provider){
        Intent intent = new Intent(context, ProviderActivity.class);
        intent.putExtra(PROVIDER_KEY, provider);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_provider);
        ButterKnife.bind(this);
        mProvider = getIntent().getParcelableExtra(PROVIDER_KEY);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ProviderActivity.this));
        mRecyclerView.setAdapter(mReviewsAdapter);
        mProviderPresenter.attachView(this);
        mProviderPresenter.loadReviews(mProvider.pid());
        companyNameTV.setText(mProvider.name());
        ratingBar.setRating(mProvider.rating());
        reviewCountTV.setText(
                String.format(Locale.getDefault(),
                        REVIEWS_COUNT_PREFIX,
                        mProvider.reviewCount()
                )
        );
    }

    @OnClick(R.id.provider_choose_button)
    public void onChooseButton(){
        DataHolder.getInstance().setOrderProviderId(mProvider.pid());
        DataHolder.getInstance().setProvider(mProvider);
        startActivity(OrderSummaryActivity.getStartIntent(this));
    }

    @OnClick(R.id.provider_cancel_button)
    public void onCancelButton(){
        onBackPressed();
    }

    @Override
    public void showReviews(List<ReviewModel> reviews) {
        mReviewsAdapter.setReviews(reviews);
        mReviewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showReviewsEmpty() {

    }

    @Override
    public void showLoadReviewsError() {
        DialogFactory.createGenericErrorDialog(
                ProviderActivity.this, R.string.error_loading_reviews)
                .show();
    }
}
