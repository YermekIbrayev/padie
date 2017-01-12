package com.iskhak.serviceprovider.ui.orders.order;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.helpers.Constants;
import com.iskhak.serviceprovider.helpers.DataUtil;
import com.iskhak.serviceprovider.helpers.DialogFactory;
import com.iskhak.serviceprovider.ui.orders.activity.OrdersActivity;
import com.iskhak.serviceprovider.ui.orders.newrequests.RequestsTab;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import timber.log.Timber;

public class OrderFragment extends Fragment implements OrderMvpView {

    public static final String TAG = "FullOrderFragment";

    @Inject
    OrderPresenter mOrderPresenter;

    public enum State {NEW, ACCEPT, DONE}
    // TODO: Rename parameter arguments, choose names that match
    private static final String DATE_PREFIX = "Date: %s";
    private static final String TIME_PREFIX = "Time: %s";
    private static final String ADDRESS_PREFIX = "Address: $s";
    private static final String NOTES_PREFIX = "Notes: $s";
    private static final String ACCEPT_TEXT="Accept";
    private static final String DONE_TEXT = "Done";

    @BindView(R.id.service_name_tv)
    TextView serviceName;
    @BindView(R.id.service_date_tv)
    TextView dateTV;
    @BindView(R.id.service_time_tv)
    TextView timeTV;
    @BindView(R.id.address_tv)
    TextView addressTV;
    @BindView(R.id.notes_tv)
    TextView notesTV;
    @BindView(R.id.accept_button)
    Button acceptButton;

    private PackageModel order;
    private State currentState;

    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance(){
        OrderFragment fragment = new OrderFragment();
        return  fragment;
    }

    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(PackageModel order, State state) {
        OrderFragment fragment = new OrderFragment();
        fragment.setOrder(order);
        fragment.setCurrentState(state);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((OrdersActivity)getActivity()).activityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        mOrderPresenter.attachView(this);
        Timber.d(""+order.id());
        fillData();
        return view;
    }

    private void setOrder(PackageModel order){
        this.order = order;
    }

    private void setCurrentState(State state){
        currentState = state;
    }

    private void fillData(){
        serviceName.setText(order.selectedItems().get(0).serviceID().name());
        dateTV.setText(String.format(DATE_PREFIX, DataUtil.updateFormat(order.orderDate(), Constants.DATE_FORMAT)));
        timeTV.setText(String.format(TIME_PREFIX, DataUtil.updateFormat(order.orderDate(), Constants.TIME_FORMAT)));
        addressTV.setText(String.format(ADDRESS_PREFIX, order.address()));
        notesTV.setText(String.format(NOTES_PREFIX,order.notes()));

        int visibility = View.VISIBLE;
        if(currentState==State.ACCEPT)
            acceptButton.setText(DONE_TEXT);
        else if(currentState==State.DONE)
            visibility = View.GONE;
        else
            acceptButton.setText(ACCEPT_TEXT);
        acceptButton.setVisibility(visibility);
    }

    @OnClick(R.id.accept_button)
    public void onButtonClick(){
        if(acceptButton.getText().toString().equals(ACCEPT_TEXT))
            mOrderPresenter.onAcceptButton(order);
        else if(acceptButton.getText().toString().equals(DONE_TEXT))
            mOrderPresenter.onDoneButton(order);
    }

    @Override
    public void showAcceptComplete() {
        OrdersActivity ordersActivity = (OrdersActivity) getActivity();
        ordersActivity.updateAll();;
        ordersActivity.showTabs(true);
    }

    @Override
    public void showAcceptError() {
        DialogFactory.createGenericErrorDialog(
                getContext(), getString(R.string.error_accepting_order))
                .show();
    }
}
