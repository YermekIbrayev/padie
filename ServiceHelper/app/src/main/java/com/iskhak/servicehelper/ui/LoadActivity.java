package com.iskhak.servicehelper.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iskhak.servicehelper.data.SyncService;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.ui.chooseprovider.ChooseProviderActivity;
import com.iskhak.servicehelper.ui.login.LoginActivity;

public class LoadActivity extends AppCompatActivity {

    public enum State {
        BEFORE_BEGIN, PROCESSING
    }

    public static final String KEY_STATE = "State";
    public static final String BEFORE_BEGIN = "Before begin";
    public static final String PROCESSING = "Processing";

    private State mState;

    public static Intent getStartIntent(Context context, String state){
        Intent intent = new Intent(context, LoadActivity.class);
        intent.putExtra(KEY_STATE, state);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Intent intent = getIntent();
        String state = intent.getStringExtra(KEY_STATE);
        if(state==null||state.equals(BEFORE_BEGIN))
            mState = State.BEFORE_BEGIN;
        else
            mState = State.PROCESSING;
        if(mState == State.BEFORE_BEGIN) {
            startService(SyncService.getStartIntent(this));
            DataHolder.getInstance().setContext(this);
        }
    }


}
