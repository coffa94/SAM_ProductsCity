package com.gmail.davidecoffaro.productscity.utilclass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.net.URL;

@Entity
public class Prodotto {
    @PrimaryKey(autoGenerate = true)
    public int productid;
    @ColumnInfo(name="nome")
    private String nome;
    @ColumnInfo(name="descrizione")
    private String descrizione;
    @ColumnInfo(name="prezzo")
    private float prezzo;
    @ColumnInfo(name="urlimmagine")
    private String urlimmagine;
    @ColumnInfo(name="quantita")
    private int quantita;

    /*public Prodotto(String nome, float prezzo){
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita=0;
    }*/

    /*public Prodotto(String nome, String descrizione, float prezzo){
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita=0;
    }

     */

    public Prodotto(String nome, String descrizione, float prezzo, String urlimmagine){
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.urlimmagine = urlimmagine;
        this.quantita=0;
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

    public void setUrlImmagine(String immagine) {
        this.urlimmagine = immagine;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
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

    public String getUrlimmagine() {
        return urlimmagine;
    }

    public int getQuantita() {
        return quantita;
    }

}
