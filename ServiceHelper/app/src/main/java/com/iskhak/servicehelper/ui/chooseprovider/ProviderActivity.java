package com.iskhak.servicehelper.ui.chooseprovider;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.data.model.Provider;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.ui.OrderSummaryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProviderActivity extends AppCompatActivity {

    @BindView(R.id.provider_company_name_tv)
    TextView companyNameTV;
    @BindView(R.id.provider_rating_bar)
    RatingBar ratingBar;
    Provider mProvider;

    public static Intent getStartIntent(Context context){
        Intent intent = new Intent(context, ProviderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        ButterKnife.bind(this);
        mProvider = DataHolder.getInstance().getProvider();
        companyNameTV.setText(mProvider.name());
        ratingBar.setRating(mProvider.rating());
    }

    @OnClick(R.id.provider_choose_button)
    public void onChooseButton(){
        DataHolder.getInstance().setOrderProviderId(mProvider.pid());
        startActivity(OrderSummaryActivity.getStartIntent(this));
    }

    @OnClick(R.id.provider_cancel_button)
    public void onCancelButton(){
        onBackPressed();
    }
}
