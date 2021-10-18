package com.gmail.davidecoffaro.productscity.utilclass;

import java.net.URL;

public class Prodotto {
    private String nome;
    private String descrizione;
    private float prezzo;
    private String immagine;

    public Prodotto(String nome, float prezzo){
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
    }

    public Prodotto(String nome, String descrizione, float prezzo){
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
    }

    public Prodotto(String nome, String descrizione, float prezzo, String immagine){
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.immagine = immagine;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public String getImmagine() {
        return immagine;
    }
}
