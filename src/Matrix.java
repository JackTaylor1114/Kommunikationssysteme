import java.util.Arrays;

/* Class representing a matrix, consisting of multiple rows and columns */
public class Matrix{

    private Integer[][] data;
    private int rows;
    private int cols;

    /* Matrix constructor */
    public Matrix(Integer[][] data){
        this.data=data;
        rows = this.data.length;
        cols = this.data[0].length;
    }

    /* Matrix constructor */
    Matrix(int cols, int rows){
        this.rows=rows;
        this.cols=cols;
        this.data= new Integer[rows][cols];
    }

    /* Set the matrix data */
    void setData(int row, int col, int data){
        this.data[row][col]=data;
    }

    /* Return matrix rows */
    int getRows() {
        return rows;
    }

    /* Return matrix columns */
    int getCols() {
        return cols;
    }

    /* Return matrix row */
    int[] getRow(int row){
        return Arrays.stream(data[row]).mapToInt(Integer::intValue).toArray();
    }

    /* Return matrix column */
    int[] getCol(int col){
        int[] column = new int[rows];
        for (int i = 0; i < rows; i++) {
            column[i]=data[i][col];
        }
        return column;
    }

    /* Check if any entry is null */
    boolean isSomethingNull(){
        for (int rowi = 0; rowi < rows; rowi++) {
            for (int coli = 0; coli < cols; coli++) {
                if(data[rowi][coli]==null)
                    return true;
            }
        }
        return false;
    }

    /* Print out the matrix */
    void print(){
        for (int rowi = 0; rowi < rows; rowi++) {
            for (int coli = 0; coli < cols; coli++) {
                System.out.print(data[rowi][coli]+" ");
            }
            System.out.print("\n");
        }
    }
}