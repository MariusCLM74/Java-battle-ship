/**
 * Represents a board cell
 */
public class Cell {

    private int x;
    private int y;
    private int id;             // equals the id of the boat it belongs to (0 by default)
    private boolean shot;       // indicates if a cell has been shot by the opponent or not
    private boolean potential;  // useful for level 3 BOT, indicates if a cell is a potential boat or not

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = 0;
        this.shot = false;
        this.potential = true;
    }

    /**
     * Assigns the cell to a boat when called
     * 
     * @param int id
     * 
     * @return void
     */
    public void addBoat(int id) {
        this.id = id;
    }

    /**
     * Updates the potential boolean to the indicated value
     * 
     * @param boolean value
     * 
     * @return void
     */
    public void updatePotential(boolean value) {
        potential = value;
    }

    /**
     * Returns whether or not the cell is a potential boat cell or not
     * 
     * @return boolean
     */
    public boolean isPotential() {
        return potential;
    }

    /**
     * Returns the x value
     * 
     * @return int
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the y value
     * 
     * @return int
     */
    public int getY() {
        return this.y;
    }

    /**
     * Updates the shot boolean when cell is shot
     * 
     * @return void
     */
    public void shoot() {
        this.shot = true;
    }

    /**
     * Returns if the cell was shot or not
     * 
     * @return boolean
     */
    public boolean isShot() {
        return this.shot;
    }

    /**
     * Returns the id of the cell
     * 
     * @return int
     */
    public int getId() {
        return this.id;
    }
}