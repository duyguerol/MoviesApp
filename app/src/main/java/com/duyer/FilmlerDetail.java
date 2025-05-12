package com.duyer;

import java.io.Serializable;

public class FilmlerDetail implements Serializable {
    private Filmler film_id;
    private Filmler film_ad;
    private int film_yil;
    private String film_resim;
    private String yonetmen_ad;

    public FilmlerDetail() {
    }

    public FilmlerDetail(Filmler film_id, Filmler film_ad, int film_yil, String film_resim, String yonetmen_ad) {
        this.film_id = film_id;
        this.film_ad = film_ad;
        this.film_yil = film_yil;
        this.film_resim = film_resim;
        this.yonetmen_ad = yonetmen_ad;
    }

    public Filmler getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Filmler film_id) {
        this.film_id = film_id;
    }

    public Filmler getFilm_ad() {
        return film_ad;
    }

    public void setFilm_ad(Filmler film_ad) {
        this.film_ad = film_ad;
    }

    public int getFilm_yil() {
        return film_yil;
    }

    public void setFilm_yil(int film_yil) {
        this.film_yil = film_yil;
    }

    public String getFilm_resim() {
        return film_resim;
    }

    public void setFilm_resim(String film_resim) {
        this.film_resim = film_resim;
    }

    public String getYonetmen_ad() {
        return yonetmen_ad;
    }

    public void setYonetmen_ad(String yonetmen_ad) {
        this.yonetmen_ad = yonetmen_ad;
    }
}

