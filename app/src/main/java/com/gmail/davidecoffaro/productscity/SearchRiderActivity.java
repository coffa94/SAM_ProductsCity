package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchRiderActivity extends AppCompatActivity implements View.OnClickListener {
    EditText codiceRider;
    Button conferma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rider);

        codiceRider = (EditText) findViewById(R.id.editTextCodiceRider);
        conferma = (Button) findViewById(R.id.buttonConferma);

        conferma.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==conferma){
            //TODO click on button conferma
            //check file on drive "riders.txt" scorrendo le righe e guardando se è presente il rider scritto dentro l'editText codiceRider
            if(codiceRider.getText().toString().equals("rider1")){
                //go to pathdeliveryactivity
                Intent i = new Intent(this, PathDeliveryActivity.class);
                startActivity(i);
            }else{
                Toast.makeText(this, R.string.error_codice_rider,Toast.LENGTH_SHORT).show();
            }
        }
    }
}