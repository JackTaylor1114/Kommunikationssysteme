import java.io.Serializable;

/* Class representing a matrix row */
public class MatrixRow implements Serializable {

    private int[] values;
    private int index;

    /* Create a new row with values and the position in matrix */
    public MatrixRow(int[] values, int index) {
        this.values = values;
        this.index = index;
    }

    /* Return the values */
    public int[] getValues() {
        return values;
    }

    /* Return the index */
    public int getIndex() {
        return index;
    }
}
