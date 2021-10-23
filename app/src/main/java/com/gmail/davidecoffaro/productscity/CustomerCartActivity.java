package com.gmail.davidecoffaro.productscity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.RVBuyProductListAdapter;
import com.gmail.davidecoffaro.productscity.utilclass.RVCartProductListAdapter;
import com.gmail.davidecoffaro.productscity.utilclass.databaseclass.NegozioDatabase;
import com.gmail.davidecoffaro.productscity.utilclass.task.DatabaseTask;
import com.gmail.davidecoffaro.productscity.utilclass.task.GetTotalDatabaseTask;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomerCartActivity extends AppCompatActivity implements View.OnClickListener {
    TextView totalArticles;
    TextView totalOrder;
    RecyclerView rv;
    RVCartProductListAdapter adapter;
    TextView nameSurnameCustomer;
    TextView completeAddressCustomer;
    String phoneNumberCustomer;
    Button modify;
    Button back;
    Button confirm;

    NegozioDatabase db;
    ArrayList<Prodotto> listaProdotti;
    String mailRider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart);

        totalArticles = (TextView) findViewById(R.id.textViewTotalArticlesCartSummary);
        totalOrder = (TextView) findViewById(R.id.textViewTotalOrderCartSummary);
        nameSurnameCustomer = (TextView) findViewById(R.id.textViewLabelNameSurnameCustomer);
        completeAddressCustomer = (TextView) findViewById(R.id.textViewLabelCompleteAddressCustomer);

        modify =  (Button) findViewById(R.id.buttonModify);
        modify.setOnClickListener(this);
        back =  (Button) findViewById(R.id.buttonBack);
        back.setOnClickListener(this);
        confirm = (Button) findViewById(R.id.buttonConfirm);
        confirm.setOnClickListener(this);

        //creation of database with table Prodotto, using abstraction provided by Room Database
        // table and rows of a database are linked to a java object
        db = Room.databaseBuilder(getApplicationContext(),
                NegozioDatabase.class, "database-negozio1").addMigrations(NegozioDatabase.MIGRATION_1_2).build();

        //initialization of recyclerView
        rv = (RecyclerView) findViewById(R.id.recyclerViewCartProductList);

        rv.setLayoutManager(new LinearLayoutManager(this));

        DatabaseTask getBuyed = new DatabaseTask(this, "GetBuyed");
        getBuyed.execute(new Prodotto[0]);

        //get mail rider in intent from BuyProductsActivity
        Intent i = getIntent();
        if(i.hasExtra("EXTRA_RIDER_MAIL")){
            mailRider = i.getStringExtra("EXTRA_RIDER_MAIL");
        }else{
            mailRider = "";
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        //get customer info from sharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preferences_file), Context.MODE_PRIVATE);
        //initialize views with customerData in sharedPreferences, if they are set
        if (sharedPreferences.contains("NameCustomer")) {
            nameSurnameCustomer.setText(sharedPreferences.getString(getString(R.string.preferences_name_customer), ""));
            completeAddressCustomer.setText(sharedPreferences.getString(getString(R.string.preferences_address_customer), ""));
            phoneNumberCustomer = sharedPreferences.getString(getString(R.string.preferences_phone_customer),"");
        }
    }

    @Override
    public void onClick(View v) {
        if(v==modify){
            //click button modify
            Intent i = new Intent(this, InsertCustomerDataActivity.class);
            i.putExtra("LabelInfoCliente", "Modifica dati cliente");
            startActivity(i);
        }
        if(v==back){
            //click button back
            finish();
        }
        if(v==confirm){
            //click button confirm

            //check internet connection
            ConnectivityManager cm =
                    (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();


            if(isConnected==false){
                Toast.makeText(this, R.string.error_internet_connection,Toast.LENGTH_SHORT).show();
            }else{

                //internet connection OK
                //set intent to send an email to mailRider with order info
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("mailto:"));
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL, new String[] {mailRider});
                i.putExtra(Intent.EXTRA_SUBJECT, "Ordine ProductsCity");

                //body mail construction
                StringBuilder bodyMail = new StringBuilder();
                bodyMail.append("Ordine da consegnare.\n");
                bodyMail.append("Informazioni cliente: \n");
                bodyMail.append(nameSurnameCustomer.getText().toString() + "\n");
                bodyMail.append(completeAddressCustomer.getText().toString() + "\n");
                bodyMail.append(phoneNumberCustomer + "\n");
                bodyMail.append("\n");
                bodyMail.append("Articoli da consegnare: \n");

                //set body of the mail with products selected from customer
                for (Prodotto p : listaProdotti){
                    bodyMail.append("   - " + p.getNome() + ", " + p.getQuantita() + "\n");
                }
                bodyMail.append("\n");

                bodyMail.append("Totale articoli: " + totalArticles.getText().toString() + "\n");
                bodyMail.append("Totale ordine: € " + totalOrder.getText().toString() + "\n");

                i.putExtra(Intent.EXTRA_TEXT, bodyMail.toString());
                startActivity(i);
            }


        }
    }

    public void updateRecyclerViewAdapter(List<Prodotto> listaProdotti){
        this.listaProdotti = (ArrayList<Prodotto>)listaProdotti;

        adapter = new RVCartProductListAdapter(listaProdotti);

        rv.setAdapter(adapter);

        GetTotalDatabaseTask getTotalArticles = new GetTotalDatabaseTask(this, "GetTotalArticles");
        getTotalArticles.execute();
    }

    public void updateTotalOrder(float queryTotalOrder){
        totalOrder.setText(String.valueOf(queryTotalOrder));


    }

    public void updateTotalArticles(int queryTotalArticles){
        totalArticles.setText(String.valueOf(queryTotalArticles));

        GetTotalDatabaseTask getTotalOrder = new GetTotalDatabaseTask(this, "GetTotalOrder");
        getTotalOrder.execute();
    }
}