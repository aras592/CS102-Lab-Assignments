import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MapPanel extends JPanel 
{
    private Board board;
    private Player[] players;
    
    public MapPanel(Board board, Player[] players) 
    {
        this.board = board;
        this.players = players;
        setPreferredSize(new Dimension(500, 500));
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int cellSize = panelWidth / 5; // assume 5 cells along top and bottom

        // Draw the board cells.
        // Top row: cells 0 to 4.
        for (int i = 0; i < 5; i++) {
            int x = i * cellSize;
            int y = 0;
            drawCell(g, x, y, cellSize, board.board[i], i);
        }
        // Right column: cells 5,6,7.
        for (int j = 0; j < 3; j++) {
            int x = panelWidth - cellSize;
            int y = cellSize + j * cellSize;
            drawCell(g, x, y, cellSize, board.board[5 + j], 5 + j);
        }
        // Bottom row: cells 8 to 12 (drawn in reverse order).
        for (int i = 0; i < 5; i++) {
            int cellIndex = 12 - i;
            int x = i * cellSize;
            int y = panelHeight - cellSize;
            drawCell(g, x, y, cellSize, board.board[cellIndex], cellIndex);
        }
        // Left column: cells 13,14,15 (bottom-to-top).
        for (int j = 0; j < 3; j++) {
            int cellIndex = 15 - j;
            int x = 0;
            int y = panelHeight - cellSize - (j + 1) * cellSize;
            drawCell(g, x, y, cellSize, board.board[cellIndex], cellIndex);
        }
        
        // Draw player tokens (only non-eliminated players) with offsets.
        for (int cellIndex = 0; cellIndex < board.board.length; cellIndex++) {
            ArrayList<Player> playersInCell = new ArrayList<>();
            for (Player p : players) {
                if (!p.isEliminated && p.position == cellIndex) {
                    playersInCell.add(p);
                }
            }
            if (!playersInCell.isEmpty()) {
                Point center = getCellCenter(cellIndex, cellSize, panelWidth, panelHeight);
                Point[] offsets = getOffsets(playersInCell.size());
                for (int i = 0; i < playersInCell.size(); i++) {
                    Player p = playersInCell.get(i);
                    int drawX = center.x + offsets[i].x;
                    int drawY = center.y + offsets[i].y;
                    
                    g.setColor(p.tokenColor);
                    g.fillOval(drawX - 10, drawY - 10, 20, 20);
                    
                    g.setColor(Color.BLACK);
                    Font originalFont = g.getFont();
                    Font newFont = new Font("Arial", Font.BOLD, 12);
                    g.setFont(newFont);
                    FontMetrics fm = g.getFontMetrics();
                    String initialStr = String.valueOf(p.initial);
                    int textWidth = fm.stringWidth(initialStr);
                    int textHeight = fm.getAscent();
                    g.drawString(initialStr, drawX - textWidth / 2, drawY + textHeight / 2 - 2);
                    g.setFont(originalFont);
                }
            }
        }
        
        // Display players' coins at the center of the game map.
        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;
        int lineHeight = 20;
        int startY = centerY - (players.length * lineHeight) / 2;
        g.setFont(new Font("Arial", Font.BOLD, 16));
        for (int i = 0; i < players.length; i++) {
            Player p = players[i];
            // Show coin count only for non-eliminated players.
            if (!p.isEliminated) {
                g.setColor(p.tokenColor);
                String coinInfo = p.name + ": " + p.coins + " coins";
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth(coinInfo);
                g.drawString(coinInfo, centerX - textWidth / 2, startY + i * lineHeight);
            }
        }
    }
    
    // Draws a cell
    private void drawCell(Graphics g, int x, int y, int size, String label, int cellIndex) {
        Property prop = board.getPropertyAtCell(cellIndex);
        if (prop != null && prop.owner != null) {
            g.setColor(prop.owner.tokenColor);
        } 
        else if(cellIndex == 0 || cellIndex == 4 || cellIndex ==8 || cellIndex == 12)
        {
            g.setColor(Color.DARK_GRAY);
        }
        else {
            g.setColor(Color.LIGHT_GRAY);
        }
        g.fillRect(x, y, size, size);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, size, size);
        g.drawString(label, x + 5, y + 15);
        
        // Draw houses.
        if (prop != null) {
            for (int i = 0; i < prop.houses; i++) {
                int houseSize = 8;
                int hx = x + 5 + (i * (houseSize + 2));
                int hy = y + size - houseSize - 5;
                g.setColor(Color.DARK_GRAY);
                g.fillRect(hx, hy, houseSize, houseSize);
                g.setColor(Color.BLACK);
                g.drawRect(hx, hy, houseSize, houseSize);
            }
        }
    }
    
    // Returns the center point.
    private Point getCellCenter(int cellIndex, int cellSize, int panelWidth, int panelHeight) {
        int cx = 0, cy = 0;
        if (cellIndex >= 0 && cellIndex <= 4) { // top row
            cx = cellIndex * cellSize + cellSize / 2;
            cy = cellSize / 2;
        } else if (cellIndex >= 5 && cellIndex <= 7) { // right column
            int j = cellIndex - 5;
            cx = panelWidth - cellSize / 2;
            cy = cellSize + j * cellSize + cellSize / 2;
        } else if (cellIndex >= 8 && cellIndex <= 12) { // bottom row (reverse order)
            int pos = 12 - cellIndex;
            cx = pos * cellSize + cellSize / 2;
            cy = panelHeight - cellSize / 2;
        } else if (cellIndex >= 13 && cellIndex <= 15) { // left column (bottom-to-top)
            int pos = 15 - cellIndex;
            cx = cellSize / 2;
            cy = panelHeight - cellSize - (pos + 1) * cellSize + cellSize / 2;
        }
        return new Point(cx, cy);
    }
    
    //handele icon showing
    private Point[] getOffsets(int count) {
        Point[] offsets;
        switch (count) {
            case 1:
                offsets = new Point[]{ new Point(0, 0) };
                break;
            case 2:
                offsets = new Point[]{ new Point(-8, 0), new Point(8, 0) };
                break;
            case 3:
                offsets = new Point[]{ new Point(-8, -8), new Point(8, -8), new Point(0, 8) };
                break;
            case 4:
                offsets = new Point[]{ new Point(-8, -8), new Point(8, -8), new Point(-8, 8), new Point(8, 8) };
                break;
            default:
                offsets = new Point[count];
                break;
        }
        return offsets;
    }
}
