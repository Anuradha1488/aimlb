import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuLevelSelector extends JFrame implements ActionListener {
    private JButton easyButton;
    private JButton moderateButton;
    private JButton hardButton;

    public SudokuLevelSelector() {
        setTitle("Sudoku Game Platform");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Set custom layout
        setLayout(new BorderLayout());

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setOpaque(false); // Make the panel transparent

        // Create buttons
        easyButton = createCustomButton("Easy", new Color(144, 238, 144)); // Light green for easy
        moderateButton = createCustomButton("Moderate", new Color(255, 223, 0)); // Yellow for moderate
        hardButton = createCustomButton("Hard", new Color(255, 99, 71)); // Light red for hard

        // Add buttons to the panel
        buttonPanel.add(easyButton);
        buttonPanel.add(moderateButton);
        buttonPanel.add(hardButton);

        // Create a title label
        JLabel titleLabel = new JLabel("Select Sudoku Difficulty Level", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        // Add components to the frame
        setContentPane(new JLabel(new ImageIcon("background.jpg"))); // Add your background image here
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createCustomButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == easyButton) {
            JOptionPane.showMessageDialog(this, "Easy level selected.");
            // Implement logic to start Easy level Sudoku game
        } else if (e.getSource() == moderateButton) {
            JOptionPane.showMessageDialog(this, "Moderate level selected.");
            // Implement logic to start Moderate level Sudoku game
        } else if (e.getSource() == hardButton) {
            JOptionPane.showMessageDialog(this, "Hard level selected.");
            // Implement logic to start Hard level Sudoku game
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuLevelSelector().setVisible(true);
            }
        });
    }
}
