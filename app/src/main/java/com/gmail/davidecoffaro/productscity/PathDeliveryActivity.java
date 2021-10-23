package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
            //click on path delivery (calcola percorso)
            // intent a google maps passando posizione di consegna come destinazione
            if(addressDelivery.getText().toString().equals("")){
                Toast.makeText(this, R.string.error_addressdelivery,Toast.LENGTH_SHORT).show();
            }else{
                //set navigation from device's position to addressDelivery using google maps
                computePath(addressDelivery.getText().toString());

            }
        }
    }

    public void computePath(String addressCustomer){
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + addressCustomer);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}