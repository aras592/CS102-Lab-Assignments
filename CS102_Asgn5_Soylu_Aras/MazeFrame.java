import javax.swing.JFrame;

public class MazeFrame extends JFrame {
    private MazePanel mazePanel;

    public MazeFrame() {
        setTitle("Mouse Maze");
        mazePanel = new MazePanel();
        add(mazePanel);
        
        setSize(420, 450);
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public MazePanel getMazePanel() {
        return mazePanel;
    }
}
