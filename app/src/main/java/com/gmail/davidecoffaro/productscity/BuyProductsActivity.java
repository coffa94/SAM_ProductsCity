package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.RVBuyProductListAdapter;

import java.util.ArrayList;
import java.util.List;

public class BuyProductsActivity extends AppCompatActivity {
    TextView totalOrder;
    RecyclerView rv;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_products);

        totalOrder = (TextView) findViewById(R.id.textViewTotalOrder);
        next = (Button) findViewById(R.id.buttonNext);

        rv = (RecyclerView) findViewById(R.id.recyclerViewBuyProductList);

        rv.setLayoutManager(new LinearLayoutManager(this));

        List<Prodotto> listaProdotti = new ArrayList<Prodotto>();
        listaProdotti.add(new Prodotto("prodotto1", 35f));
        listaProdotti.add(new Prodotto("prodotto2", 2f));
        listaProdotti.add(new Prodotto("prodotto3", 14f));

        RVBuyProductListAdapter adapter = new RVBuyProductListAdapter(listaProdotti);

        rv.setAdapter(adapter);

    }
}