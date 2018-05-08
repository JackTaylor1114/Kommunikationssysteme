class TaskDTO {

    private MatrixColumn column;
    private MatrixRow row;
    private int result = 0;
    private int tasknumber;
    private String signature;

    public TaskDTO(MatrixRow row, MatrixColumn column, int tasknumber) {
        this.row = row;
        this.column = column;
        this.tasknumber = tasknumber;
    }

    public void sign(String signature){ this.signature = signature; }

    public MatrixColumn getColumn() { return column; }

    public MatrixRow getRow() { return row; }

    public int getResult() { return result; }

    public int getTasknumber() { return tasknumber; }

    public String getSignature(){ return signature; }
}
