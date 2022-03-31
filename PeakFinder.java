package Problem2;

import java.util.Random;
import java.util.Scanner;

public class PeakFinder {
    public static void initRandom(int[][] arr) {
        Random r = new Random();
        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr[0].length; j++)
                arr[i][j] = r.nextInt(100);
    }

    public static void initInput(int[][] arr) {
        Scanner input = new Scanner(System.in);

        System.out.printf("Enter a %d by %d array :\n",arr.length,arr[0].length);
        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr[0].length; j++)
                arr[i][j] = input.nextInt();
    }

    public static void printArray(int[][] arr) {
        System.out.println("Array : ");
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                System.out.print("   ");
                for (int j = 0; j < arr[0].length; j++)
                    System.out.printf("%2d ", j);
                System.out.println();
            }
            System.out.print(i + " [");
            for (int j = 0; j < arr[0].length; j++) {
                System.out.printf("%2d", arr[i][j]);
                if (j != arr[0].length - 1) System.out.print(",");
            }
            System.out.println("]");
        }
    }

    public static boolean isPeak(int[][] arr, int row, int col) {
        int val = arr[row][col];
        //上方点
        if (row - 1 >= 0 && arr[row - 1][col] > val) return false;

        //下方点
        if (row + 1 < arr.length && arr[row + 1][col] > val) return false;

        //左侧点
        if (col - 1 > 0 && arr[row][col - 1] > val) return false;

        //右侧点
        if (col + 1 < arr[0].length && arr[row][col + 1] > val) return false;

        return true;
    }

    public static int[] findPeak(int[][] arr, int rowOfPoint, int colOfPoint, int row, int column) {
        //find peak within the window
        int maxInWindow = -1, rowOfMax = -1, colOfMax = -1;
        int firstRow = rowOfPoint, lastRow = firstRow + row - 1, midRow = (firstRow + lastRow) / 2;
        for (int j = 0; j < column; j++) {
            if (arr[firstRow][j] > maxInWindow) {
                maxInWindow = arr[firstRow][j];
                rowOfMax = firstRow;
                colOfMax = j;
            }
            if (arr[midRow][j] > maxInWindow) {
                maxInWindow = arr[midRow][j];
                rowOfMax = midRow;
                colOfMax = j;
            }
            if (arr[lastRow][j] > maxInWindow) {
                maxInWindow = arr[lastRow][j];
                rowOfMax = lastRow;
                colOfMax = j;
            }
        }

        int firstCol = colOfPoint, lastCol = firstCol + column - 1, midCol = (firstCol + lastCol) / 2;
        for (int i = 0; i < row; i++) {
            if (arr[i][firstCol] > maxInWindow) {
                maxInWindow = arr[i][firstCol];
                rowOfMax = i;
                colOfMax = firstCol;
            }
            if (arr[i][midCol] > maxInWindow) {
                maxInWindow = arr[i][midCol];
                rowOfMax = i;
                colOfMax = midCol;
            }
            if (arr[i][lastCol] > maxInWindow) {
                maxInWindow = arr[i][lastCol];
                rowOfMax = i;
                colOfMax = lastCol;
            }
        }

        if (isPeak(arr, rowOfMax, colOfMax)) return new int[]{rowOfMax, colOfMax};

        //To one of four quadrant
        int[] ret;
        if (rowOfMax < midRow && colOfMax > midCol)//Q1
            ret = findPeak(arr, rowOfPoint, midCol, midRow - rowOfPoint + 1, column - midCol);
        else if (rowOfMax < midRow && colOfMax < midCol)//Q2
            ret = findPeak(arr, rowOfPoint, colOfPoint, midRow - rowOfPoint + 1, midCol - colOfPoint + 1);
        else if (rowOfMax > midRow && colOfMax < midCol)//Q3
            ret = findPeak(arr, midRow, colOfPoint, row - midRow, midCol - colOfPoint + 1);
        else if (rowOfMax > midRow && colOfMax > midCol)//Q4
            ret = findPeak(arr, midRow, midCol, row - midRow, column - midCol);
        else if (rowOfMax == midRow) {
            if (arr[rowOfMax - 1][colOfMax] >= maxInWindow) {
                if (colOfMax < midCol)//Q2
                    ret = findPeak(arr, rowOfPoint, colOfPoint, midRow - rowOfPoint + 1, midCol - colOfPoint + 1);
                else//Q1
                    ret = findPeak(arr, rowOfPoint, midCol, midRow - rowOfPoint + 1, column - midCol);
            } else {
                if (colOfMax < midCol)//Q3
                    ret = findPeak(arr, midRow, colOfPoint, row - midRow, midCol - colOfPoint + 1);
                else//Q4
                    ret = findPeak(arr, midRow, midCol, row - midRow, column - midCol);
            }
        } else {//colOfMax == midCol
            if (arr[rowOfMax][colOfMax - 1] >= maxInWindow) {
                if (rowOfMax < midRow)//Q2
                    ret = findPeak(arr, rowOfPoint, colOfPoint, midRow - rowOfPoint + 1, midCol - colOfPoint + 1);
                else//Q3
                    ret = findPeak(arr, midRow, colOfPoint, row - midRow, midCol - colOfPoint + 1);
            } else {
                if (rowOfMax < midRow)//Q1
                    ret = findPeak(arr, rowOfPoint, midCol, midRow - rowOfPoint + 1, column - midCol);
                else//Q4
                    ret = findPeak(arr, midRow, midCol, row - midRow, column - midCol);
            }
        }

        return ret;
    }


    public static void main(String[] args) {
        int row, column;
        row = 10;
        column = 10;
        int[][] array = new int[row][column];

        //random
        initRandom(array);

        //input
//        initInput(array);

        //special test
//        array = new int[][]{
//                {1,2,3,6,5},
//                {16,41,23,22,6},
//                {15,17,22,21,7},
//                {14,18,19,20,10},
//                {13,14,11,10,9}
//        };

        printArray(array);

        int[] peak = findPeak(array, 0, 0, array.length, array[0].length);
        System.out.printf("Peak appears at [%d,%d] with value of %d!", peak[0], peak[1], array[peak[0]][peak[1]]);
    }
}
