import java.io.Serializable;
import java.util.ArrayList;

/* Class representing a matrix, consisting of multiple rows and columns */
public class Matrix implements Serializable {

    private ArrayList<MatrixRow> rows = new ArrayList<>();
    private ArrayList<MatrixColumn> columns = new ArrayList<>();

    public Matrix() {}

    /* Add a row */
    public void addRow(MatrixRow row) {
        rows.add(row);
    }

    /* Return all rows */
    public ArrayList<MatrixRow> getRows() {
        return rows;
    }

    /* Add a column */
    public void addColumn(MatrixColumn column) {
        columns.add(column);
    }

    /* Return all columns */
    public ArrayList<MatrixColumn> getColumns() {
        return columns;
    }
}