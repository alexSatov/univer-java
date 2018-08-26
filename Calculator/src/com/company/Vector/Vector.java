package com.company.Vector;

import com.company.Calculator.Calculator;
import com.company.Calculator.Complex;

import java.util.Arrays;
import java.util.Collections;


public class Vector {
    private Complex[] coords;

    public Vector(Complex... coords) { this.coords = coords; }

    public Vector(String strVector){
        strVector = strVector.replace(" ", "");
        boolean minusFirst = strVector.charAt(0) == '-';
        if (minusFirst) strVector = strVector.substring(1);

        strVector = strVector.substring(1, strVector.length() - 1);
        String[] strCoords = strVector.split(",");
        coords = new Complex[strCoords.length];
        for(int i = 0; i < strCoords.length; i++)
            coords[i] = new Complex(Calculator.instance.calculate(strCoords[i]));
        if (minusFirst) this.coords = multiply(new Complex(-1, 0)).coords;
    }

    public boolean isZeroVector(){
        Complex zero = new Complex(0, 0);
        for (Complex coord : coords)
            if (!coord.equals(zero))
                return false;
        return true;
    }

    public Vector add (Vector other)
    {
        Complex[] maxCoords = moreDimensions(other) ? coords : other.coords;
        Complex[] minCoords = !moreDimensions(other) ? coords : other.coords;

        for (int i = 0; i < minCoords.length; i++)
            maxCoords[i] = maxCoords[i].add(minCoords[i]);

        return new Vector(maxCoords);
    }

    public Vector subtract(Vector other)
    {
        Complex[] maxCoords = moreDimensions(other) ? coords : other.coords;

        if (coords.length < other.coords.length) {
            for (int i = 0; i < other.coords.length; i++)
                other.coords[i] = other.coords[i].multiply(new Complex(-1, 0));
            for (int i = 0; i < coords.length; i++)
                maxCoords[i] = maxCoords[i].add(coords[i]);
        }
        else {
            for (int i = 0; i < other.coords.length; i++)
                maxCoords[i] = maxCoords[i].subtract(other.coords[i]);
        }
        return new Vector(maxCoords);
    }

    public Complex scalar (Vector other)
    {
        Complex[] maxCoords = moreDimensions(other) ? coords : other.coords;
        Complex[] minCoords = !moreDimensions(other) ? coords : other.coords;

        Complex sum = new Complex(0, 0);
        for (int i = 0; i < minCoords.length; i++)
            sum = sum.add(maxCoords[i].multiply(minCoords[i]));

        return sum;
    }

    public Vector multiply (Complex multiplier)
    {
        Complex[] newCoords = new Complex[coords.length];
        for (int i = 0; i < coords.length; i++)
            newCoords[i] = coords[i].multiply(multiplier);

        return new Vector(newCoords);
    }

    public Vector divide (Complex multiplier)
    {
        if (multiplier.isZero())
            throw new IllegalArgumentException("Division by zero");

        Complex[] newCoords = new Complex[coords.length];
        for (int i = 0; i < coords.length; i++)
            newCoords[i] = coords[i].divide(multiplier);

        return new Vector(newCoords);
    }

    private boolean moreDimensions(Vector other)
    {
        return this.coords.length >= other.coords.length;
    }

    @Override
    public String toString()
    {
        return Arrays.toString(coords).replace('[', '(').replace(']', ')');
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coords);
    }

    @Override
    public boolean equals(Object obj) {
        Vector vector;
        try {
            vector = (Vector) obj;
        }
        catch (ClassCastException exc) {
            return false;
        }
        return Arrays.equals(coords, vector.coords);
    }
}
