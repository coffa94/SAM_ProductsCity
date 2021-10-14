package com.gmail.davidecoffaro.productscity.utilclass;

import java.net.URL;

public class Prodotto {
    private String nome;
    private float prezzo;
    private URL immagine;

    public Prodotto(String nome, float prezzo){
        this.nome = nome;
        this.prezzo = prezzo;
    }



    public Prodotto(String nome, float prezzo, URL immagine){
        this.nome = nome;
        this.prezzo = prezzo;
        this.immagine = immagine;
    }

    public String getNome() {
        return nome;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public URL getImmagine() {
        return immagine;
    }
}
