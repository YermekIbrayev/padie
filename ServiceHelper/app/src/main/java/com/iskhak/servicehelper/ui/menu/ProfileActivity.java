package com.iskhak.servicehelper.ui.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.extra.UserPreferences;
import com.iskhak.servicehelper.helpers.DataHolder;

public class ProfileActivity extends BasicSettingsActivity {
    EditText userName;
    EditText userAddress;
    UserPreferences userPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setSubtitleText("Profile");
        userName = (EditText)findViewById(R.id.userName);
        userAddress = (EditText) findViewById(R.id.userAddress);

        userPreferences = DataHolder.getInstance().getUserPreferences();

        userName.setText(userPreferences.getUserName());
        userAddress.setText(userPreferences.getCurrentUserAddress());
    }

    public void onSaveButton(View view){
        userPreferences.setUserName(userName.getText().toString());
        userPreferences.setCurrentUserAddress(userAddress.getText().toString());
        finish();
    }
}
