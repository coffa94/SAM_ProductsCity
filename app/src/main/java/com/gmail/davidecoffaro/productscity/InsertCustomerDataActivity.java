package com.gmail.davidecoffaro.productscity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gmail.davidecoffaro.productscity.utilclass.MyLocationClass;
import com.gmail.davidecoffaro.productscity.utilclass.task.MyGeocodingTask;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Locale;

public class InsertCustomerDataActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {
    EditText nameCustomer;
    EditText addressCustomer;
    Button locate;
    EditText cityCustomer;
    EditText capCustomer;
    EditText provinceCustomer;
    EditText phoneCustomer;
    Button confirm;
    private SharedPreferences sharedPreferences; //save customer info in sharedPreferences


    /*LocationManager lm;
    private LocationProvider locationProvider;
    //private Location currentLocation;
    */
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

        //location customer management
        /*lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        //providers: passive, gps, network
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW);

        List<String> providers = lm.getProviders(true);
        for(String s : providers){
            Log.d("Providers","Provider: " + s);
            nameCustomer.setText(s);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        //Log.d("PROVIDER",lm.getBestProvider(criteria,true));

        //locationProvider = lm.getProvider(lm.getBestProvider(criteria, true));
        //locationProvider = lm.getProvider(LocationManager.GPS_PROVIDER);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            // here to request the missing permissions, and then overriding

            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        currentLocation = lm.getLastKnownLocation(locationProvider.getName());
        */

        myLocation = new MyLocationClass(this);

        myLocation.createMethod();

    }

    public void onStart() {
        super.onStart();

        myLocation.startMethod();
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(locationProvider.getName(), 0, 0f, this);*/
    }







    /*public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(locate, "Permesso accettato", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        return;
    }*/

    public void onLocationChanged(Location loc) {
        Location currentLocation = loc;
        Log.d("Localizzazione", "Localizzazione effettuatta lat: " + currentLocation.getLatitude() + " , long: " + currentLocation.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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
            //TODO pulsante localizza
            /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm.requestLocationUpdates(locationProvider.getName(), 0, 0f, this);
*/
            myLocation.getLocation();


        }

    }

    public void startGeocoding(double lat, double longitudine){

        MyGeocodingTask geocodingTask = new MyGeocodingTask(this);
        geocodingTask.execute(lat, longitudine);

    }
}