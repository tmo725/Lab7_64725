import java.util.Arrays;

public class Lab_MatrixMul {
    public static void main(String[] args) {
        int[][] inputA = { {5, 6, 7}, {4, 8, 9} };
        int[][] inputB = { {6, 4}, {5, 7}, {1, 1} };
        MyData matA = new MyData(inputA);
        MyData matB = new MyData(inputB);
        int matC_r = matA.data.length;
        int matC_c = matB.data[0].length;
        MyData matC = new MyData(matC_r, matC_c);
        // Q4 construct 2D array for each "thread" with respected to each cell in matC, then start each thread
        Thread t1 = new Thread(new MatrixMulThread(0, 0, matA, matB, matC));
        t1.start();
        Thread t2 = new Thread(new MatrixMulThread(0, 1, matA, matB, matC));
        t2.start();
        Thread t3 = new Thread(new MatrixMulThread(1, 0, matA, matB, matC));
        t3.start();
        Thread t4 = new Thread(new MatrixMulThread(1, 1, matA, matB, matC));
        t4.start();
        // Q5 join each thread
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } 
        catch (Exception e) { System.out.println(e); }
            matC.show();
    }
}

class MatrixMulThread implements Runnable {
    int processing_row;  int processing_col;
    MyData datA;  MyData datB;  MyData datC;
    MatrixMulThread(int tRow, int tCol, MyData a, MyData b, MyData c) {
        //Q1 code here
        processing_row = tRow;
        processing_col = tCol;
        datA = a;
        datB = b;
        datC = c;   

        if(tRow == tCol) {
            if(tRow == 0) {
                for(int i = tCol; i <= datA.data.length; i++) {
                    datC.data[tRow][tCol] += datA.data[tRow][i] * datB.data[i][tRow];
                }
            }
            else {
                for(int i = tCol - 1; i <= datA.data.length; i++) {
                    datC.data[tRow][tCol] += datA.data[tRow][i] * datB.data[i][tRow];
                }
            }
        }
        else if(tRow < tCol) {
            for(int i = tRow; i <= datA.data.length; i++) {
                datC.data[tRow][tCol] += datA.data[tRow][i] * datB.data[i][tCol];
            }
        }
        else if(tRow > tCol) {
            for(int i = tCol; i <= datA.data.length; i++) {
                datC.data[tRow][tCol] += datA.data[tRow][i] * datB.data[i][tCol];
            }
        }
    }
    /* Q2 */ public void run() {
            // Q3
            System.out.println("perform sum of multipication of assigned row and col");
        }
} // class

class MyData {
    int[][] data;
    MyData(int[][] m) { data = m; }
    MyData(int r, int c) {
        data = new int[r][c];
        for (int[] aRow : data)
        Arrays.fill(aRow, 0);
        // 9 will be overwritten anyway
    }
    void show() {
        System.out.println(Arrays.deepToString(data));
    }
} // classs