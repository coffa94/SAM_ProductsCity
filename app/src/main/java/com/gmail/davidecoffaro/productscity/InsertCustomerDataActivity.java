package com.gmail.davidecoffaro.productscity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gmail.davidecoffaro.productscity.utilclass.MyLocationClass;
import com.gmail.davidecoffaro.productscity.utilclass.task.MyGeocodingTask;
import com.google.android.material.snackbar.Snackbar;


public class InsertCustomerDataActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nameCustomer;
    EditText addressCustomer;
    Button locate;
    EditText cityCustomer;
    EditText capCustomer;
    EditText provinceCustomer;
    EditText phoneCustomer;
    Button confirm;
    private SharedPreferences sharedPreferences; //save customer info in sharedPreferences

    MyLocationClass myLocation;

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

        locate = (Button) findViewById(R.id.buttonLocate);
        locate.setOnClickListener(this);

        //inizializzazione riferimento a oggetto SharedPreferences
        // utilizzo di un file preferences di nome string.preferences_file condiviso fra tutte le
        // activity che hanno bisogno di utilizzarlo
        sharedPreferences = getSharedPreferences(getString(R.string.preferences_file), Context.MODE_PRIVATE);

        //initialize views with customerData in sharedPreferences, if they are set
        if (sharedPreferences.contains("NameCustomer")) {
            nameCustomer.setText(sharedPreferences.getString(getString(R.string.preferences_name_customer), ""));
            addressCustomer.setText(sharedPreferences.getString(getString(R.string.preferences_address_customer), ""));
            cityCustomer.setText(sharedPreferences.getString(getString(R.string.preferences_city_customer), ""));
            capCustomer.setText(sharedPreferences.getString(getString(R.string.preferences_cap_customer), ""));
            provinceCustomer.setText(sharedPreferences.getString(getString(R.string.preferences_province_customer), ""));
            phoneCustomer.setText(sharedPreferences.getString(getString(R.string.preferences_phone_customer), ""));
        }

        //initialize myLocation variable to get locationUser
        myLocation = new MyLocationClass(this);
        myLocation.createMethod();

    }

    public void onStart() {
        super.onStart();

        myLocation.startMethod();
    }

    public void onClick(View v) {
        //click su pulsante conferma
        if (v == confirm) {
            //TODO conferma per salvare le informazioni cliente (in una struttura dati permanente
            // (database, preferences?, file JSON in locale)) prendendo i dati dagli editText
            //check if required fields are empty
            if (nameCustomer.getText().toString().equals("")) {
                Snackbar.make(v, "Nome cliente mancante", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            if (addressCustomer.getText().toString().equals("")) {
                Snackbar.make(v, "Indirizzo cliente mancante", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            if (phoneCustomer.getText().toString().equals("")) {
                Snackbar.make(v, "Numero di telefono cliente mancante", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }

            //start edit preferences_file
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(getString(R.string.preferences_name_customer), nameCustomer.getText().toString());
            editor.putString(getString(R.string.preferences_address_customer), addressCustomer.getText().toString());
            editor.putString(getString(R.string.preferences_city_customer), cityCustomer.getText().toString());
            editor.putString(getString(R.string.preferences_cap_customer), capCustomer.getText().toString());
            editor.putString(getString(R.string.preferences_province_customer), provinceCustomer.getText().toString());
            editor.putString(getString(R.string.preferences_phone_customer), phoneCustomer.getText().toString());
            editor.apply();

            //start next activity BuyProductsActivity
            Intent i = new Intent(this, BuyProductsActivity.class);
            startActivity(i);

        }

        //click su pulsante localizza
        if (v == locate) {
            //check for current user's location
            myLocation.getLocation();
        }
    }

    public void startGeocoding(double latitude, double longitude){
        //async task to get current address depending on latitude and longitude. Not executed in UI
        // thread
        MyGeocodingTask geocodingTask = new MyGeocodingTask(this);
        geocodingTask.execute(latitude, longitude);

    }
}