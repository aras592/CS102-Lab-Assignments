import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame 
{
    private GameController controller;
    private MapPanel mapPanel;
    private JTextArea logArea;
    private JButton rollButton, buyButton, sellButton, buildButton, endTurnButton;
    
    public MainFrame(GameController controller) 
    {
        this.controller = controller;
        setTitle("Monopoly Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        mapPanel = new MapPanel(controller.getBoard(), controller.getPlayers());
        add(mapPanel, BorderLayout.CENTER);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 5, 5));
        rollButton = new JButton("Roll");
        buyButton = new JButton("Buy");
        sellButton = new JButton("Sell");
        buildButton = new JButton("Build");
        endTurnButton = new JButton("End Turn");
        
        buttonPanel.add(rollButton);
        buttonPanel.add(buyButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(buildButton);
        buttonPanel.add(endTurnButton);
        
        rightPanel.add(buttonPanel, BorderLayout.NORTH);
        
        logArea = new JTextArea(15, 50);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(rightPanel, BorderLayout.EAST);
        
        rollButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                controller.rollDice();
                updateButtons();
            }
        });
        
        buyButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                controller.buyProperty();
                updateButtons();
            }
        });
        
        sellButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                controller.sellProperty();
                updateButtons();
            }
        });
        
        buildButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                controller.promptBuildHouse();
                updateButtons();
            }
        });
        
        endTurnButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                controller.finishTurn();
                updateButtons();
            }
        });
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        updateButtons();
    }
    
    public void updateLog(String text) 
    {
        logArea.append(text + "\n");
    }
    
    public void refreshMap() 
    {
        mapPanel.repaint();
    }
    
    private void updateButtons() 
    {
        logArea.setText(controller.getLog());
        mapPanel.repaint();
        
        if(controller.isHumanTurn()) 
        {
            rollButton.setEnabled(controller.canRoll());
            buyButton.setEnabled(controller.canBuy());
            sellButton.setEnabled(controller.canSell());
            buildButton.setEnabled(controller.canBuild());
            endTurnButton.setEnabled(controller.canEndTurn());
        } 
        else 
        {
            rollButton.setEnabled(false);
            buyButton.setEnabled(false);
            sellButton.setEnabled(false);
            buildButton.setEnabled(false);
            endTurnButton.setEnabled(false);
        }
    }
}
