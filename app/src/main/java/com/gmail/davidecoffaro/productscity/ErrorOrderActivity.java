package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ErrorOrderActivity extends AppCompatActivity implements View.OnClickListener{
    TextView errorOrder;
    Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_order);

        errorOrder = (TextView) findViewById(R.id.textViewErrorOrder);
        retry = (Button) findViewById(R.id.buttonRiprova);

        retry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==retry){
            //TODO click on button retry
        }
    }
}