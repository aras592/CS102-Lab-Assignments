import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SellDialog extends JDialog 
{
    public SellDialog(GameController controller) 
    {
        setTitle("Sell Property");
        setModal(true);
        setLayout(new BorderLayout());
        
        Player current = controller.getCurrentPlayer();
        
        JPanel propertiesPanel = new JPanel();
        propertiesPanel.setLayout(new GridLayout(0, 1, 5, 5));
        
        if (current.ownedProperties.size() > 0)
        {
            for (Property prop : current.ownedProperties) 
            {
                JButton btn = new JButton("Sell " + prop.name + " for " + prop.price + " coins");
                btn.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e) 
                    {
                        current.sellProperty(prop);
                        controller.appendLog(current.name + " sold property " + prop.name + ".");
                        current.hasSoldThisTurn = true;
                        dispose();
                        JOptionPane.showMessageDialog(null, current.name+" sold property "+prop.name+" for "+prop.houseCost+" coins.", "Property Selling", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                propertiesPanel.add(btn);
            }
        } 
        else 
        {
            propertiesPanel.add(new JLabel("No properties to sell."));
        }
        
        add(propertiesPanel, BorderLayout.CENTER);
        
        JButton closeBtn = new JButton("Cancel");
        closeBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                dispose();
            }
        });
        add(closeBtn, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
