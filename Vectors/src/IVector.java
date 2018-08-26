public interface IVector {
    IVector add(IVector vector);
    IVector subtraction(IVector vector);
    double scalar(IVector vector);
    double[] getCoords();
}
