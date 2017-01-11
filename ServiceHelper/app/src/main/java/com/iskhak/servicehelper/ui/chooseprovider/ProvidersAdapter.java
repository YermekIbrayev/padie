package com.iskhak.servicehelper.ui.chooseprovider;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.data.model.Provider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProvidersAdapter extends RecyclerView.Adapter<ProvidersAdapter.ProviderHolder> {
    private List<Provider> mProviders;
    private Callback mCallback;



    @Inject
    public ProvidersAdapter(){
        mProviders = new ArrayList<>();
    }

    public void setProviders(List<Provider> providers){
        mProviders = providers;
    }

    public void setCallback(Callback callback){
        mCallback = callback;
    }

    @Override
    public ProviderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_provider, parent, false);
        return new ProviderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProviderHolder holder, int position) {
        final Provider provider = mProviders.get(position);
        holder.setProvider(provider);
    }

    @Override
    public int getItemCount() {
        return mProviders.size();
    }

    class ProviderHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.choose_provider_company_name_tv)
        TextView companyNameTV;
        @BindView(R.id.choose_provider_rating_bar)
        RatingBar ratingBar;
        public Provider provider;
        public ProviderHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setProvider(final Provider provider){
            this.provider = provider;
            companyNameTV.setText(provider.name());
            ratingBar.setRating(provider.rating());

        }

        @OnClick(R.id.choose_provider_card_view)
        void onProviderCardViewClicked(){
            if(mCallback!=null)
                mCallback.onProviderClicked(provider);
        }
    }

    interface Callback{
        void onProviderClicked(Provider provider);
    }
}
