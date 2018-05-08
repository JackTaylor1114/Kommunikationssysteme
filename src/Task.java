import java.io.Serializable;

class Task implements Serializable {

    private MatrixColumn column;
    private MatrixRow row;
    private int result = 0;
    private int tasknumber;
    private int dimension = 0;
    private String signature;

    public Task(MatrixRow row, MatrixColumn column, String requesterID, int tasknumber, int dimension) {
        this.row = row;
        this.column = column;
        this.tasknumber = tasknumber;
        this.dimension = dimension;
    }

    public void sign(String signature){ this.signature = signature; }

    public void setResult(int result) { this.result=result; }

    public MatrixColumn getColumn() { return column; }

    public MatrixRow getRow() { return row; }

    public int getResult() { return result; }

    public int getTasknumber() { return tasknumber; }

    public String getSignature(){ return signature; }

    public int getDimension() { return dimension; }
}
