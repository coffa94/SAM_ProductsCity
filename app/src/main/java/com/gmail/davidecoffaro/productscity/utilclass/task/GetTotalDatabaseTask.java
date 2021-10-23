package com.gmail.davidecoffaro.productscity.utilclass.task;

import android.app.Activity;
import android.os.AsyncTask;

import com.gmail.davidecoffaro.productscity.BuyProductsActivity;
import com.gmail.davidecoffaro.productscity.CustomerCartActivity;
import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.databaseclass.NegozioDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

public class GetTotalDatabaseTask extends AsyncTask<Void, Void, Float> {
    WeakReference<Activity> linkedActivity;
    String operation;
    Class activityType;

    public GetTotalDatabaseTask(Activity inputActivity, String inputOperation){
        linkedActivity = new WeakReference<Activity>(inputActivity);
        activityType = inputActivity.getClass();
        operation = inputOperation;
    }

    @Override
    protected Float doInBackground(Void... avoids) {

        //execute the correct operation on database
        switch (operation){
            case "GetTotalArticles":
                return NegozioDatabase.database.prodottoDao().getTotalArticles();
            case "GetTotalOrder":
                return NegozioDatabase.database.prodottoDao().getTotalOrder();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Float result) {
        switch (operation){
            case "GetTotalArticles":
                if(activityType==CustomerCartActivity.class){
                    ((CustomerCartActivity)linkedActivity.get()).updateTotalArticles((int)result.floatValue());
                }
                break;
            case "GetTotalOrder":
                if(activityType==CustomerCartActivity.class){
                    ((CustomerCartActivity)linkedActivity.get()).updateTotalOrder(result);
                }
        }

    }
}
