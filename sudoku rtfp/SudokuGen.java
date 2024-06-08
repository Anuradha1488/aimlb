import java.util.Random;

class SudokuGen {
    private int[][] mat;
    private int N = 9;
    private int K;
    private Random random = new Random();

    public SudokuGen(int K) {
        this.K = K;
        this.mat = new int[N][N];
    }

    public int[][] getMat() {
        return mat;
    }

    public void fillValues() {
        fillDiagonal();
        fillRemaining(0, 3);
        removeKDigits();
    }

    private void fillDiagonal() {
        for (int i = 0; i < N; i += 3) {
            fillBox(i, i);
        }
    }

    private void fillBox(int row, int col) {
        int num;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    num = random.nextInt(N) + 1;
                } while (!isUnusedInBox(row, col, num));
                mat[row + i][col + j] = num;
            }
        }
    }

    private boolean isUnusedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mat[rowStart + i][colStart + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIfSafe(int i, int j, int num) {
        return isUnusedInRow(i, num) && isUnusedInCol(j, num) && isUnusedInBox(i - i % 3, j - j % 3, num);
    }

    private boolean isUnusedInRow(int i, int num) {
        for (int j = 0; j < N; j++) {
            if (mat[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    private boolean isUnusedInCol(int j, int num) {
        for (int i = 0; i < N; i++) {
            if (mat[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    private boolean fillRemaining(int i, int j) {
        if (j >= N && i < N - 1) {
            i = i + 1;
            j = 0;
        }
        if (i >= N && j >= N) {
            return true;
        }
        if (i < 3) {
            if (j < 3) {
                j = 3;
            }
        } else if (i < N - 3) {
            if (j == (i / 3) * 3) {
                j = j + 3;
            }
        } else {
            if (j == N - 3) {
                i = i + 1;
                j = 0;
                if (i >= N) {
                    return true;
                }
            }
        }

        for (int num = 1; num <= N; num++) {
            if (checkIfSafe(i, j, num)) {
                mat[i][j] = num;
                if (fillRemaining(i, j + 1)) {
                    return true;
                }
                mat[i][j] = 0;
            }
        }
        return false;
    }

    private void removeKDigits() {
        int count = K;
        while (count != 0) {
            int cellId = random.nextInt(N * N);
            int i = (cellId / N);
            int j = cellId % N;
            if (mat[i][j] != 0) {
                count--;
                mat[i][j] = 0;
            }
        }
    }
}
