package com.gmail.davidecoffaro.productscity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.RVBuyProductListAdapter;
import com.gmail.davidecoffaro.productscity.utilclass.RVCartProductListAdapter;
import com.gmail.davidecoffaro.productscity.utilclass.databaseclass.NegozioDatabase;
import com.gmail.davidecoffaro.productscity.utilclass.task.DatabaseTask;

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
    Button modify;
    Button back;
    Button confirm;

    NegozioDatabase db;

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

    }

    @Override
    public void onClick(View v) {
        if(v==modify){
            //TODO click button modify
        }
        if(v==back){
            //TODO click button back
        }
        if(v==confirm){
            //TODO click button confirm
        }
    }

    public void updateRecyclerViewAdapter(List<Prodotto> listaProdotti){
        adapter = new RVCartProductListAdapter(listaProdotti);

        rv.setAdapter(adapter);
    }
}