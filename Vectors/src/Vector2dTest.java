import junit.framework.TestCase;

public class Vector2dTest extends TestCase {
    public void testLength()
    {
        Vector2d vector = new Vector2d(4, 3);
        assertEquals(5.0, vector.getLength());
    }

    public void testInitialisation()
    {
        Vector2d vector = new Vector2d(-1, 7);
        assertEquals(-1.0, vector.x);
        assertEquals(7.0, vector.y);
    }

    public void testAdd()
    {
        Vector2d firstVector = new Vector2d(-1, 7);
        Vector2d secondVector = new Vector2d(4, -2);
        Vector2d resultVector = (Vector2d) firstVector.add(secondVector);
        assertEquals(3.0, resultVector.x);
        assertEquals(5.0, resultVector.y);
    }

    public void testSubtraction()
    {
        Vector2d firstVector = new Vector2d(-1, 7);
        Vector2d secondVector = new Vector2d(4, -2);
        Vector2d resultVector = (Vector2d) firstVector.subtraction(secondVector);
        assertEquals(-5.0, resultVector.x);
        assertEquals(9.0, resultVector.y);
    }

    public void testScalarMultiplication()
    {
        Vector2d firstVector = new Vector2d(3, -2);
        Vector2d secondVector = new Vector2d(-1, -4);
        double result = firstVector.scalar(secondVector);
        assertEquals(5.0, result);
    }

    public void testHashCodeEquals()
    {
        VectorNd firstVector = new VectorNd(-3, 2);
        Vector2d secondVector = new Vector2d(-3, 2);
        int firstHashCode = firstVector.hashCode();
        int secondHashCode = secondVector.hashCode();
        assertEquals(firstHashCode, secondHashCode);
    }
}