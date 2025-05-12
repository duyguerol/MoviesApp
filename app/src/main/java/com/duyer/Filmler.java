package com.duyer;

import java.io.Serializable;

public class Filmler implements Serializable {
    private int film_id;
    private String film_ad;
    private int film_yil;
    private double film_imdb;
    private String film_yonetmen;
    private byte[] film_resim;
    private int kategori_id;

    public Filmler() {
    }

    public Filmler(int film_id, String film_ad, int film_yil, double film_imdb, String film_yonetmen, byte[] film_resim, int kategori_id) {
        this.film_id = film_id;
        this.film_ad = film_ad;
        this.film_yil = film_yil;
        this.film_imdb = film_imdb;
        this.film_yonetmen = film_yonetmen;
        this.film_resim = film_resim;
        this.kategori_id = kategori_id;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public String getFilm_ad() {
        return film_ad;
    }

    public void setFilm_ad(String film_ad) {
        this.film_ad = film_ad;
    }

    public int getFilm_yil() {
        return film_yil;
    }

    public void setFilm_yil(int film_yil) {
        this.film_yil = film_yil;
    }

    public double getFilm_imdb() {
        return film_imdb;
    }

    public void setFilm_imdb(double film_imdb) {
        this.film_imdb = film_imdb;
    }

    public String getFilm_yonetmen() {
        return film_yonetmen;
    }

    public void setFilm_yonetmen(String film_yonetmen) {
        this.film_yonetmen = film_yonetmen;
    }

    public byte[] getFilm_resim() {
        return film_resim;
    }

    public void setFilm_resim(byte[] film_resim) {
        this.film_resim = film_resim;
    }

    public int getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(int kategori_id) {
        this.kategori_id = kategori_id;
    }
}






