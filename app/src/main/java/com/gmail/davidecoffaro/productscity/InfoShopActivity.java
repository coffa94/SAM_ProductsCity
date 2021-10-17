package com.gmail.davidecoffaro.productscity;

import android.content.res.AssetManager;
import android.os.Bundle;

import com.gmail.davidecoffaro.productscity.utilclass.DataNegozioJSon;
import com.gmail.davidecoffaro.productscity.utilclass.RVProductListShopAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

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
    DataNegozioJSon negozioJSon;
    EditText mailRider;

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

        //create instance DataNegozioJSon from json file "negozio1.json"
        negozioJSon = new DataNegozioJSon();
        getDataNegozioJSon(negozioJSon);

        RVProductListShopAdapter adapter = new RVProductListShopAdapter(negozioJSon.getListaprodotti());

        rv.setAdapter(adapter);

        mailRider = (EditText) findViewById(R.id.editTextMailRider);
        mailRider.setText(negozioJSon.getMailrider());

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

    public void getDataNegozioJSon(DataNegozioJSon negozioJSon){
        //reading of json file in internal file directory "negozio1.json"
        String stringJSon = readShopFileJSon();

        Gson gson = new Gson();
        DataNegozioJSon negozioFromJSon = gson.fromJson(stringJSon, DataNegozioJSon.class);

        //set read values from json file to negozioJSon DataNegozioJSon data structure
        negozioJSon.setListaprodotti(negozioFromJSon.getListaprodotti());
        negozioJSon.setMailrider(negozioFromJSon.getMailrider());
    }

    public String readShopFileJSon(){

        File fileToOpen = new File(getFilesDir(), "negozio1.json");
        if(!fileToOpen.exists()){
            createFileJSon();
            fileToOpen = new File(getFilesDir(), "negozio1.json");
        }

        StringBuilder stringBuilder = new StringBuilder();
        try(BufferedReader readJsonFile = new BufferedReader(new FileReader(fileToOpen))){
            String line = readJsonFile.readLine();
            while(line!= null){
                //stringBuilder.append(line).append("\n");
                stringBuilder.append(line);
                line = readJsonFile.readLine();
            }
        } catch(FileNotFoundException e){
            Log.d("FileNotFound", "Error: file negozio1.json not found");
        }catch(IOException e){
            Log.d("IO exception" , "Error: IO exception");
        }

        return stringBuilder.toString();
    }

    public void createFileJSon(){
        AssetManager assetManager = getAssets();
        try (DataInputStream fileToCopy = new DataInputStream (new BufferedInputStream(assetManager.open("negozio1.json")));
             BufferedReader fileReaderToCopy = new BufferedReader(new InputStreamReader(fileToCopy));
             FileWriter newFile = new FileWriter(getFilesDir() + "/negozio1.json")){
            String line = fileReaderToCopy.readLine();
            while(line!= null){
                //newFile.write(line + "\n");
                newFile.write(line);

                newFile.flush();
                line = fileReaderToCopy.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}