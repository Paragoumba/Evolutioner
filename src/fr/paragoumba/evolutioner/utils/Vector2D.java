package fr.paragoumba.evolutioner.utils;

public class Vector2D {

    public Vector2D(double x, double y){

        this.x = x;
        this.y = y;

    }

    private double x;
    private double y;

    public double getLength(){

        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

    }

    public double getOrientedAngle(){

        return Math.acos(x / getLength());

    }
}
