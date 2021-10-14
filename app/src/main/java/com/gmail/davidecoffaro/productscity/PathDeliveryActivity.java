package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PathDeliveryActivity extends AppCompatActivity implements View.OnClickListener {
    EditText addressDelivery;
    Button pathDelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_delivery);

        addressDelivery = (EditText) findViewById(R.id.editTextAddressDelivery);
        pathDelivery = (Button) findViewById(R.id.buttonPathDelivery);

        pathDelivery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==pathDelivery){
            //TODO click on path delivery (calcola percorso)
            // intent a google maps passando posizione attuale e posizione di consegna come extra

        }
    }
}