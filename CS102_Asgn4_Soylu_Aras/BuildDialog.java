import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BuildDialog extends JDialog {
    public BuildDialog(GameController controller) 
    {
        setTitle("Build House");
        setModal(true);
        setLayout(new BorderLayout(10, 10));
        
        Player current = controller.getCurrentPlayer();
        
        JPanel propertiesPanel = new JPanel();
        propertiesPanel.setLayout(new GridLayout(0, 1, 5, 5));
        
        boolean available = false;
        
        for (Property prop : current.ownedProperties) 
        {
            if (current.canBuildHouse(prop)) 
            {
                available = true;
                JButton btn = new JButton("Build on " + prop.name + " (Cost: " + prop.houseCost + " coins, Houses: " + prop.houses + ")");
                btn.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e) 
                    {
                        current.buildHouse(prop);
                        controller.appendLog(current.name + " built a house on property " + prop.name + ".");
                        current.hasBuiltThisTurn = true;
                        dispose();
                        JOptionPane.showMessageDialog(null, current.name+" build a house on "+prop.name+" for "+prop.houseCost+" coins.", "Property Selling", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                propertiesPanel.add(btn);
            }
        }
        
        if (!available) 
        {
            propertiesPanel.add(new JLabel("No properties available to build a house."));
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
        JPanel btnPanel = new JPanel();
        btnPanel.add(closeBtn);
        add(btnPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
