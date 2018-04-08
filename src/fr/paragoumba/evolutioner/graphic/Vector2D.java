package fr.paragoumba.evolutioner.graphic;

public class Vector2D {

    public Vector2D(int x, int y){

        this.x = x;
        this.y = y;

    }

    public int x, y;

    public void setX(int x) {

        this.x = x;

    }

    public void setY(int y) {

        this.y = y;

    }

    public double getAngle(){

        return Math.atan(y / x);

    }
}
