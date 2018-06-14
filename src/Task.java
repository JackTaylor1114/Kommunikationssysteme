import java.io.Serializable;

class Task implements Serializable {

    private int[] columnData;
    private int[] rowData;
    private int column;
    private int row;
    private String clientID;


    public Task(int[] rowData, int[] columnData, int row, int column, String clientID) {
        this.columnData=columnData;
        this.rowData=rowData;
        this.row = row;
        this.column = column;
        this.clientID=clientID;
    }

    public int[] getColumnData() {
        return columnData;
    }

    public int[] getRowData() {
        return rowData;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getCalcLength(){
        return columnData.length;
    }

    public String getClientID() {
        return clientID;
    }
}
