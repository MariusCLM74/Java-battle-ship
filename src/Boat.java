/**
 * Represents a boat
 */
public class Boat {
    private int id;
    private String name;
    private Cell[] cells;
    private String direction;

    public Boat(Cell[] cells, int id, String name) {
        this.name = name;
        this.cells = cells;
        this.id = id;
    }

    /**
     * Computes if the boat was sunk or not
     * 
     * @return boolean
     */
    public boolean isSunk() {
        for (int i = 0; i < this.cells.length; i++) {
            if (!this.cells[i].isShot()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a specific cell
     * 
     * @return Cell
     */
    public Cell getCells(int cellPosition) {
        return this.cells[cellPosition];
    }

    /**
     * Returns a specific cell
     * 
     * @return Cell
     */
    public Cell getCells(int x, int y) {
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].getX() == x && cells[i].getY() == y) {
                return cells[i];
            }
        }
        return cells[0];
    }

    /**
     * Returns the array of cells belonging to the boat
     * 
     * @return Cell[]
     */
    public Cell[] getCells() {
        return this.cells;
    }

    /**
     * Returns the boat's id
     * 
     * @return int
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the boat's size
     * 
     * @return int
     */
    public int getSize() {
        return cells.length;
    }

    /**
     * Returns the boat's name
     * 
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the boat's direction
     * 
     * @return String
     */
    public String getDirection() {
        return this.direction;
    }
}