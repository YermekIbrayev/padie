package com.iskhak.serviceprovider.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.helpers.DataHolder;

import java.util.ArrayList;

public class JobsTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jobs_tab, container, false);

        JobsAdapter jobsAdapter = new JobsAdapter(getActivity(), R.layout.job_item, new ArrayList<PackageModel>());
        ListView jobsList = (ListView) view.findViewById(R.id.job_list_lv);
        jobsList.setAdapter(jobsAdapter);
        return view;
    }
}
