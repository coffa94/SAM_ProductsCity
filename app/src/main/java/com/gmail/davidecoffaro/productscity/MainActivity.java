package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gmail.davidecoffaro.productscity.supportclass.ProductListActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonNegozio;
    private Button buttonCliente;
    private Button buttonRider;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inizializzazione riferimenti ai bottoni
        buttonNegozio = (Button) findViewById(R.id.buttonShop);
        buttonCliente = (Button) findViewById(R.id.buttonCustomer);
        buttonRider = (Button) findViewById(R.id.buttonRider);

        //inizializzazione riferimento a oggetto SharedPreferences
        // utilizzo di un unico file preferences per questa activity
        sharedPreferences = getPreferences(MODE_PRIVATE);

        //prendo la variabile nelle preferenze con nome TipoUtente
        int tipoUtente = sharedPreferences.getInt(getString(R.string.preferences_user_type),0);

        //collego i listener agli onClick dei 3 bottoni negozio, cliente e rider
        buttonNegozio.setOnClickListener(this);
        buttonCliente.setOnClickListener(this);
        buttonRider.setOnClickListener(this);

        //controllo se il tipoUtente era già stato scelto in precedenza (tipoUtente=0 non ancora scelto)
        //tipoUtente=1 negozio
        if(tipoUtente==1){
            //lancio la nuova activity per visualizzare l'activity per l'inserimento del codice negozio
            Intent intent = new Intent(this, SearchShopActivity.class);
            startActivity(intent);
        }

        //tipoUtente=2 cliente
        if(tipoUtente==2){
            //lancio la nuova activity per far vedere al cliente la lista dei prodotti da acquistare dal negozio
            Intent intent = new Intent(this, BuyProductsActivity.class);
            startActivity(intent);
        }

        //tipoUtente=3 rider
        if(tipoUtente==3){
            //lancio la nuova activity per far vedere al rider l'activity per l'inserimento del codice rider
            Intent intent = new Intent(this, SearchRiderActivity.class);
            startActivity(intent);
        }

    }

    //implementazione dell'interfaccia OnClickListener distinguendo per la classe View v su cui viene effettuato il click
    public void onClick(View v){
        if((Button) v == buttonNegozio){
            //cliccato sul pulsante negozio
            //setto variabile delle impostazioni tipoUtente a 1=negozio e faccio il commit della preferenza
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.preferences_user_type), 1);
            editor.apply();
            //lancio la nuova activity collegato al pulsante negozio per inserire il codice negozio
            Intent intent = new Intent(this, SearchShopActivity.class);
            startActivity(intent);
        }

        if((Button) v == buttonCliente){
            //cliccato sul pulsante cliente
            //setto variabile delle impostazioni tipoUtente a 2=cliente e faccio il commit della preferenza
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.preferences_user_type), 2);
            editor.apply();
            //lancio la nuova activity collegata al pulsante cliente per inserire i dati del cliente
            Intent intent = new Intent(this, InsertCustomerDataActivity.class);
            startActivity(intent);
        }

        if((Button)v == buttonRider){
            //cliccato sul pulsante rider
            //setto variabile delle impostazioni tipoUtente a 3=rider e faccio il commit della preferenza
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.preferences_user_type), 3);
            editor.apply();
            //lancio la nuova activity collegata al pulsante rider per inserire il codice del rider
            Intent intent = new Intent(this, SearchRiderActivity.class);
            startActivity(intent);
        }
    }
}