import java.io.Serializable;

/* Task class representing a single task */
class Task implements Serializable {

    private int[] columnData;
    private int[] rowData;
    private int column;
    private int row;
    private String clientID;

    /* Task constructor */
    Task(int[] rowData, int[] columnData, int row, int column, String clientID) {
        this.columnData=columnData;
        this.rowData=rowData;
        this.row = row;
        this.column = column;
        this.clientID=clientID;
    }

    /* Return column data */
    int[] getColumnData() {
        return columnData;
    }

    /* Return row data */
    int[] getRowData() {
        return rowData;
    }

    /* Return a column */
    int getColumn() {
        return column;
    }

    /* Return a row */
    int getRow() {
        return row;
    }

    /* Return the calculation length */
    int getCalcLength(){
        return columnData.length;
    }

    /* Return the client ID */
    String getClientID() { return clientID; }
}
