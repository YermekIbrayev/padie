package com.iskhak.servicehelper.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iskhak.servicehelper.R;

public class EstimatedOrderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estamated_order);
        TextView totalEstimated = (TextView)findViewById(R.id.totalEstimated);
        int totalSum = getIntent().getIntExtra("TotalSum", 0);
        totalEstimated.setText("$"+totalSum);
    }

    public void onCancelButton(View view){
        finish();
    }

    public void onOrderButton(View view){
        Intent intent = new Intent(this, OrderSummaryActivity.class );
        startActivity(intent);
    }
}
