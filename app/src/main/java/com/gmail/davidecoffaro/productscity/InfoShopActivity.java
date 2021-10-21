package com.gmail.davidecoffaro.productscity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.gmail.davidecoffaro.productscity.utilclass.DataNegozioJSon;
import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.RVProductListShopAdapter;
import com.gmail.davidecoffaro.productscity.utilclass.task.SaveShopFileJSonTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class InfoShopActivity extends AppCompatActivity implements View.OnClickListener{
    Button confirm;
    FloatingActionButton fab;
    CoordinatorLayout cl;
    RecyclerView rv;
    RVProductListShopAdapter rvAdapter;
    DataNegozioJSon negozioJSon;
    EditText mailRider;

    //use of this because startActivityForResult is deprecated
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Handle the returned result
                    if(result.getResultCode()==RESULT_OK){
                        Intent i = result.getData();

                        //check intent from newProductActivity for modified product or new product
                        checkIntentActivity(i);

                        //save shop info on file json
                        negozioJSon.saveShopFileJSon("OnActivityResult");

                    }

                }
            });

    public void startNewProductActivityForResult(Intent i){
        mStartForResult.launch(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_shop);
        Toolbar toolbar = findViewById(R.id.toolbar);

        //set title of the activity's toolbar
        toolbar.setTitle(R.string.info_shop);

        setSupportActionBar(toolbar);

        //create instance DataNegozioJSon from json file "negozio1.json"
        negozioJSon = new DataNegozioJSon(this);
        negozioJSon.getDataNegozioJSon();

        rv = (RecyclerView) findViewById(R.id.recyclerViewProductsList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        rvAdapter = new RVProductListShopAdapter(negozioJSon.getListaprodotti());
        rv.setAdapter(rvAdapter);

        mailRider = (EditText) findViewById(R.id.editTextMailRider);
        mailRider.setText(negozioJSon.getMailrider());

        //add new product's button listener
        fab = findViewById(R.id.addNewProductButton);
        fab.setOnClickListener(this);

        //confirm button listener
        confirm = (Button) findViewById(R.id.buttonConfirm);
        confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //confirm button management
        if(v==confirm){
            Snackbar.make(v, "Salvataggio modifiche in corso", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            //save shop info on file json
            negozioJSon.saveShopFileJSon("OnClick");

        }

        //add new product management
        if(v==fab){
           Intent i = new Intent(this, NewProductActivity.class);
           startNewProductActivityForResult(i);

        }
    }

    private void checkIntentActivity(Intent i){
        if(i.hasExtra("NumberListProduct")){
            //intent from newProductActivity
            String nameProduct = i.getStringExtra("NameProduct");
            String descriptionProduct = i.getStringExtra("DescriptionProduct");
            float priceProduct = i.getFloatExtra("PriceProduct", 0F);
            int numberListProduct = i.getIntExtra("NumberListProduct", -1);
            if(numberListProduct==-1){
                //new product management

                Prodotto newProduct = new Prodotto(nameProduct, descriptionProduct, priceProduct);
                negozioJSon.addNewProduct(newProduct);

                //aggiornamento adapter recycler view per avere la recycler view aggiornata con il
                // nuovo prodotto
                rvAdapter.notifyItemInserted(negozioJSon.getListaprodotti().size()-1);
            }else{
                negozioJSon.modifyExistingProduct(numberListProduct, nameProduct, descriptionProduct, priceProduct);

                //aggiornamento adapter recycler view per avere la recycler view aggiornata con il
                // prodotto modificato
                rvAdapter.notifyItemChanged(numberListProduct);
            }


        }
    }


}