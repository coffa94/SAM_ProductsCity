package com.gmail.davidecoffaro.productscity;

import android.content.Context;
import android.os.Bundle;

import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.RVProductListShopAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class InfoShopActivity extends AppCompatActivity implements View.OnClickListener{
    Button confirm;
    FloatingActionButton fab;
    CoordinatorLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_shop);
        Toolbar toolbar = findViewById(R.id.toolbar);

        //set title of the activity's toolbar
        toolbar.setTitle(R.string.info_shop);

        setSupportActionBar(toolbar);


        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerViewProductsList);

        rv.setHasFixedSize(true);

        rv.setLayoutManager(new LinearLayoutManager(this));

        List<Prodotto> listaProdotti = new ArrayList<>();
        listaProdotti.add(new Prodotto("prodotto1", 35f));
        listaProdotti.add(new Prodotto("prodotto2", 2f));
        listaProdotti.add(new Prodotto("prodotto3", 14f));

        RVProductListShopAdapter adapter = new RVProductListShopAdapter(listaProdotti);

        rv.setAdapter(adapter);


        //add new product's button listener
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        //confirm button listener
        confirm = (Button) findViewById(R.id.buttonConfirm);
        confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //confirm button management
        if(v==confirm){
            //tasto conferma cliccato
            /*Snackbar.make(view, "Pulsante conferma cliccato", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                    */

        }

        //add new product management
        if(v==fab){
           /* Snackbar.make(view, "Pulsante aggiunta nuovo prodotto", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                    
            */
        }
    }
}