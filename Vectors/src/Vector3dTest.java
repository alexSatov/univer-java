import junit.framework.TestCase;

public class Vector3dTest extends TestCase {
    public void testLength()
    {
        Vector3d vector = new Vector3d(4, 3, -5);
        assertEquals(7.07, vector.getLength(), 0.01);
    }

    public void testInitialisation()
    {
        Vector3d vector = new Vector3d(-1, 7, 2);
        assertEquals(-1.0, vector.getCoords()[0]);
        assertEquals(7.0, vector.getCoords()[1]);
        assertEquals(2.0, vector.getCoords()[2]);
    }

    public void testAdd()
    {
        Vector3d firstVector = new Vector3d(-1, 7, 3);
        Vector3d secondVector = new Vector3d(4, -2, -10);
        Vector3d resultVector = (Vector3d) firstVector.add(secondVector);
        assertEquals(3.0, resultVector.x);
        assertEquals(5.0, resultVector.y);
        assertEquals(-7.0, resultVector.z);
    }

    public void testSubtraction()
    {
        Vector3d firstVector = new Vector3d(-1, 7, 3);
        Vector3d secondVector = new Vector3d(4, -2, -10);
        Vector3d resultVector = (Vector3d) firstVector.subtraction(secondVector);
        assertEquals(-5.0, resultVector.x);
        assertEquals(9.0, resultVector.y);
        assertEquals(13.0, resultVector.z);
    }

    public void testScalarMultiplication()
    {
        Vector3d firstVector = new Vector3d(3, -2, 7);
        Vector3d secondVector = new Vector3d(-1, -4, 0);
        double result = firstVector.scalar(secondVector);
        assertEquals(5.0, result);
    }

    public void testEquals()
    {
        VectorNd firstVector = new VectorNd(-3, 2, 6);
        Vector3d secondVector = new Vector3d(-3, 2, 6);
        assertEquals(firstVector, secondVector);
    }
}