package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class SearchShopActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonConferma;
    private EditText editTextCodiceNegozio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop);

        //reference to the correct view
        buttonConferma = (Button) findViewById(R.id.buttonConferma);
        editTextCodiceNegozio = (EditText) findViewById(R.id.editTextCodiceNegozio);

        //set listener on the buttonConferma
        buttonConferma.setOnClickListener(this);

        Intent i = this.getIntent();
        if(i.hasExtra("Saved")){
            if(i.getBooleanExtra("Saved", Boolean.FALSE)){
                Snackbar.make(buttonConferma,"Salvataggio completato", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v==buttonConferma){

            //no internet connection
            //TODO gestire controllo connessione internet

            if(!(editTextCodiceNegozio.getText().toString().equals("negozio1"))){
                Toast.makeText(this, R.string.error_codice_negozio,Toast.LENGTH_SHORT).show();
            }else{
                //TODO scarica e importa file json relativo al negozio per avere lista dei prodotti e mail del rider

                //display new activity
                Intent i = new Intent(this, InfoShopActivity.class);
                startActivity(i);
            }
        }
    }
}