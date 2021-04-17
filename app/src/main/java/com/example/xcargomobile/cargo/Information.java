package com.example.xcargomobile.cargo;

public class Information {

    private double Length;
    private double Width;
    private double Height;
    private double Price;


    public Information(double length, double width, double height, double price) {
        Length = length;
        Width = width;
        Height = height;
        Price = price;
    }

    public double getLength() {
        return Length;
    }

    public void setLength(double length) {
        Length = length;
    }

    public double getWidth() {
        return Width;
    }

    public void setWidth(double width) {
        Width = width;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}
