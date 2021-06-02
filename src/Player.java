import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Represents a player
 */
public abstract class Player {

    protected String playerName;
    protected Board board = new Board();
    protected BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private int statNbTotalShot;
    private int statNbSuccessfullShot;
    private int statNbBoatShot;
    private Cell lastCellShot = new Cell(-1, -1);

    protected abstract void placeBoats();

    protected abstract void shoot(Player enemy);

    protected abstract void setPlayerName();

    /**
     * Increases the number of shots fired by the player
     * 
     * @return void
     */
    protected void incrementStatNbTotalShot() {
        statNbTotalShot++;
    }

    /**
     * Increases the number of successfull shots fired by the player
     * 
     * @return void
     */
    protected void incrementStatNbSuccessfullShot() {
        statNbSuccessfullShot++;
    }

    /**
     * Increases the number of ships sunk by the player
     * 
     * @return void
     */
    protected void incrementStatNbBoatShot() {
        statNbBoatShot++;
    }

    /**
     * Gets the board of the player
     * 
     * @return Board
     */
    protected Board getBoard() {
        return this.board;
    }

    /**
     * Gets the name of the player
     * 
     * @return String
     */
    protected String getPlayerName() {
        return this.playerName;
    }

    /**
     * Show player stats
     * 
     * @return void
     */
    protected void printStats() {
        System.out.println();
        System.out.println("║");
        System.out.println("║ Statistiques de jeu de " + playerName + " :");
        System.out.println("║");
        System.out.println("║ Nombre de tirs réalisés : " + statNbTotalShot);
        System.out.println("║ Nombre de tirs réussis : " + statNbSuccessfullShot);
        System.out.println("║ Précision : " + (double) Math.round(((double) (statNbSuccessfullShot) / (double) (statNbTotalShot)) * 100.0) + "%");
        System.out.println("║ Nombre de bateaux adverses détruis : " + statNbBoatShot + "/" + Config.getNbBoats());
        System.out.println("║");
    }

    /**
     * Gets the last shot made by the player
     * 
     * @return Cell
     */
    protected Cell getLastCellShot() {
        return lastCellShot;
    }

    /**
     * Saves the last shot made by the player
     * 
     * @param int x
     * @param int y
     * 
     * @return void
     */
    protected void setLastCellShot(int x, int y) {
        lastCellShot = new Cell(x, y);
    }
}