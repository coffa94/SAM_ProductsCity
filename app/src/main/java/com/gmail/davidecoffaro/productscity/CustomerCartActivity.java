package com.gmail.davidecoffaro.productscity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;
import com.gmail.davidecoffaro.productscity.utilclass.RVBuyProductListAdapter;
import com.gmail.davidecoffaro.productscity.utilclass.RVCartProductListAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomerCartActivity extends AppCompatActivity implements View.OnClickListener {
    TextView totalArticles;
    TextView totalOrder;
    RecyclerView rv;
    TextView nameSurnameCustomer;
    TextView completeAddressCustomer;
    Button modify;
    Button back;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_products);

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
        rv = (RecyclerView) findViewById(R.id.recyclerViewCartProductList);

        rv.setLayoutManager(new LinearLayoutManager(this));

        List<Prodotto> listaProdotti = new ArrayList<Prodotto>();
        listaProdotti.add(new Prodotto("prodotto1", 35f));
        listaProdotti.add(new Prodotto("prodotto2", 2f));
        listaProdotti.add(new Prodotto("prodotto3", 14f));

        RVCartProductListAdapter adapter = new RVCartProductListAdapter(listaProdotti);

        rv.setAdapter(adapter);

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
}