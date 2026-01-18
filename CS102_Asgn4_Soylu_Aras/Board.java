public class Board {
    // The board array holds labels for each cell.
    public String[] board;
    public Property[] properties;

    public Board() {
        board = new String[]{"0", "A", "B", "C", "1", "D", "E", "F", "2", "G", "H", "I", "3", "J", "K", "L"};
        properties = new Property[12];
        properties[0] = new Property("A", 2, 1, new int[]{1, 2, 3, 4, 6});
        properties[1] = new Property("B", 2, 1, new int[]{1, 2, 3, 4, 6});
        properties[2] = new Property("C", 2, 1, new int[]{1, 2, 3, 4, 6});
        properties[3] = new Property("D", 4, 1, new int[]{2, 2, 3, 3, 7});
        properties[4] = new Property("E", 4, 1, new int[]{2, 2, 3, 3, 7});
        properties[5] = new Property("F", 4, 1, new int[]{2, 2, 3, 3, 7});
        properties[6] = new Property("G", 6, 2, new int[]{1, 3, 4, 6, 7});
        properties[7] = new Property("H", 6, 2, new int[]{1, 3, 4, 6, 7});
        properties[8] = new Property("I", 6, 2, new int[]{1, 3, 4, 6, 7});
        properties[9] = new Property("J", 8, 3, new int[]{3, 3, 6, 6, 9});
        properties[10] = new Property("K", 8, 3, new int[]{3, 3, 6, 6, 9});
        properties[11] = new Property("L", 8, 3, new int[]{3, 3, 6, 6, 9});
    }
   
    public Property getPropertyAtCell(int index) {
        switch(index) {
            case 1: return properties[0];
            case 2: return properties[1];
            case 3: return properties[2];
            case 5: return properties[3];
            case 6: return properties[4];
            case 7: return properties[5];
            case 9: return properties[6];
            case 10: return properties[7];
            case 11: return properties[8];
            case 13: return properties[9];
            case 14: return properties[10];
            case 15: return properties[11];
            default: return null;
        }
    }
}
