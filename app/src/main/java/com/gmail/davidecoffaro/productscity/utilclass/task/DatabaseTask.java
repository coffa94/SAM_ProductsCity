package com.gmail.davidecoffaro.productscity.utilclass.task;

import android.app.Activity;
import android.os.AsyncTask;

import com.gmail.davidecoffaro.productscity.BuyProductsActivity;
import com.gmail.davidecoffaro.productscity.CustomerCartActivity;
import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.databaseclass.NegozioDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

public class DatabaseTask extends AsyncTask<Prodotto[], Void, List<Prodotto>> {
    WeakReference<Activity> linkedActivity;
    String operation;
    Class activityType;

    public DatabaseTask(Activity inputActivity, String inputOperation){
        linkedActivity = new WeakReference<Activity>(inputActivity);
        activityType = inputActivity.getClass();
        operation = inputOperation;
    }

    @Override
    protected List<Prodotto> doInBackground(Prodotto[]... prodotti) {

        //execute the correct operation on database
        switch (operation){
            case "DeleteAll":
                NegozioDatabase.database.clearAllTables();
                return null;
            case "InsertAll":
                NegozioDatabase.database.prodottoDao().insertAll(prodotti[0]);
                return null;
            case "Insert":
                NegozioDatabase.database.prodottoDao().insert(prodotti[0][0]);
                return null;
            case "Update":
                NegozioDatabase.database.prodottoDao().update(prodotti[0]);
                return null;
            case "UpdateStartNewActivity":
                NegozioDatabase.database.prodottoDao().update(prodotti[0]);
                return null;
            case "GetAll":
                return NegozioDatabase.database.prodottoDao().getAll();
            case "GetBuyed":
                return NegozioDatabase.database.prodottoDao().getBuyed();
        }


        return null;
    }

    @Override
    protected void onPostExecute(List<Prodotto> listaProdotti) {
        switch (operation){
            case "DeleteAll":
                if(activityType==BuyProductsActivity.class){
                    ((BuyProductsActivity)linkedActivity.get()).insertAllProducts();
                }
                break;
            case "InsertAll":
                if(activityType==BuyProductsActivity.class){
                    ((BuyProductsActivity)linkedActivity.get()).getAllProducts();;
                }
                break;
            case "Insert":
                if(activityType==BuyProductsActivity.class){
                    ((BuyProductsActivity)linkedActivity.get()).getAllProducts();;;
                }
                break;
            case "Update":
                if(activityType==BuyProductsActivity.class){
                    ((BuyProductsActivity)linkedActivity.get()).closeDatabaseConnection();
                }
                break;
            case "UpdateStartNewActivity":
                if(activityType==BuyProductsActivity.class){
                    ((BuyProductsActivity)linkedActivity.get()).startCustomerCartActivity();
                }
                break;
            case "GetAll":
                if(activityType==BuyProductsActivity.class){
                    ((BuyProductsActivity)linkedActivity.get()).updateRecyclerViewAdapter(listaProdotti);;;
                }
                break;
            case "GetBuyed":
                if(activityType==BuyProductsActivity.class){
                    ((BuyProductsActivity)linkedActivity.get()).updateRecyclerViewAdapter(listaProdotti);;
                }
                if(activityType== CustomerCartActivity.class){
                    ((CustomerCartActivity)linkedActivity.get()).updateRecyclerViewAdapter(listaProdotti);;
                }
                break;
        }

    }
}
