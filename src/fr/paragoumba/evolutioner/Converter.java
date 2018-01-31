package fr.paragoumba.evolutioner;

import static fr.paragoumba.evolutioner.Unit.METER;
import static fr.paragoumba.evolutioner.Unit.PIXEL;

public class Converter {

    private Converter(){}

    public static int convert(Unit unit, Unit unit2, int value){

        if (unit == METER && unit2 == PIXEL){

            return value * 250;

        } else if (unit2 == METER && unit == PIXEL){

            return value / 250;

        }

        return value;
    }

    public static double convert(Unit unit, Unit unit2, double value){

        if (unit == METER && unit2 == PIXEL){

            return value * 250;

        } else if (unit2 == METER && unit == PIXEL){

            return value / 250;

        }

        return value;
    }
}
