import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.border.LineBorder;

public class SudokoGameWithLevels extends JFrame {
    private JTextField[][] cells;
    private SudokuGen sudokuGen;
    private JPanel levelSelectorPanel;
    private JPanel gamePanel;
    private String choice;
    private JButton backButton;

    public SudokoGameWithLevels() {
        Font font = new Font("Cascadia Code", Font.PLAIN, 15);
        setTitle("Sudoku Challenger");
        setSize(500, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize panels
        levelSelectorPanel = createLevelSelectorPanel(font);
        gamePanel = createGamePanel(font);

        // Add panels to the frame
        add(levelSelectorPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createLevelSelectorPanel(Font font) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Select Sudoku Difficulty Level", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton easyButton = createCustomButton("Easy", new Color(144, 238, 144), font);
        easyButton.addActionListener(e -> startGame("Easy"));

        JButton moderateButton = createCustomButton("Moderate", new Color(255, 223, 0), font);
        moderateButton.addActionListener(e -> startGame("Moderate"));

        JButton hardButton = createCustomButton("Hard", new Color(255, 99, 71), font);
        hardButton.addActionListener(e -> startGame("Hard"));

        buttonPanel.add(easyButton);
        buttonPanel.add(moderateButton);
        buttonPanel.add(hardButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createGamePanel(Font font) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel sudokuPanel = new JPanel();
        sudokuPanel.setLayout(new GridLayout(9, 9));
        cells = new JTextField[9][9];
        initializeCells(sudokuPanel);
        mainPanel.add(sudokuPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton resetButton = new JButton("Reset");
        resetButton.setBackground(new Color(252, 3, 36));
        resetButton.setFocusable(false);
        resetButton.setFont(font);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetSudoku();
            }
        });

        JButton generateButton = new JButton("Generate");
        generateButton.setBackground(new Color(252, 3, 36));
        generateButton.setFocusable(false);
        generateButton.setFont(font);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateSudoku();
            }
        });

        JButton verifyButton = new JButton("Solve");
        verifyButton.setBackground(new Color(148, 252, 3));
        verifyButton.setFocusable(false);
        verifyButton.setFont(font);
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifySudoku();
            }
        });

        backButton = new JButton("Back to Level Selection");
        backButton.setBackground(new Color(0, 122, 204));
        backButton.setFocusable(false);
        backButton.setFont(font);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToLevelSelection();
            }
        });

        buttonPanel.add(resetButton);
        buttonPanel.add(generateButton);
        buttonPanel.add(verifyButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JButton createCustomButton(String text, Color bgColor, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return button;
    }

    private void startGame(String difficulty) {
        this.choice = difficulty;
        remove(levelSelectorPanel);
        add(gamePanel);
        revalidate();
        repaint();
        generateSudoku();
    }

    private void backToLevelSelection() {
        remove(gamePanel);
        add(levelSelectorPanel);
        revalidate();
        repaint();
    }

    private void initializeCells(JPanel sudokuPanel) {
        Font font = new Font("Cascadia Code", Font.PLAIN, 40);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JTextField(1);
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                cells[i][j].setFont(font);

                if ((i / 3 + j / 3) % 2 == 0) {
                    cells[i][j].setBackground(new Color(173, 216, 230));
                }

                cells[i][j].setBorder(new LineBorder(Color.BLACK, 1));

                sudokuPanel.add(cells[i][j]);
            }
        }
    }

    protected void resetSudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText("");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void verifySudoku() {
        int[][] sudokuMatrix = new int[9][9];
        int[][] givenNums = new int[9][9];
        HashSet<Integer>[] rows = (HashSet<Integer>[]) new HashSet[9];
        HashSet<Integer>[] cols = (HashSet<Integer>[]) new HashSet[9];
        HashSet<Integer>[] grids = (HashSet<Integer>[]) new HashSet[9];
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
            grids[i] = new HashSet<>();
        }
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String cellText = cells[i][j].getText().trim();
                    sudokuMatrix[i][j] = cellText.isEmpty() ? 0 : Integer.parseInt(cellText);
                    if (!cellText.isEmpty()) {
                        rows[i].add(Integer.parseInt(cellText));
                        cols[j].add(Integer.parseInt(cellText));
                        grids[(i / 3) * 3 + j / 3].add(Integer.parseInt(cellText));
                        givenNums[i][j] = 1;
                        if (Integer.parseInt(cellText) > 9 || Integer.parseInt(cellText) < 1) {
                            resetSudoku();
                            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers between 1 and 9.");
                            return;
                        }
                    }
                }
            }
            int rowindex = 0;
            int colindex = 0;
            solveSudoku(sudokuMatrix, rows, cols, grids, rowindex, colindex);
            printSolvedSudoku(sudokuMatrix, givenNums);
        } catch (NumberFormatException ex) {
            resetSudoku();
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter only numbers.");
        }
    }

    public static int[][] solveSudoku(int[][] sudokuMatrix, HashSet<Integer>[] rows, HashSet<Integer>[] cols,
                                      HashSet<Integer>[] grids, int rowindex, int colindex) {
        if (rowindex == 9) {
            return sudokuMatrix;
        }
        if (colindex == 9) {
            return solveSudoku(sudokuMatrix, rows, cols, grids, rowindex + 1, 0);
        }
        if (sudokuMatrix[rowindex][colindex] != 0) {
            return solveSudoku(sudokuMatrix, rows, cols, grids, rowindex, colindex + 1);
        }
        for (int i = 1; i <= 9; i++) {
            if (!rows[rowindex].contains(i) && !cols[colindex].contains(i)
                    && !grids[(rowindex / 3) * 3 + colindex / 3].contains(i)) {
                sudokuMatrix[rowindex][colindex] = i;
                rows[rowindex].add(i);
                cols[colindex].add(i);
                grids[(rowindex / 3) * 3 + colindex / 3].add(i);
                int[][] solvedMatrix = solveSudoku(sudokuMatrix, rows, cols, grids, rowindex, colindex + 1);
                if (solvedMatrix != null) {
                    return solvedMatrix;
                }
                sudokuMatrix[rowindex][colindex] = 0;
                rows[rowindex].remove(i);
                cols[colindex].remove(i);
                grids[(rowindex / 3) * 3 + colindex / 3].remove(i);
            }
        }
        return null;
    }

    protected void printSolvedSudoku(int[][] solvedMatrix, int[][] givenNums) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String num = Integer.toString(solvedMatrix[i][j]);
                cells[i][j].setText(num);
                if (givenNums[i][j] != 1) {
                    cells[i][j].setForeground(Color.RED);
                }
            }
        }
    }

    protected void generateSudoku() {
        resetSudoku();
        switch (choice) {
            case "Easy":
                sudokuGen = new SudokuGen(9, 40);
                break;
            case "Moderate":
                sudokuGen = new SudokuGen(9, 50);
                break;
            case "Hard":
                sudokuGen = new SudokuGen(9, 60);
                break;
        }
        sudokuGen.fillValues();
        printSudoku(sudokuGen.mat);
    }

    private void printSudoku(int[][] mat) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText(mat[i][j] == 0 ? "" : String.valueOf(mat[i][j]));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokoGameWithLevels();
            }
        });
    }
}

// SudokuGen class (assumed to be provided separately)
class SudokuGen {
    int[][] mat;
    int N;
    int K;

    public SudokuGen(int N, int K) {
        this.N = N;
        this.K = K;
        mat = new int[N][N];
    }

    public void fillValues() {
        // Fill the Sudoku puzzle logic goes here
        // Example: randomly fill cells with some values and set others to zero
        // This is just a placeholder for actual Sudoku generation logic
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                mat[i][j] = (Math.random() < 0.5) ? 0 : (int) (Math.random() * 9 + 1);
            }
        }
    }
}
