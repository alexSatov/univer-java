public class Vector2d extends Vector
{
    final double x, y;

    public Vector2d(double x,  double y)
    {
        super(x, y);
        this.x = x;
        this.y = y;
    }

    @Override
    public Vector2d createVector(double... coords)
    {
        return new Vector2d(coords[0], coords[1]);
    }
}
