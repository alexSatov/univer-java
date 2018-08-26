public class Vector3d extends Vector
{
    final double x, y, z;

    public Vector3d(double x,  double y, double z)
    {
        super(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Vector3d createVector(double... coords)
    {
        return new Vector3d(coords[0], coords[1], coords[2]);
    }
}
