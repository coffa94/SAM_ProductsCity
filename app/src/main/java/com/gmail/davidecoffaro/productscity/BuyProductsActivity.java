package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.davidecoffaro.productscity.utilclass.DataNegozioJSon;
import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.RVBuyProductListAdapter;
import com.gmail.davidecoffaro.productscity.utilclass.databaseclass.NegozioDatabase;
import com.gmail.davidecoffaro.productscity.utilclass.task.DatabaseTask;
import com.gmail.davidecoffaro.productscity.utilclass.task.InsertAllDatabaseTask;

import java.util.ArrayList;
import java.util.List;

public class BuyProductsActivity extends AppCompatActivity implements View.OnClickListener{
    TextView totalOrder;
    RecyclerView rv;
    RVBuyProductListAdapter adapter;
    Button next;
    DataNegozioJSon negozioJSon;
    NegozioDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_products);

        totalOrder = (TextView) findViewById(R.id.textViewTotalOrder);
        next = (Button) findViewById(R.id.buttonNext);

        next.setOnClickListener(this);

        //getting information shop from file negozio1.json
        negozioJSon = new DataNegozioJSon(this);
        negozioJSon.getDataNegozioJSon();

        //creation of database with table Prodotto, using abstraction provided by Room Database
        // table and rows of a database are linked to a java object
        db = Room.databaseBuilder(getApplicationContext(),
                NegozioDatabase.class, "database-negozio1").addMigrations(NegozioDatabase.MIGRATION_1_2).build();

        DatabaseTask deleteAll = new DatabaseTask(this, "DeleteAll");
        deleteAll.execute(new Prodotto[0]);

        //initialize recyclerView without setting the adapter, it will be setted when the asyncTask
        // database finishes the query execution
        rv = (RecyclerView) findViewById(R.id.recyclerViewBuyProductList);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    public void insertAllProducts(){
        Prodotto[] paramProdottoDatabase = new Prodotto[negozioJSon.getListaprodotti().size()];
        paramProdottoDatabase = negozioJSon.getListaprodotti().toArray(paramProdottoDatabase);

        //execute query insert all in a different thread with asyncTask
        DatabaseTask insertAll = new DatabaseTask(this, "InsertAll");
        insertAll.execute(paramProdottoDatabase);
    }

    public void getAllProducts(){
        DatabaseTask getAll = new DatabaseTask(this, "GetAll");
        getAll.execute(new Prodotto[0]);
    }

    public void updateRecyclerViewAdapter(List<Prodotto> listaProdotti){
        //set lista prodotti on negozioJSon as the same of the recycler view adapter, to link
        // together the changes in the variables
        negozioJSon.setListaprodotti((ArrayList<Prodotto>) listaProdotti);

        //creation of adapter of recycler view from listaProdotti returned from the query
        adapter = new RVBuyProductListAdapter(listaProdotti);

        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v==next){
            //click on next button
            //tried without make an update query to see if the changes in the listaProdotti sono
            // portati automaticamente dentro il database essendo collegati la tabella e gli oggetti
            // di tipo Prodotto all'interno di essa, non funziona perché dentro la tabella è stata
            // copiata la nuova lista di tipo Prodotto[] e inoltre l'adapter della recycler view è
            // una ArrayList<Prodotti> creati a partire dalla query di database, le due liste non
            // coincidono, quindi ho bisogno dell'update righe database

            //if total order is 0, no products was buyed
            if(totalOrder.getText().toString().equals("0.0")){
                Toast.makeText(this, R.string.error_no_product,Toast.LENGTH_SHORT).show();
            }else{
                //take listaProdotti from negozioJSon and update table Prodotto in database with these
                // new data (with quantity changed)
                Prodotto[] paramProdottoDatabase = new Prodotto[negozioJSon.getListaprodotti().size()];
                paramProdottoDatabase = negozioJSon.getListaprodotti().toArray(paramProdottoDatabase);

                DatabaseTask update = new DatabaseTask(this, "Update");
                update.execute(paramProdottoDatabase);
            }



        }
    }

    public void startCustomerCartActivity(){
        //start next activity
        Intent i = new Intent(this, CustomerCartActivity.class);
        i.putExtra("EXTRA_RIDER_MAIL", negozioJSon.getMailrider());
        startActivity(i);
    }
}