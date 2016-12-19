package com.iskhak.serviceprovider.ui;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.DataManager;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.helpers.DataHolder;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class FullOrderFragment extends Fragment {

    @Inject
    DataManager mDataManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String BY_ID = "byId";
    public static final String BY_POSITION = "byPosition";
    private static final String DATE_PREFIX = "Date: ";
    private static final String TIME_PREFIX = "Time: ";
    private static final String ADDRESS_PREFIX = "Address: ";
    private static final String NOTES_PREFIX = "Notes: ";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private PackageModel order;
    private Subscription mSubscription;
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


    public FullOrderFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FullOrderFragment newInstance(int param1, String param2) {
        FullOrderFragment fragment = new FullOrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).activityComponent().inject(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            if(mParam2.equals(BY_POSITION))
                order = DataHolder.getInstance().getOrderByPosition(mParam1);
            else
                order = DataHolder.getInstance().getOrderById(mParam1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.order_item, container, false);
        ButterKnife.bind(this, view);
        fillData();
        return view;
    }

    private void fillData(){
        serviceName.setText(order.selectedItems().get(0).serviceID().name());
        dateTV.setText(DATE_PREFIX+DataHolder.updateFormat(order.orderDate(),DataHolder.DATE_FORMAT));
        timeTV.setText(TIME_PREFIX+DataHolder.updateFormat(order.orderDate(),DataHolder.TIME_FORMAT));
        addressTV.setText(ADDRESS_PREFIX+order.address());
        notesTV.setText(NOTES_PREFIX+order.notes());
    }

    @OnClick(R.id.accept_button)
    public void onAcceptButton(){
        Timber.d("On Accept button");
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        mSubscription = mDataManager.acceptOrder(order.id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Void>>() {
                    @Override
                    public void onCompleted() {
                        mSubscription = null;
                        List<Fragment> fragments =getActivity().getSupportFragmentManager().getFragments();

                        for(Fragment currentFragment:fragments)
                            if(currentFragment instanceof RequestsTab) {
                                ((RequestsTab) currentFragment).updateAll();
                            }
                        ((MainActivity)getActivity()).showTabs(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "onError %s", e.toString());
                    }

                    @Override
                    public void onNext(Response<Void> voidResponse) {
                        Timber.d("onNext %s", voidResponse.code());
                    }
                });
    }
}
