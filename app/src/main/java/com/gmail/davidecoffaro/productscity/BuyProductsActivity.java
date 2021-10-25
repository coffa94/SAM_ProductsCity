package com.gmail.davidecoffaro.productscity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.davidecoffaro.productscity.utilclass.DataNegozioJSon;
import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.RVBuyProductListAdapter;
import com.gmail.davidecoffaro.productscity.utilclass.databaseclass.NegozioDatabase;
import com.gmail.davidecoffaro.productscity.utilclass.task.DatabaseTask;

import java.util.ArrayList;
import java.util.List;

public class BuyProductsActivity extends AppCompatActivity implements View.OnClickListener{
    TextView totalOrder;
    RecyclerView rv;
    RVBuyProductListAdapter adapter;
    Button next;
    DataNegozioJSon negozioJSon;
    NegozioDatabase db;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_products);

        //setting up toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbarBuyProductList);
        setSupportActionBar(toolbar);

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

        //first execution of onCreate, reset database
        if(savedInstanceState==null){
            DatabaseTask deleteAll = new DatabaseTask(this, "DeleteAll");
            deleteAll.execute(new Prodotto[0]);
        }


        //initialize recyclerView without setting the adapter, it will be set when the asyncTask
        // database finishes the query execution
        rv = (RecyclerView) findViewById(R.id.recyclerViewBuyProductList);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //creation of database with table Prodotto, using abstraction provided by Room Database
        // table and rows of a database are linked to a java object
        db = Room.databaseBuilder(getApplicationContext(),
                NegozioDatabase.class, "database-negozio1").addMigrations(NegozioDatabase.MIGRATION_1_2).build();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //restore information on the rows of the recyclerView with the values saved in the database
        // onSaveInstanceState
        getAllProducts();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //save current quantities in the recycler view in the database to not lose this information
        updateDatabase();

        super.onSaveInstanceState(outState);
    }

    //action bar management
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_buyproductlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_reset){
            int i=0;
            for(Prodotto p : negozioJSon.getListaprodotti()){
                //reset all product's quantity to zero
                p.setQuantita(0);
                //notify adapter of change in its list
                adapter.notifyDataSetChanged();
                i++;
            }

            //reset total order
            totalOrder.setText("0.0");

            return true;
        }else{
            return  super.onOptionsItemSelected(item);
        }
    }

    public void insertAllProducts(){
        Prodotto[] paramProdottoDatabase = new Prodotto[negozioJSon.getListaprodotti().size()];
        paramProdottoDatabase = negozioJSon.getListaprodotti().toArray(paramProdottoDatabase);

        //execute query insert all in a different thread with asyncTask
        DatabaseTask insertAll = new DatabaseTask(this, "InsertAll");
        insertAll.execute(paramProdottoDatabase);
    }

    public void getAllProducts(){
        //async task to get all products in the table Prodotto in database
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

            //if total order is 0 or less, no products was buyed
            if(Float.parseFloat(totalOrder.getText().toString())<=0f){
                Toast.makeText(this, R.string.error_no_product,Toast.LENGTH_SHORT).show();
            }else{
                //save info quantities on database
                updateAndStartCustomerCartActivityDatabase();
            }
        }
    }

    private void updateDatabase(){
        //take listaProdotti from negozioJSon and update table Prodotto in database with these
        // new data (with quantity changed)
        Prodotto[] paramProdottoDatabase = new Prodotto[negozioJSon.getListaprodotti().size()];
        paramProdottoDatabase = negozioJSon.getListaprodotti().toArray(paramProdottoDatabase);

        DatabaseTask update = new DatabaseTask(this, "Update");
        update.execute(paramProdottoDatabase);
    }

    private void updateAndStartCustomerCartActivityDatabase(){
        //take listaProdotti from negozioJSon and update table Prodotto in database with these
        // new data (with quantity changed)
        Prodotto[] paramProdottoDatabase = new Prodotto[negozioJSon.getListaprodotti().size()];
        paramProdottoDatabase = negozioJSon.getListaprodotti().toArray(paramProdottoDatabase);

        DatabaseTask update = new DatabaseTask(this, "UpdateStartNewActivity");
        update.execute(paramProdottoDatabase);
    }

    public void startCustomerCartActivity(){
        //close database connection
        closeDatabaseConnection();

        //start next activity
        Intent i = new Intent(this, CustomerCartActivity.class);
        i.putExtra("EXTRA_RIDER_MAIL", negozioJSon.getMailrider());
        startActivity(i);
    }

    public void closeDatabaseConnection(){
        //close connection to database to free system resources
        db.close();
    }
}