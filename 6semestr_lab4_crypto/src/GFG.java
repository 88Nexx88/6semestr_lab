import java.util.Arrays;
import java.util.Collections;

// Java program to find Determinant of a matrix
class GFG {
    /*
    1 1 1   1 1 1   1 1 1
    1 1 1   0 0 0   0 0 0
    1 1 1   0 0 0   0 0
     */

    public boolean determinantMatrix(double[][] matrix){
        double[][] source = new double[matrix.length][matrix.length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix.length; j++){
                source[i][j] = matrix[i][j];
            }
        }

        int str = 0;
        for(int col = 0; col < matrix.length; col++){
            int str1 = (str == matrix.length) ? str : str + 1;
            if(checkAllZero(matrix, col, str)) {
                return true;
            }
            int r = max(matrix, col, str);
            double d = matrix[str][col];
            matrix[str][col] = matrix[r][col];
            matrix[r][col] = d; //a11 - max
            for(int i = str1; i < matrix.length; i++) {
                for (int j = col + 1; j < matrix.length; j++) {
                    matrix[i][j] = matrix[i][j] - ((matrix[i][col] / matrix[str][col]) * matrix[str][j]);
                }
                matrix[i][col] = 0;
            }
            str++;
        }
        int count = 0;
        for(int i = 0; i < matrix.length; i++){
            if(matrix[i][i] == 0){
                count++;
            }
        }
        if(count == matrix.length) return true;
        else return false;



    }

    public void matrixToString(double[][] matrix){
        System.out.println("___________________________");
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix.length; j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("___________________________");
    }

    public boolean checkAllZero(double[][] matrix, int col, int str){
        int count = 0;
        for(int i = str; i < matrix.length; i++){
            if(matrix[i][col] == 0) count++;
        }
        if(count == matrix.length - str){
            return true;
        }
        else return false;
    }
    public int max(double[][] matrix, int col, int str){
        int count = str;
        for(int i = str; i < matrix.length; i++){
            if(matrix[count][col] < matrix[i][col]){
                count = i;
            }
        }
        return count;
    }
}
