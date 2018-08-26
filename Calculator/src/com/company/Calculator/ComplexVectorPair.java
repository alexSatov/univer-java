package com.company.Calculator;

import com.company.Vector.Vector;

public class ComplexVectorPair {
    private Complex complex;
    private Vector vector;

    public ComplexVectorPair(Complex complex, Vector vector){
        this.complex = complex;
        this.vector = vector;
    }

    public ComplexVectorPair add(ComplexVectorPair other){
        return new ComplexVectorPair(complex.add(other.complex),
                vector.add(other.vector));
    }

    public ComplexVectorPair subtract(ComplexVectorPair other){
        return new ComplexVectorPair(complex.subtract(other.complex),
                vector.subtract(other.vector));
    }

    public ComplexVectorPair multiply(ComplexVectorPair other){ // (a + b)*(c + d) = ac + ad + bc + bd = x + y + z + k
        Complex x = complex.multiply(other.complex);
        Complex k = vector.scalar(other.vector);
        Vector y = other.vector.multiply(complex);
        Vector z = vector.multiply(other.complex);
        return new ComplexVectorPair(x.add(k), y.add(z));
    }

    public ComplexVectorPair divide(ComplexVectorPair other){
        if (!other.vector.isZeroVector())
            throw new IllegalArgumentException("Division by vector");
        return new ComplexVectorPair(complex.divide(other.complex), vector.divide(other.complex));
    }

    @Override
    public String toString() {
        if (complex.isZero() && vector.isZeroVector()) return "0.0";
        if (complex.isZero()) return vector.toString();
        if (vector.isZeroVector()) return complex.toString();
        return complex + " + " + vector.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComplexVectorPair that = (ComplexVectorPair) o;

        return (complex != null ? complex.equals(that.complex) : that.complex == null)
                && (vector != null ? vector.equals(that.vector) : that.vector == null);
    }

    @Override
    public int hashCode() {
        int result = complex != null ? complex.hashCode() : 0;
        result = 31 * result + (vector != null ? vector.hashCode() : 0);
        return result;
    }
}
