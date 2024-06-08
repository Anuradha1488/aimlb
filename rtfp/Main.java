
public class Main {
    // Driver code
    public static void main(String[] args)
    {
        int N = 9, K = 20;
        SudokuGen sudoku = new SudokuGen(N, K);
        sudoku.fillValues();
        sudoku.printSudoku();
    }
}
