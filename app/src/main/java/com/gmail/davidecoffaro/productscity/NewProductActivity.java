package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class NewProductActivity extends AppCompatActivity implements View.OnClickListener{
    EditText nameProduct;
    EditText descriptionProduct;
    EditText priceProduct;
    EditText urlImageProduct;
    Button confirm;
    int numberModifingProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        nameProduct = (EditText) findViewById(R.id.editTextNameProduct);
        descriptionProduct = (EditText) findViewById(R.id.editTextDescriptionProduct);
        priceProduct = (EditText) findViewById(R.id.editTextPriceProduct);
        urlImageProduct = (EditText) findViewById(R.id.editTextUrlImage);

        confirm = (Button) findViewById(R.id.buttonConfirm);

        confirm.setOnClickListener(this);

        checkIntentActivity(getIntent());
    }

    public void onClick(View v){
        //conferma prendendo i dati dagli editText e inserendoli nell'intent passato come risultato
        // a infoShopActivity
        if(v==confirm){
            //check name product and price correct values
            if(nameProduct.getText().toString().equals("")){
                Snackbar.make(v, "Nome prodotto mancante", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            if(priceProduct.getText().toString().equals("")){
                Snackbar.make(v, "Prezzo prodotto mancante", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            if (Float.parseFloat(priceProduct.getText().toString())<=0f){
                Snackbar.make(v, "Prezzo prodotto uguale a zero", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            //check url image not empty and that is a .png file
            if(urlImageProduct.getText().toString().equals("")){
                Snackbar.make(v, "Immagine prodotto mancante", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            if(!(urlImageProduct.getText().toString().contains(".png"))){
                Snackbar.make(v, "Immagine prodotto deve essere in formato .PNG", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            /*
             * if(!(urlImageProduct.getText().toString().equals("")) && !(urlImageProduct.getText().toString().contains(".png"))){
                Snackbar.make(v, "Immagine prodotto deve essere in formato .PNG", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }*/

            Intent i = new Intent(v.getContext(), InfoShopActivity.class);
            i.putExtra("NameProduct" , nameProduct.getText().toString());
            i.putExtra("DescriptionProduct" , descriptionProduct.getText().toString());
            i.putExtra("PriceProduct", Float.parseFloat(priceProduct.getText().toString()));
            i.putExtra("UrlImageProduct", urlImageProduct.getText().toString());
            i.putExtra("NumberListProduct", numberModifingProduct);
            setResult(RESULT_OK, i);
            finish();
        }
    }

    public void checkIntentActivity(Intent i){
        if(i.hasExtra("NameProduct")){
            //intent has information of product clicked in infoShopActivity's recycler view
            // initialization of views
            nameProduct.setText(i.getStringExtra("NameProduct"));
            descriptionProduct.setText(i.getStringExtra("DescriptionProduct"));
            priceProduct.setText(i.getStringExtra("PriceProduct"));
            urlImageProduct.setText(i.getStringExtra("UrlImageProduct"));

            //number modifing product in the list of the shop
            numberModifingProduct = i.getIntExtra("NumberListProduct", 0);

            //change title label
            ((TextView)findViewById(R.id.textViewLabelNewProduct)).setText("Modifica prodotto");
        }else{
            numberModifingProduct = -1;
        }
    }
}