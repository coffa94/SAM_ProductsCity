package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.gmail.davidecoffaro.productscity.utilclass.DataNegozioJSon;
import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.RVBuyProductListAdapter;
import com.gmail.davidecoffaro.productscity.utilclass.databaseclass.NegozioDatabase;
import com.gmail.davidecoffaro.productscity.utilclass.task.DatabaseTask;
import com.gmail.davidecoffaro.productscity.utilclass.task.InsertAllDatabaseTask;

import java.util.ArrayList;
import java.util.List;

public class BuyProductsActivity extends AppCompatActivity {
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
        adapter = new RVBuyProductListAdapter(listaProdotti);

        rv.setAdapter(adapter);
    }
}