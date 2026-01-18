import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MazePanel extends JPanel implements MouseListener {
    
    private final int ROWS = 5;
    private final int COLS = 5;

    private int[][] walls = new int[ROWS][COLS];

    public static final int DEFAULT_CASE = 0;
    public static final int SET_START_CASE = 1;
    public static final int SET_END_CASE = 2;
    public static final int ADD_WALL_CASE = 3;
    public static final int REMOVE_WALL_CASE = 4;
    
    private int currentMode = DEFAULT_CASE;
    
    private int startRow = 0;
    private int startCol = 0;

    private final int cellSize = 80;
    
    private int endRow = ROWS - 1;
    private int endCol = COLS - 1;
    
    private int[][] pathCells = new int[ROWS][COLS];
    
    private BufferedImage mouseImage;
    private BufferedImage cheeseImage;
    private BufferedImage wallImage;
    
    private int shortestPath;
    private ArrayList<Point> bestPath;
    
    public MazePanel() {
        setPreferredSize(new Dimension(COLS * cellSize, ROWS * cellSize));
        addMouseListener(this);
        
        try {
            mouseImage = ImageIO.read(new File("mouse.png"));
            cheeseImage = ImageIO.read(new File("cheese.png"));
            wallImage = ImageIO.read(new File("wall.png"));
        } catch (IOException e) {
            System.out.println("Error loading images: " + e.getMessage());
        }
    }
    
    public void setCurrentMode(int mode) {
        currentMode = mode;
    }
    
    public void resetMaze() {
        startRow = 0;
        startCol = 0;
        endRow = ROWS - 1;
        endCol = COLS - 1;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                walls[i][j] = 0;
                pathCells[i][j] = 0;
            }
        }
        currentMode = DEFAULT_CASE;
        repaint();
    }
    
    public void findPath() {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                pathCells[i][j] = 0;
            }
        }

        shortestPath = Integer.MAX_VALUE;
        bestPath = new ArrayList<>();
        int[][] checked = new int[ROWS][COLS];

        ArrayList<Point> currentPath = new ArrayList<>();
        recursiveSearch(startRow, startCol, 0, checked, currentPath);
        
        if (bestPath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cannot find a path!");
        } 
        else 
        {
            for (Point p : bestPath) 
            {
                pathCells[p.x][p.y] = 1;
            }
        }
        repaint();
    }
    
    
    private void recursiveSearch(int r, int c, int len, int[][] checked, ArrayList<Point> currentPath) {
        
        if (r < 0 || r >= ROWS || c < 0 || c >= COLS)
        {
            return;
        }
        if (walls[r][c] == 1|| checked[r][c] == 1) 
        {
            return;
        }
        
        currentPath.add(new Point(r, c));
        
        if (r == endRow && c == endCol) {
            if (len < shortestPath) {
                shortestPath = len;
                bestPath = new ArrayList<>(currentPath);
            }
            currentPath.remove(currentPath.size() - 1);
            return;
        }
        
        checked[r][c] = 1;
        
        recursiveSearch(r - 1, c, len + 1, checked, currentPath); 
        recursiveSearch(r + 1, c, len + 1, checked, currentPath); 
        recursiveSearch(r, c - 1, len + 1, checked, currentPath); 
        recursiveSearch(r, c + 1, len + 1, checked, currentPath); 
        

        checked[r][c] = 0;
        
        currentPath.remove(currentPath.size() - 1);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int i = 0; i <= ROWS; i++) {
            g.drawLine(0, i * cellSize, COLS * cellSize, i * cellSize);
        }
        for (int j = 0; j <= COLS; j++) {
            g.drawLine(j * cellSize, 0, j * cellSize, ROWS * cellSize);
        }
        
        g.setColor(Color.GREEN);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (pathCells[i][j]==1) 
                {
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }
        
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (walls[i][j]==1) 
                {
                    if (wallImage != null)
                    {
                        g.drawImage(wallImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                    }
                }
            }
        }
        
        if (mouseImage != null)
        {
            g.drawImage(mouseImage, startCol * cellSize, startRow * cellSize, cellSize, cellSize, null);
        }
        
        if (cheeseImage != null)
            g.drawImage(cheeseImage, endCol * cellSize, endRow * cellSize, cellSize, cellSize, null);
        
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX() / cellSize;
        int row = e.getY() / cellSize;
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS)
        {
            return;
        }
        
        switch (currentMode) {
            case SET_START_CASE:
                if (row == endRow && col == endCol) 
                {
                    JOptionPane.showMessageDialog(this, "Start cannot be the same as End cell!");
                    return;
                }
                else if(walls[row][col] == 1)
                {
                    JOptionPane.showMessageDialog(this, "Start cell cannot be on a wall!");
                    return;
                }
                startRow = row;
                startCol = col;
                break;
            case SET_END_CASE:
                if (row == startRow && col == startCol) 
                {
                    JOptionPane.showMessageDialog(this, "End cannot be the same as Start cell!");
                    return;
                }
                else if(walls[row][col] == 1)
                {
                    JOptionPane.showMessageDialog(this, "End cell cannot be on a wall!");
                    return;
                }
                endRow = row;
                endCol = col;
                break;
            case ADD_WALL_CASE:
                if ((row == endRow && col == endCol) || (row == startRow && col == startCol)) 
                {
                    JOptionPane.showMessageDialog(this, "Cannot place wall on Start or End cell!");
                    return;
                }
                walls[row][col] = 1;
                break;
            case REMOVE_WALL_CASE:
                walls[row][col] = 0;
                break;
            default:
                break;
        }
        repaint();
    }
    
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void mouseClicked(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    
}

