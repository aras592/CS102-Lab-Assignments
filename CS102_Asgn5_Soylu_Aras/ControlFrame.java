import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlFrame extends JFrame implements ActionListener {
    private MazeFrame mazeFrame;
    private JButton btnSetStart; 
    private JButton btnSetEnd; 
    private JButton btnAddWall; 
    private JButton btnRemoveWall;
    private JButton btnFindPath;
    private JButton btnReset;

    public ControlFrame(MazeFrame mf) {
        mazeFrame = mf;
        setTitle("Controls");
        setLayout(new GridLayout(6, 1));
        
        btnSetStart = new JButton("Set Start");
        btnSetEnd   = new JButton("Set End");
        btnAddWall  = new JButton("Add Wall");
        btnRemoveWall = new JButton("Remove Wall");
        btnFindPath = new JButton("Find Path");
        btnReset    = new JButton("Reset");
        
        add(btnSetStart);
        add(btnSetEnd);
        add(btnAddWall);
        add(btnRemoveWall);
        add(btnFindPath);
        add(btnReset);
        
        btnSetStart.addActionListener(this);
        btnSetEnd.addActionListener(this);
        btnAddWall.addActionListener(this);
        btnRemoveWall.addActionListener(this);
        btnFindPath.addActionListener(this);
        btnReset.addActionListener(this);
        
        setSize(200, 400);
        setLocation(550, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MazePanel panel = mazeFrame.getMazePanel();
        if (e.getSource() == btnSetStart) 
        {
            panel.setCurrentMode(MazePanel.SET_START_CASE);
        } 
        else if (e.getSource() == btnSetEnd) 
        {
            panel.setCurrentMode(MazePanel.SET_END_CASE);
        } 
        else if (e.getSource() == btnAddWall) 
        {
            panel.setCurrentMode(MazePanel.ADD_WALL_CASE);
        } 
        else if (e.getSource() == btnRemoveWall) 
        {
            panel.setCurrentMode(MazePanel.REMOVE_WALL_CASE);
        } 
        else if (e.getSource() == btnFindPath) 
        {
            panel.findPath();
        } 
        else if (e.getSource() == btnReset) 
        {
            panel.resetMaze();
        }
    }
}

