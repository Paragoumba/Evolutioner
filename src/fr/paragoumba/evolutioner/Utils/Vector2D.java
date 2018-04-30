package fr.paragoumba.evolutioner.Utils;

public class Vector2D {

    public Vector2D(int x, int y){

        this.x = x;
        this.y = y;

    }

    public int x;
    public int y;

    public double getLength(){

        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

    }

    public double getOrientedAngle(){

        return Math.acos(x / getLength());

    }
}
