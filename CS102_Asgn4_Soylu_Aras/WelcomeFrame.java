import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeFrame extends JFrame 
{
    private JTextField humanField, comp1Field, comp2Field, comp3Field;
    private JButton startButton;
    
    public WelcomeFrame() 
    {
        setTitle("Monopoly Game - Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        
        add(new JLabel("Human Player (Red):"));
        humanField = new JTextField("Player 1");
        add(humanField);
        
        add(new JLabel("Computer Player 1 (Blue):"));
        comp1Field = new JTextField("Computer 1");
        add(comp1Field);
        
        add(new JLabel("Computer Player 2 (Green):"));
        comp2Field = new JTextField("Computer 2");
        add(comp2Field);
        
        add(new JLabel("Computer Player 3 (Yellow):"));
        comp3Field = new JTextField("Computer 3");
        add(comp3Field);
        
        startButton = new JButton("Start Game");
        add(startButton);
        add(new JLabel(""));
        
        startButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                startGame();
            }
        });
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void startGame() 
    {
        String humanName = humanField.getText().trim();
        String comp1Name = comp1Field.getText().trim();
        String comp2Name = comp2Field.getText().trim();
        String comp3Name = comp3Field.getText().trim();
        
        if (humanName.isEmpty()) 
        {
            humanName = "Player 1";
        }
        if (comp1Name.isEmpty()) 
        {
            comp1Name = "Blue Player/Computer 1";
        }
        if (comp2Name.isEmpty()) 
        {
            comp2Name = "Green Player/Computer 2";
        }
        if (comp3Name.isEmpty()) 
        {
            comp3Name = "Yellow Player/Computer 3";
        }
        
        Player human = new Player(humanName, humanName.charAt(0), Color.RED);
        Player comp1 = new Player(comp1Name, comp1Name.charAt(0), Color.BLUE);
        Player comp2 = new Player(comp2Name, comp2Name.charAt(0), Color.GREEN);
        Player comp3 = new Player(comp3Name, comp3Name.charAt(0), Color.YELLOW);
        
        Player[] players = new Player[]{human, comp1, comp2, comp3};
        
        GameController controller = new GameController(players);
        new MainFrame(controller);
        
        dispose();
    }
}
