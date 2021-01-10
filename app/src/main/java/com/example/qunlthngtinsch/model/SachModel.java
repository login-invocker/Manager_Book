package com.example.qunlthngtinsch.model;


import java.io.Serializable;

public class SachModel  implements Serializable {
    private int id;
    private String nameBook;
    private int publishingYear;
    private String typeBook;

    public void setId(int id) {
        this.id = id;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public void setTypeBook(String typeBook) {
        this.typeBook = typeBook;
    }

    public int getId() {
        return id;
    }

    public String getNameBook() {
        return nameBook;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public String getTypeBook() {
        return typeBook;
    }

    public SachModel(String nameBook, int publishingYear, String typeBook) {
        this.nameBook = nameBook;
        this.publishingYear = publishingYear;
        this.typeBook = typeBook;
    }

    public SachModel(int id, String nameBook, int publishingYear, String typeBook) {
        this.id = id;
        this.nameBook = nameBook;
        this.publishingYear = publishingYear;
        this.typeBook = typeBook;
    }

    public void setNameFood(String nameBook) {
        this.nameBook = nameBook;
    }


}
