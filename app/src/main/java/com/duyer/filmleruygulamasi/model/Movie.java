package com.duyer.filmleruygulamasi.model;

public class Movie {
    private int id;
    private String name;
    private int year;
    private double imdbRating;
    private String director;
    private byte[] image;
    private int categoryId;

    public Movie(int id, String name, int year, double imdbRating, String director, byte[] image, int categoryId) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.imdbRating = imdbRating;
        this.director = director;
        this.image = image;
        this.categoryId = categoryId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getImdbRating() { return imdbRating; }
    public void setImdbRating(double imdbRating) { this.imdbRating = imdbRating; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
} 