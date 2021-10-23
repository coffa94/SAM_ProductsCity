package com.gmail.davidecoffaro.productscity.utilclass.task;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.EditText;

import com.gmail.davidecoffaro.productscity.InsertCustomerDataActivity;
import com.gmail.davidecoffaro.productscity.R;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

public class MyGeocodingTask extends AsyncTask<Double, Void, Address> {
    WeakReference<Activity> linkedActivity;

    public MyGeocodingTask(Activity inputActivity){
        linkedActivity = new WeakReference<Activity>(inputActivity);

    }

    @Override
    protected Address doInBackground(Double... doubles) {
        Geocoder geocoder = new Geocoder(linkedActivity.get(), new Locale("it"));

        List<Address> list = null;
        try {
            list = geocoder.getFromLocation(doubles[0], doubles[1],1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(list!=null){
            return list.get(0);
        }else{
            return null;
        }

    }

    @Override
    protected void onPostExecute(final Address address) {
        super.onPostExecute(address);

        if(address!=null){
            if(linkedActivity.get().getClass()== InsertCustomerDataActivity.class){
                linkedActivity.get().runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        //UI views updating with address info
                        ((EditText)linkedActivity.get().findViewById(R.id.editTextAddress)).setText(address.getAddressLine(0));
                        ((EditText)linkedActivity.get().findViewById(R.id.editTextCity)).setText(address.getLocality());
                        ((EditText)linkedActivity.get().findViewById(R.id.editTextCap)).setText(address.getPostalCode());
                        ((EditText)linkedActivity.get().findViewById(R.id.editTextProvince)).setText(address.getSubAdminArea());

                    }
                });
            }

        }


    }
}
