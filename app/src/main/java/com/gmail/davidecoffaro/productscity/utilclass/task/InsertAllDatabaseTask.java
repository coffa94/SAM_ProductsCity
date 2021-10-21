package com.gmail.davidecoffaro.productscity.utilclass.task;

import android.app.Activity;
import android.os.AsyncTask;

import com.gmail.davidecoffaro.productscity.BuyProductsActivity;
import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.databaseclass.NegozioDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

public class InsertAllDatabaseTask extends AsyncTask<Prodotto[], Void, Void> {
    WeakReference<BuyProductsActivity> linkedActivity;

    public InsertAllDatabaseTask(BuyProductsActivity inputActivity){
        linkedActivity = new WeakReference<BuyProductsActivity>(inputActivity);
    }

    @Override
    protected Void doInBackground(Prodotto[]... prodotti) {

        NegozioDatabase.database.prodottoDao().insertAll(prodotti[0]);

        return (Void) null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        linkedActivity.get().getAllProducts();
    }
}
