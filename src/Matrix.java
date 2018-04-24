public class Matrix {

    private double[][] data = null;
    private int cols = 0, rows = 0;

    public Matrix(int rows, int cols) {
        data = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public Matrix(double[][] data) {
        this.data = data.clone();
        rows = this.data.length;
        cols = this.data[0].length;
    }

    public void setData(double[][] data){
        this.data = data.clone();
        rows = this.data.length;
        cols = this.data[0].length;
    }

    public void setValue(int row, int col, double Value) throws Exception {
        if(row>rows||col>cols)
            throw new Exception("Position is not in Martix");
        else
            data[row][col]=Value;
    }

    public void display() {
        System.out.print("[");
        for (int row = 0; row < rows; ++row) {
            if (row != 0) {
                System.out.print(" ");
            }

            System.out.print("[");

            for (int col = 0; col < cols; ++col) {
                System.out.printf("%8.3f", data[row][col]);

                if (col != cols - 1) {
                    System.out.print(" ");
                }
            }

            System.out.print("]");

            if (row == rows - 1) {
                System.out.print("]");
            }

            System.out.println();
        }
    }
}
