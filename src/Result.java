import java.io.Serializable;

/* Result class that represents a calculation result */
class Result implements Serializable {

    private int result;
    private int col;
    private int row;
    private String clientID;
    private String workerID;

    /* Result constructor */
    Result(int col, int row, String clientID){
        this.row=row;
        this.col=col;
        this.clientID=clientID;
    }

    /* Returns the result */
    int getResult() {
        return result;
    }

    /* Sets the result */
    void setResult(int result) {
        this.result = result;
    }

    /* Returns the column index */
    int getCol() {
        return col;
    }

    /* Returns the row index */
    int getRow() {
        return row;
    }

    /* Returns client ID */
    String getClientID() {
        return clientID;
    }

    /* Returns the worker ID */
    String getWorkerID() {
        return workerID;
    }

    /* Sets the worker ID */
    void setWorkerID(String workerID) {
        this.workerID = workerID;
    }
}