public class VectorNd extends Vector
{
    public VectorNd(double... coords) { super(coords); }

    @Override
    public VectorNd createVector(double... coords)
    {
        return new VectorNd(coords);
    }
}
