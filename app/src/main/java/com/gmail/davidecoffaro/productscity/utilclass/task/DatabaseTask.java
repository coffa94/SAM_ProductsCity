package com.gmail.davidecoffaro.productscity.utilclass.task;

import android.os.AsyncTask;

import com.gmail.davidecoffaro.productscity.BuyProductsActivity;
import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.databaseclass.NegozioDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

public class DatabaseTask extends AsyncTask<Prodotto[], Void, List<Prodotto>> {
    WeakReference<BuyProductsActivity> linkedActivity;
    String operation;

    public DatabaseTask(BuyProductsActivity inputActivity, String inputOperation){
        linkedActivity = new WeakReference<BuyProductsActivity>(inputActivity);
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
                linkedActivity.get().insertAllProducts();
                break;
            case "InsertAll":
                linkedActivity.get().getAllProducts();
                break;
            case "Insert":
                linkedActivity.get().getAllProducts();
                break;
            case "GetAll":
                linkedActivity.get().updateRecyclerViewAdapter(listaProdotti);
                break;
            case "GetBuyed":
                linkedActivity.get().updateRecyclerViewAdapter(listaProdotti);
        }

    }
}
