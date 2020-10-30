public class SortGrid {
    private static int compares = 0;
    private static int[][] grid;
    private static int[][] copy;

    /***PUBLIC METHODS***/
    public static int sort(int[][] thisGrid) {
        compares = 0;
        copy = new int[thisGrid.length][thisGrid.length];
        grid = thisGrid;
        //sortRows(grid);
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid.length; j++)
                copy[i][j] = grid[i][j];
        for (int i = 0; i < thisGrid.length; i++) {
            rowSort(grid[i], 0, thisGrid.length - 1, i);
            //grid = copy;
        }
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid.length; j++)
                grid[i][j] = copy[i][j];
        int m = (grid.length * grid.length - 1) / 2;
        fullSort(grid, 0, (grid.length * grid.length) - 1);
        System.out.println(compares);
        //TO BE IMPLEMENTED

        return compares;
    }

    /*** HELPER METHODS ***/
    //returns true if value at (r1, c1) is less
    //than value at (r2, c2) and false otherwise;
    //counts as 1 compare
    private static boolean lessThan(int r1, int c1, int r2, int c2) {
        compares++;
        if (grid[r1][c1] < grid[r2][c2])
            return true;
        return false;
    }

    //returns true if value at (r1, c1) is greater than
    //value at (r2, c2) and false otherwise;
    //counts as 1 compare
    private static boolean greaterThan(int r1, int c1, int r2, int c2) {
        compares++;
        if (grid[r1][c1] > grid[r2][c2])
            return true;
        return false;
    }

    //swaps values in the grid
    //at (r1, c1) and (r2, c2);
    //assumes that the parameters
    //are within the bounds
    private static void swap(int r1, int c1, int r2, int c2) {
        int temp = grid[r1][c1];
        grid[r1][c1] = grid[r2][c2];
        grid[r2][c2] = temp;
    }

    private static void recursiveSort(int[][] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            recursiveSort(arr, l, m);
            recursiveSort(arr, m + 1, r);
            merge(arr, m, l, r);
            if ((l==0) && (r == grid.length * grid.length - 1))
                merge(arr, m, l, r);
        }
    }

    private static void rowSort(int[] arr, int l, int r, int row) {
        if (l < r) {
            int m = (l + r) / 2;
            rowSort(arr, l, m, row);
            rowSort(arr, m + 1, r, row);
            rowMerge(arr, m, l, r, row);
//            if ((l==0) && (r == grid.length * grid.length - 1))
//                rowMerge(arr, m, l, r, row);
        }
    }
    private static void fullSort(int[][] arr, int l, int r) {

        if (l < r) {
            int m = (l + r) / 2;
            fullSort(arr, l, m);
            fullSort(arr, m + 1, r);
            fullMerge(arr, m, l, r);
            if ((l == 0) && (r == grid.length * grid.length - 1))
                fullMerge(arr, m, l, r);
        }
    }


    private static void merge(int[][] arr, int m, int l, int r) {
        int leftIndex = 0;
        int leftBound = m - l ;
        int rightIndex = m - l + 1;
        int rightBound = r - l;
        for (int i = 0; i <= leftBound; i++)
            arr[getRow(i)][getCol(i)] = copy[getRow(l + i)][getCol(l + i)];
        for (int i = rightIndex; i <= rightBound; i++)
            arr[getRow(i)][getCol(i)] = copy[getRow(m + 1 + i - rightIndex)][getCol(m + i + 1 - rightIndex)];
        int count = l;
        while ((leftIndex <= leftBound) && (rightIndex <= rightBound)) {
            if (!greaterThan(getRow(leftIndex), getCol(leftIndex), getRow(rightIndex), getCol(rightIndex))) {
                copy[getRow(count)][getCol(count)] = arr[getRow(leftIndex)][getCol(leftIndex)];
                leftIndex++;
            }
            else {
                copy[getRow(count)][getCol(count)] = arr[getRow(rightIndex)][getCol(rightIndex)];
                rightIndex++;
            }
            count++;
        }
        while (leftIndex <= leftBound) {
            copy[getRow(count)][getCol(count)] = arr[getRow(leftIndex)][getCol(leftIndex)];
            leftIndex++;
            count++;
        }
        while (rightIndex <= rightBound) {
            copy[getRow(count)][getCol(count)] = arr[getRow(rightIndex)][getCol(rightIndex)];
            rightIndex++;
            count++;
        }
    }

    private static void rowMerge(int[] arr, int m, int l, int r, int row) {
        int leftIndex = 0;
        int leftBound = m - l ;
        int rightIndex = m - l + 1;
        int rightBound = r - l;
        for (int i = 0; i <= leftBound; i++) {
            arr[i] = copy[row][l + i];
        }
        for (int i = rightIndex; i <= rightBound; i++) {
            arr[i] = copy[row][m + i + 1 - rightIndex];
        }
        int count = l;
        while ((leftIndex <= leftBound) && (rightIndex <= rightBound)) {
            if (!greaterThan(row, leftIndex, row, rightIndex)) {
                copy[row][count] = arr[leftIndex];
                leftIndex++;
            }
            else {
                copy[row][count] = arr[rightIndex];
                rightIndex++;
            }
            count++;
        }
        while (leftIndex <= leftBound) {
            copy[row][count] = arr[leftIndex];
            leftIndex++;
            count++;
        }
        while (rightIndex <= rightBound) {
            copy[row][count] = arr[rightIndex];
            rightIndex++;
            count++;
        }
    }
    private static void fullMerge(int[][] arr, int m, int l, int r) {
//        int leftIndex = 0;
//        int leftBound = ((m - l + 1) * arr.length) - 1;
//        int rightIndex = leftBound + 1;
//        int rightBound = ((r - l + 1) * arr.length) - 1;
//        for (int i = 0; i <= leftBound; i++) {
//            grid[getRow(i)][getCol(i)] = copy[getRow((l * arr.length) + i)][getCol((l * arr.length) + i)];
//            //arr[i] = copy[row][l + i];
//        }
//        for (int i = rightIndex; i <= rightBound; i++) {
//            grid[getRow(i)][getCol(i)] = copy[getRow(((m + 1) * arr.length) + i - rightIndex)][getCol(((m + 1) * arr.length) + i - rightIndex)];
//            //arr[i] = copy[row][m + i + 1 - rightIndex];
//        }
//        for (int i = l; i <= r; i++) {
//            arr[getRow(i)][getCol(i)] = copy[getRow(i)][getCol(i)];
//        }
//        int count = l;
//        while ((leftIndex <= leftBound) && (rightIndex <= rightBound)) {
//            if (!lessThan(getRow(leftIndex), getCol(leftIndex), getRow(rightIndex), getCol(rightIndex))) {
//                copy[getRow(count)][getCol(count)] = arr[getRow(rightIndex)][getCol(rightIndex)];
//                rightIndex++;
//            }
//            else {
//                copy[getRow(count)][getCol(count)] = arr[getRow(leftIndex)][getCol(leftIndex)];
//                leftIndex++;
//            }
//            count++;
//        }
//        while (leftIndex <= leftBound) {
//            copy[getRow(count)][getCol(count)] = arr[getRow(leftIndex)][getCol(leftIndex)];
//            leftIndex++;
//            count++;
//        }
//        while (rightIndex <= rightBound) {
//            copy[getRow(count)][getCol(count)] = arr[getRow(rightIndex)][getCol(rightIndex)];
//            rightIndex++;
//            count++;
//        }
        for (int i = l; i <= r; i++) {
            copy[getRow(i)][getCol(i)] = grid[getRow(i)][getCol(i)];
        }
        int left = l;
        int right = m + 1;
        int count = l;
        while (left <= m && right <= r) {
            if (!greaterThan(getRow(left), getCol(left), getRow(right), getCol(right))) {
                grid[getRow(count)][getCol(count)] = copy[getRow(left)][getCol(left)];
                left++;
            }
            else {
                grid[getRow(count)][getCol(count)] = copy[getRow(right)][getCol(right)];
                right++;
            }
            count++;
        }
        while (left <= m) {
            grid[getRow(count)][getCol(count)] = copy[getRow(left)][getCol(left)];
            left++;
            count++;
        }
        while (right <= r) {
            grid[getRow(count)][getCol(count)] = copy[getRow(right)][getCol(right)];
            right++;
            count++;
        }
    }

    private static int getRow(int index) {
        return (index / grid.length);
    }

    private static int getCol(int index) {
        return (index % grid.length);
    }
}
