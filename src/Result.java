import java.io.Serializable;

class Result implements Serializable {

    private int result;
    private int col;
    private int row;
    private String clientID;
    private String workerID;

    public Result(int col, int row, String clientID){
        this.row=row;
        this.col=col;
        this.clientID=clientID;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public String getClientID() {
        return clientID;
    }

    public String getWorkerID() {
        return workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }
}
