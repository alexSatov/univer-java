import java.util.Arrays;

public abstract class Vector implements IVector {
    private double[] coords;

    public Vector(double... coords) { this.coords = coords; }

    public double getLength() {
        double sum = 0;
        for (double coord : coords)
            sum += coord*coord;
        return Math.sqrt(sum);
    }

    private void checkEqualDimensions(IVector vector)
    {
        if (this.coords.length != vector.getCoords().length)
            throw new IllegalArgumentException("Vectors of different dimensions");
    }

    @Override
    public double[] getCoords()
    {
        return coords;
    }

    @Override
    public IVector add (IVector vector)
    {
        checkEqualDimensions(vector);

        double[] newCoords = new double[coords.length];
        for (int i = 0; i < coords.length; i++)
            newCoords[i] = coords[i] + vector.getCoords()[i];

        return createVector(newCoords);
    }

    @Override
    public IVector subtraction (IVector vector)
    {
        checkEqualDimensions(vector);

        double[] newCoords = new double[coords.length];
        for (int i = 0; i < coords.length; i++)
            newCoords[i] = coords[i] - vector.getCoords()[i];

        return createVector(newCoords);
    }

    @Override
    public double scalar (IVector vector)
    {
        checkEqualDimensions(vector);

        double sum = 0;
        for (int i = 0; i < this.coords.length; i++)
            sum += this.coords[i] * vector.getCoords()[i];

        return sum;
    }

    public abstract Vector createVector (double... coords);

    @Override
    public String toString()
    {
        return Arrays.toString(coords);
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
