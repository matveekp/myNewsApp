package ru.matveev.demo.entity;

public enum RatingEnum {

    YANDEX("www.yandex.ru", 10),
    VESTI("www.vesti.ru", 10),
    GARANT("www.garant.ru", 8),
    AIF("www.aif.ru", 7),
    VSESMI("www.vsesmi.ru", 7),
    KOMMERSANT("www.kommersant.ru", 10),
    RBC("www.rbc.ru",10),
    LENTA("www.lenta.ru", 8),

    PANORAMA("panorama.pub", 0);



    private String source;
    private int rating;

    RatingEnum(String source, int rating) {
        this.source = source;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public String getSource() {
        return source;
    }
}
