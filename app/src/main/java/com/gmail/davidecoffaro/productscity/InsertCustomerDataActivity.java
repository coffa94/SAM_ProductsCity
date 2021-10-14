package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InsertCustomerDataActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nameCustomer;
    EditText addressCustomer;
    Button locate;
    EditText cityCustomer;
    EditText capCustomer;
    EditText provinceCustomer;
    EditText phoneCustomer;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_customer_data);

        nameCustomer = (EditText) findViewById(R.id.editTextPersonName);
        addressCustomer = (EditText) findViewById(R.id.editTextAddress);
        locate = (Button) findViewById(R.id.buttonLocate);
        cityCustomer = (EditText) findViewById(R.id.editTextCity);
        capCustomer = (EditText) findViewById(R.id.editTextCap);
        provinceCustomer = (EditText) findViewById(R.id.editTextProvince);
        phoneCustomer = (EditText) findViewById(R.id.editTextPhone);
        confirm = (Button) findViewById(R.id.buttonConfirm);

        confirm.setOnClickListener(this);
    }

    public void onClick(View v){
        //click su pulsante conferma
        if(v==confirm){
            //TODO conferma per salvare le informazioni cliente (in una struttura dati permanente
            // (database, preferences?, file JSON in locale)) prendendo i dati dagli editText
        }

        //click su pulsante localizza
        if(v==locate){
            //TODO pulsante localizza
        }

    }
}