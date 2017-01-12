package com.iskhak.serviceprovider.ui.orders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.helpers.Constants;
import com.iskhak.serviceprovider.helpers.DataUtil;
import com.iskhak.serviceprovider.ui.orders.OnClickCallback;
import com.iskhak.serviceprovider.ui.orders.order.OrderFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.OrderViewHolder> {
    List<PackageModel> mOrderList;
    private OnClickCallback mCallback;
    private OrderFragment.State mState;

    @Inject
    public RecyclerViewAdapter() {
        mOrderList = new ArrayList<>();
    }

    public void setState(OrderFragment.State state){
        mState = state;
    }

    public void setOrder(List<PackageModel> orderList){
        this.mOrderList = orderList;
    }

    public void setOnClickCallback(OnClickCallback onClickCallback){
        this.mCallback = onClickCallback;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_item, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int pasition){
        final PackageModel order = mOrderList.get(pasition);
        holder.setOrder(order);
    }

    public void add(PackageModel order) {
        if(!mOrderList.contains(order)) {
            mOrderList.add(order);
            notifyDataSetChanged();
        }
    }

    public void clear(){
        mOrderList.clear();
    }

    @Override
    public int getItemCount(){
        return mOrderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{
        private final static String DATE_PREF = "Date: ";
        private final static String TIME_PREF = "Time: ";
        private final static String JOB_COUNT_PREF = "Jobs Count: ";
        private final static String ADDRESS_PREF = "Address: ";
        private final static String PRICE_PREF = "Price $";

        @BindView(R.id.job_name_tv)
        TextView jobName;
        @BindView(R.id.job_address_tv)
        TextView jobAddress;
        @BindView(R.id.job_date_tv)
        TextView jobDate;
        @BindView(R.id.job_time_tv)
        TextView jobTime;
        @BindView(R.id.job_price_tv)
        TextView jobPrice;

        private PackageModel order;

        public OrderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOrder(final PackageModel order){
            this.order = order;
            jobName.setText(JOB_COUNT_PREF+order.id());
            jobAddress.setText(ADDRESS_PREF+order.address());
            jobDate.setText(DATE_PREF+ DataUtil.updateFormat( order.orderDate(), Constants.DATE_FORMAT));
            jobTime.setText(TIME_PREF+DataUtil.updateFormat( order.orderDate(), Constants.TIME_FORMAT));
            jobPrice.setText(PRICE_PREF+order.price());
        }

        @OnClick()
        void onOrderClicked(){
            if(mCallback!=null)
                mCallback.onItemClicked(order, mState);

        }
    }
}
