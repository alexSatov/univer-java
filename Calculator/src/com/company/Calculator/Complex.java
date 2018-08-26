package com.company.Calculator;

public class Complex {
    private double real, imaginary;

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex(String strComplex){
        strComplex = strComplex.replace(" ", "");
        boolean minusFirst = strComplex.charAt(0) == '-';
        if (minusFirst) strComplex = strComplex.substring(1);

        char[] separators = { '+', '-' };
        String regex = "(" + new String(separators).replaceAll("(.)", "\\\\$1|").replaceAll("\\|$", ")");
        String[] args = strComplex.split(regex);

        if (args.length == 1) {
            if (args[0].charAt(args[0].length() - 1) == 'i')
                imaginary = args[0].length() == 1 ? 1 : Double.parseDouble(args[0].substring(0, args[0].length() - 1));
            else
                real = Double.parseDouble(args[0]);
            imaginary *= minusFirst ? -1 : 1;
            real *= minusFirst ? -1 : 1;
        }
        else {
            real = Double.parseDouble(args[0]);
            real *= minusFirst ? -1 : 1;
            imaginary = args[1].length() == 1 ? 1 : Double.parseDouble(args[1].substring(0, args[1].length() - 1));
            imaginary *= strComplex.contains("-") ? -1 : 1;
        }
    }

    public boolean isZero(){
        return real == 0 && imaginary == 0;
    }

    public Complex add(Complex other){
        return new Complex(real + other.real, imaginary + other.imaginary);
    }

    public Complex subtract(Complex other){
        return new Complex(real - other.real, imaginary - other.imaginary);
    }

    public Complex multiply(Complex other){
        return new Complex(real * other.real - imaginary * other.imaginary,
                real * other.imaginary + other.real * imaginary);
    }

    public Complex divide(Complex other){
        if (other.isZero())
            throw new IllegalArgumentException("Division by zero");

        Complex additionalMultiplier = new Complex(other.real, -other.imaginary);
        double denominator = other.multiply(additionalMultiplier).real;
        Complex numerator = multiply(additionalMultiplier);
        return new Complex(numerator.real / denominator, numerator.imaginary / denominator);
    }

    @Override
    public String toString() {
        String realStr, imaginaryStr;

        realStr = real == 0 ? "" : Double.toString(real);

        imaginaryStr = "";
        if (imaginary > 0) imaginaryStr = " + " + (imaginary != 1 ? Double.toString(imaginary) : "") + "i";
        if (imaginary < 0) imaginaryStr = " - " + (imaginary != -1 ? Double.toString(-imaginary) : "") + "i";

        if (realStr.equals("") && imaginaryStr.equals("")) return "0.0";
        if (realStr.equals("")) {
            if (imaginary == 1) return "i";
            if (imaginary == -1) return "-i";
            return Double.toString(imaginary) + "i";
        }

        return realStr + imaginaryStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Complex complex = (Complex) o;

        return real == complex.real && imaginary == complex.imaginary;
    }

    @Override
    public int hashCode() {
        double result = real;
        result = 31 * result + imaginary;
        return (int)result;
    }
}
