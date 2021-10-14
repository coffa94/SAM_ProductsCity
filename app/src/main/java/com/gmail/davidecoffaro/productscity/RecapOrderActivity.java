package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.RVCartProductListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecapOrderActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView rv;
    TextView dateTimeDelivery;
    TextView completeAddressDelivery;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap_order);

        //recycler view management
        rv = (RecyclerView) findViewById(R.id.recyclerViewRecapOrder);
        rv.setLayoutManager(new LinearLayoutManager(this));

        List<Prodotto> listaProdotti = new ArrayList<Prodotto>();
        listaProdotti.add(new Prodotto("prodotto1", 35f));
        listaProdotti.add(new Prodotto("prodotto2", 2f));
        listaProdotti.add(new Prodotto("prodotto3", 14f));

        RVCartProductListAdapter adapter = new RVCartProductListAdapter(listaProdotti);

        rv.setAdapter(adapter);


        dateTimeDelivery = (TextView) findViewById(R.id.textViewLabelDateTimeDelivery);
        completeAddressDelivery = (TextView) findViewById(R.id.textViewAddressDelivery);

        home = (Button) findViewById(R.id.buttonHome);
        home.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==home){
            //TODO click on button home
        }
    }
}