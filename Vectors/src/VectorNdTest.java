import junit.framework.TestCase;

public class VectorNdTest extends TestCase {
    public void testLength()
    {
        VectorNd vector = new VectorNd(6, 3, 5);
        assertEquals(8.36, vector.getLength(), 0.01);
    }

    public void testAdd()
    {
        VectorNd firstVector = new VectorNd(3, 5, 1, 4);
        VectorNd secondVector = new VectorNd(2, 1, 4, 3);
        VectorNd expectedVector = new VectorNd(5, 6, 5, 7);
        IVector resultVector = firstVector.add(secondVector);
        assertEquals(expectedVector, resultVector);
    }

    public void testSubtraction()
    {
        VectorNd firstVector = new VectorNd(3, 5, 1, 4);
        VectorNd secondVector = new VectorNd(2, 1, 4, 3);
        VectorNd expectedVector = new VectorNd(1, 4, -3, 1);
        IVector resultVector = firstVector.subtraction(secondVector);
        assertEquals(expectedVector, resultVector);
    }

    public void testScalarMultiplication()
    {
        VectorNd firstVector = new VectorNd(-3, 2, 7, 1);
        VectorNd secondVector = new VectorNd(4, 5, 1, -4);
        double result = firstVector.scalar(secondVector);
        assertEquals(1.0, result);
    }

    public void testToString()
    {
        VectorNd firstVector = new VectorNd(-3, 2, 7, 1);
        String resultString = firstVector.toString();
        assertEquals("[-3.0, 2.0, 7.0, 1.0]", resultString);
    }

    public void testHashCodeEquals()
    {
        VectorNd firstVector = new VectorNd(-3, 2, 7, 1);
        VectorNd secondVector = new VectorNd(-3, 2, 7, 1);
        int firstHashCode = firstVector.hashCode();
        int secondHashCode = secondVector.hashCode();
        assertEquals(firstHashCode, secondHashCode);
    }
}