import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.border.LineBorder;

public class SudokuGameWithLevels extends JFrame {
    private JTextField[][] cells;
    private SudokuGen sudokuGen;
    private JPanel levelSelectorPanel;
    private JPanel gamePanel;
    private String choice;
    private JButton backButton;

    public SudokuGameWithLevels() {
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
                cells[i][j].setForeground(Color.BLACK);
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
        boolean invalidInput = false;
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String cellText = cells[i][j].getText().trim();
                    sudokuMatrix[i][j] = cellText.isEmpty() ? 0 : Integer.parseInt(cellText);
                    if (!cellText.isEmpty()) {
                        int value = Integer.parseInt(cellText);
                        if (value < 1 || value > 9 || rows[i].contains(value) || cols[j].contains(value) || grids[(i / 3) * 3 + j / 3].contains(value)) {
                            cells[i][j].setForeground(Color.RED);
                            invalidInput = true;
                        } else {
                            rows[i].add(value);
                            cols[j].add(value);
                            grids[(i / 3) * 3 + j / 3].add(value);
                            givenNums[i][j] = 1;
                        }
                    }
                }
            }
            if (invalidInput) {
                JOptionPane.showMessageDialog(this, "Invalid input. Highlighted cells have wrong values.");
                return;
            }
            solveSudoku(sudokuMatrix, rows, cols, grids);
            printSolvedSudoku(sudokuMatrix, givenNums);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter only numbers between 1 and 9.");
        }
    }

    private boolean solveSudoku(int[][] board, HashSet<Integer>[] rows, HashSet<Integer>[] cols, HashSet<Integer>[] grids) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(num, row, col, rows, cols, grids)) {
                            board[row][col] = num;
                            rows[row].add(num);
                            cols[col].add(num);
                            grids[(row / 3) * 3 + (col / 3)].add(num);

                            if (solveSudoku(board, rows, cols, grids)) {
                                return true;
                            } else {
                                board[row][col] = 0;
                                rows[row].remove(num);
                                cols[col].remove(num);
                                grids[(row / 3) * 3 + (col / 3)].remove(num);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int num, int row, int col, HashSet<Integer>[] rows, HashSet<Integer>[] cols, HashSet<Integer>[] grids) {
        return !rows[row].contains(num) && !cols[col].contains(num) && !grids[(row / 3) * 3 + (col / 3)].contains(num);
    }

    protected void printSolvedSudoku(int[][] solvedMatrix, int[][] givenNums) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String num = Integer.toString(solvedMatrix[i][j]);
                cells[i][j].setText(num);
                if (givenNums[i][j] != 1) {
                    cells[i][j].setForeground(Color.RED);
                } else {
                    cells[i][j].setForeground(Color.BLACK);
                }
            }
        }
    }

    protected void generateSudoku() {
        resetSudoku();
        switch (choice) {
            case "Easy":
                sudokuGen = new SudokuGen(40);
                break;
            case "Moderate":
                sudokuGen = new SudokuGen(50);
                break;
            case "Hard":
                sudokuGen = new SudokuGen(60);
                break;
        }
        sudokuGen.fillValues();
        printSudoku(sudokuGen.getMat());
    }

    protected void printSudoku(int[][] mat) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String num = mat[i][j] == 0 ? "" : String.valueOf(mat[i][j]);
                cells[i][j].setText(num);
                if (mat[i][j] != 0) {
                    cells[i][j].setForeground(Color.BLACK);
                    cells[i][j].setEditable(false); // Set non-editable
                } else {
                    cells[i][j].setForeground(Color.RED);
                    cells[i][j].setEditable(true); // Set editable for empty cells
                }
            }
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuGameWithLevels();
            }
        });
    }
}

