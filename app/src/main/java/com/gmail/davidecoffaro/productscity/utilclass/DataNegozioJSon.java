package com.gmail.davidecoffaro.productscity.utilclass;

import java.util.ArrayList;

public class DataNegozioJSon {
    private ArrayList<Prodotto> listaprodotti;
    private String mailrider;

    public DataNegozioJSon(){

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
}
