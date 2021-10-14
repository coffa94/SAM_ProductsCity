package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewProductActivity extends AppCompatActivity implements View.OnClickListener{
    EditText nameProduct;
    EditText descriptionProduct;
    EditText priceProduct;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        nameProduct = (EditText) findViewById(R.id.editTextNameProduct);
        descriptionProduct = (EditText) findViewById(R.id.editTextDescriptionProduct);
        priceProduct = (EditText) findViewById(R.id.editTextPriceProduct);
        confirm = (Button) findViewById(R.id.buttonConfirm);

        confirm.setOnClickListener(this);
    }

    public void onClick(View v){
        //TODO conferma per salvare il nuovo prodotto (nel file json e aggiornamento struttura dati
        // datiNegozioJSon) prendendo i dati dagli editText
    }
}