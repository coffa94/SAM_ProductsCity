package com.gmail.davidecoffaro.productscity.utilclass;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.EditText;

import com.gmail.davidecoffaro.productscity.R;
import com.gmail.davidecoffaro.productscity.utilclass.task.SaveShopFileJSonTask;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DataNegozioJSon {
    private ArrayList<Prodotto> listaprodotti;
    private String mailrider;
    private WeakReference<Activity> linkedActivity;

    public DataNegozioJSon(){

    }

    public DataNegozioJSon(Activity linkedActivity){
        this.linkedActivity = new WeakReference<Activity>(linkedActivity);

        //arraylist initialization
        listaprodotti = new ArrayList<Prodotto>();

    }

    public DataNegozioJSon(ArrayList<Prodotto> inputListaProdotti, String inputMailRider){
        listaprodotti = inputListaProdotti;
        mailrider = inputMailRider;
    }

    public ArrayList<Prodotto> getListaprodotti() {
        return listaprodotti;
    }

    public void setListaprodotti(ArrayList<Prodotto> listaprodotti) {
        this.listaprodotti = listaprodotti;
    }

    public String getMailrider() {
        return mailrider;
    }

    public void setMailrider(String mailrider) {
        this.mailrider = mailrider;
    }

    public void addNewProduct(Prodotto newProduct){
        listaprodotti.add(newProduct);
    }

    public void modifyExistingProduct(int numberProduct, String newNameProduct, String newDescriptionProduct, float newPrice){
        listaprodotti.get(numberProduct).setNome(newNameProduct);
        listaprodotti.get(numberProduct).setDescrizione(newDescriptionProduct);
        listaprodotti.get(numberProduct).setPrezzo(newPrice);
    }

    public void getDataNegozioJSon(){
        //reading of json file in internal file directory "negozio1.json"
        String stringJSon = readShopFileJSon();

        //convert from json file to dataNegozioJSon data structure (with listaProdotti and mailRider)
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(stringJSon, JsonObject.class);

        Log.d("JSON", jsonObject.toString());

        //gestione listaProdotti, da file json a oggetto java List<Prodotto>
        JsonArray listaProdottiJSon = null;

        listaProdottiJSon = jsonObject.getAsJsonArray("listaprodotti");


        if(listaProdottiJSon!=null){
            //insert each element in the list listaProdottiJSon in the listaProdotti
            for (int i=0; i<listaProdottiJSon.size(); i++){
                //get each jsonElement and insert in the corresponding Prodotto variable

                String nomeProdotto = listaProdottiJSon.get(i).getAsJsonObject().getAsJsonPrimitive("nome").getAsString();
                String descrizioneProdotto = listaProdottiJSon.get(i).getAsJsonObject().getAsJsonPrimitive("descrizione").getAsString();
                float prezzoProdotto = listaProdottiJSon.get(i).getAsJsonObject().getAsJsonPrimitive("prezzo").getAsFloat();

                //nuovo prodotto dai dati presi dall'elemento i-esimo dell'array Prodotto nel file json
                Prodotto newProdotto = new Prodotto(nomeProdotto,descrizioneProdotto, prezzoProdotto);

                //inserimento nella listaProdotti del nuovo prodotto
                listaprodotti.add(newProdotto);

            }
        }

        //gestione mailRider, da file json a oggetto java String
        String mailriderJSon=null;

        mailriderJSon = jsonObject.getAsJsonPrimitive("mailrider").getAsString();

        if(mailriderJSon!=null){
            mailrider = mailriderJSon;
        }

    }

    private String readShopFileJSon(){

        File fileToOpen = new File(linkedActivity.get().getFilesDir(), "negozio1.json");
        if(!fileToOpen.exists()){
            createFileJSon();
            fileToOpen = new File(linkedActivity.get().getFilesDir(), "negozio1.json");
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

    private void createFileJSon(){
        AssetManager assetManager = linkedActivity.get().getAssets();
        try (DataInputStream fileToCopy = new DataInputStream (new BufferedInputStream(assetManager.open("negozio1.json")));
             BufferedReader fileReaderToCopy = new BufferedReader(new InputStreamReader(fileToCopy));
             FileWriter newFile = new FileWriter(linkedActivity.get().getFilesDir() + "/negozio1.json")){
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

    public void saveShopFileJSon(String methodCalling){
        //se la mail del rider è stata cambiata la aggiorno
        setMailrider(((EditText)linkedActivity.get().findViewById(R.id.editTextMailRider)).getText().toString());

        SaveShopFileJSonTask asyncSaveShopFileJSonTask = new SaveShopFileJSonTask(linkedActivity.get(), methodCalling);
        asyncSaveShopFileJSonTask.execute(this);

    }
}
