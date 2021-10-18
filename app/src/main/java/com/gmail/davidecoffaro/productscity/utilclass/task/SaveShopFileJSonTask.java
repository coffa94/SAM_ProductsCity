package com.gmail.davidecoffaro.productscity.utilclass.task;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.widget.ImageView;

import com.gmail.davidecoffaro.productscity.R;
import com.gmail.davidecoffaro.productscity.SearchShopActivity;
import com.gmail.davidecoffaro.productscity.utilclass.DataNegozioJSon;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class SaveShopFileJSonTask extends AsyncTask<DataNegozioJSon, Void, Boolean> {
    private WeakReference<Activity> linkedActivity;
    private String methodCalling;

    public SaveShopFileJSonTask(Activity inputLinkedActivity, String methodCalling){
        linkedActivity = new WeakReference<Activity>(inputLinkedActivity);
        this.methodCalling = methodCalling;
    }

    protected Boolean doInBackground(DataNegozioJSon... params){
        return saveShopFileJSon(params[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        //check which method execute saveShopFileJSonTask
        //OnClick
        if(methodCalling.equals("OnClick")){
            if(result.booleanValue()){
                Intent i = new Intent(linkedActivity.get(), SearchShopActivity.class);
                i.putExtra("Saved", result.booleanValue());
                linkedActivity.get().startActivity(i);
            }else{
                Snackbar.make(linkedActivity.get().findViewById(R.id.buttonConfirm), "Salvataggio non completato", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }

        //OnActivityResult
        if(methodCalling.equals("OnActivityResult")){
            if(result.booleanValue()){
                Snackbar.make(linkedActivity.get().findViewById(R.id.buttonConfirm), "Salvataggio completato", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else{
                Snackbar.make(linkedActivity.get().findViewById(R.id.buttonConfirm), "Salvataggio non completato", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    private Boolean saveShopFileJSon(DataNegozioJSon negozioJSon){
        Gson gson = new Gson();
        String negozioJSonToSave = gson.toJson(negozioJSon);

        return writeFileJSon(negozioJSonToSave);

    }

    private Boolean writeFileJSon(String stringJSonToSave){
        Boolean result = Boolean.FALSE;
        try(BufferedWriter fileToSave = new BufferedWriter(new FileWriter(linkedActivity.get().getFilesDir() + "/negozio1.json"))){

            //save json file writing stringJSonToSave in it
            fileToSave.write(stringJSonToSave);
            fileToSave.flush();
            result = Boolean.TRUE;

        } catch (IOException e) {
            e.printStackTrace();
            result = Boolean.FALSE;
        }

        return result;
    }
}
