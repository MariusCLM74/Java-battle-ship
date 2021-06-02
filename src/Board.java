import helpers.ConsoleHelper;
import helpers.CoordinateHelper;
import helpers.TextHelper;

/**
 * Represents a board which contains a 2D array of cells
 */
public class Board {

    private Cell[][] board;
    private Boat[] boats;
    private int nbBoats;

    public Board() {
        this.board = generateBlankBoard(10, 10);
        this.boats = new Boat[Config.getNbBoats()];
    }

    /**
     * Return a specific cell of the board
     * 
     * @param int x
     * @param int y
     *
     * @return Cell
     */
    public Cell getCell(int x, int y) {
        return board[x][y];
    }

    /**
     * Shows personnal board
     *
     * @return void
     */
    public void showPersonnalBoard() {
        System.out.println("  ╔═══════════════════════════════════════╗");
        System.out.println("  ║              Votre grille             ║");
        System.out.println("  ╚═══════════════════════════════════════╝");
        System.out.println();
        System.out.println("    1   2   3   4   5   6   7   8   9  10 ");

        for (int j = 0; j < this.board.length; j++) {

            if (j == 0) {
                System.out.println("  ╔═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╗");
            }

            for (int i = 0; i < this.board[j].length; i++) {
                if (i == 0) {
                    System.out.print(CoordinateHelper.numberCoordinateToLetter(j) + " ║");
                } else {
                    System.out.print("║");
                }

                if (this.board[i][j].getId() > 0) {
                    System.out.print(" " + String.valueOf(this.board[i][j].getId()) + " ");
                } else if (this.board[i][j].getId() == -1) {
                    System.out.print(" X ");
                } else {
                    System.out.print("   ");
                }

                if (i == this.board[j].length - 1) {
                    System.out.print("║");
                }
            }
            System.out.println();

            if (j == this.board.length - 1) {
                System.out.println("  ╚═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╝");
            } else {
                System.out.println("  ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣");
            }
        }
        System.out.println();
    }

    /**
     * Shows the play board, i.e. the board of both players
     * 
     * @param Player player
     * @param Player enemy
     *
     * @return void
     */
    public void showPlayBoard(Player player, Player enemy) {
        ConsoleHelper.eraseConsole();

        int titleBlanks1 = 39 - (14 + player.playerName.length());
        int titleBlanks2 = 39 - (10 + enemy.getPlayerName().length());

        Cell[][] enemyBoard = enemy.getBoard().getCells();

        System.out.println("  ═════════════════════════════════════════        ═════════════════════════════════════════");
        System.out.print("   ");
        TextHelper.generateSpaceBlanks(titleBlanks1 / 2);
        System.out.print("Votre grille, " + player.playerName);
        TextHelper.generateSpaceBlanks(titleBlanks1 / 2);
        System.out.print("          ");
        TextHelper.generateSpaceBlanks(titleBlanks2 / 2);
        System.out.print("Grille de " + enemy.getPlayerName());
        TextHelper.generateSpaceBlanks(titleBlanks2 / 2);
        System.out.println(" ");
        System.out.println("  ═════════════════════════════════════════        ═════════════════════════════════════════");
        System.out.println();
        System.out.println("    1   2   3   4   5   6   7   8   9  10            1   2   3   4   5   6   7   8   9  10");

        for (int j = 0; j < board.length; j++) {

            if (j == 0) {
                System.out.println("  ╔═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╗        ╔═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╗");
            }

            // We loop on the columns of the first grid (player's grid)
            for (int i = 0; i < board[j].length; i++) {
                if (i == 0) {
                    System.out.print(CoordinateHelper.numberCoordinateToLetter(j) + " ║");
                } else {
                    System.out.print("║");
                }

                // If it is the last shot did by the opponent, it is displayed in blue
                if (i == enemy.getLastCellShot().getX() && j == enemy.getLastCellShot().getY()) {
                    System.out.print(TextHelper.ANSI_CYAN_BACKGROUND);
                }

                if (board[i][j].getId() > 0 && board[i][j].isShot()) {
                    System.out.print(TextHelper.ANSI_RED + " X " + TextHelper.ANSI_RESET);
                } else if (board[i][j].getId() == 0 && board[i][j].isShot()) {
                    System.out.print(TextHelper.ANSI_BLUE + " O " + TextHelper.ANSI_RESET);
                } else if (board[i][j].getId() > 0 && !board[i][j].isShot()) {
                    System.out.print(" " + String.valueOf(board[i][j].getId()) + " ");
                } else {
                    System.out.print("   ");
                }

                // If it is the last shot did by the opponent, it is displayed in blue. We put it back here in black
                if (i == enemy.getLastCellShot().getX() && j == enemy.getLastCellShot().getY()) {
                    System.out.print(TextHelper.ANSI_BLACK_BACKGROUND);
                }

                if (i == board[j].length - 1) {
                    System.out.print("║");
                }
            }
            System.out.print("      ");
            // We loop on the columns of the second grid (opponent's grid on which the players' previous shots are marked)
            for (int i = 0; i < enemyBoard[j].length; i++) {
                if (i == 0) {
                    System.out.print(CoordinateHelper.numberCoordinateToLetter(j) + " ║");
                } else {
                    System.out.print("║");
                }

                if (enemyBoard[i][j].getId() > 0 && enemyBoard[i][j].isShot()) {
                    System.out.print(TextHelper.ANSI_RED + " X " + TextHelper.ANSI_RESET);
                } else if (enemyBoard[i][j].getId() == 0 && enemyBoard[i][j].isShot()) {
                    System.out.print(TextHelper.ANSI_BLUE + " O " + TextHelper.ANSI_RESET);
                } else {
                    System.out.print("   ");
                }

                if (i == enemyBoard[j].length - 1) {
                    System.out.print("║");
                }
            }
            System.out.println();

            if (j == board.length - 1) {
                System.out.println("  ╚═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╝        ╚═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╝");
            } else {
                System.out.println("  ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣        ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣");
            }
        }

        System.out.println();
        System.out.println("╔════════════════════════════════════════════╗  ╔════════════════════════════════════════════╗");
        System.out.print("║ " + player.playerName + ", voici l'état de vos troupes :");
        TextHelper.generateSpaceBlanks(46 - (34 + player.playerName.length()));
        System.out.println("║  ║  Voici les navires que vous avez coulés :  ║");

        if (player.getBoard().getNbBoats() != 0) {
            for (int i = 1; i <= player.getBoard().getNbBoats(); i++) {
                int lineSize = 4 + player.getBoard().getBoats(i).getName().length() + 4 + player.getBoard().getBoats(i).getSize() * 3 + 4;
                int blanksToGenerate = 46 - lineSize;

                System.out.print("║");
                System.out.print(" - " + player.getBoard().getBoats(i).getName() + " (" + player.getBoard().getBoats(i).getId() + ") ");
                TextHelper.generateSpaceBlanks(blanksToGenerate);
                for (int j = 0; j < player.getBoard().getBoats(i).getSize(); j++) {
                    System.out.print("[");
                    if (player.getBoard().getBoats(i).getCells(j).isShot()) {
                        System.out.print(TextHelper.ANSI_RED + "X" + TextHelper.ANSI_RESET);
                    } else {
                        System.out.print(" ");
                    }
                    System.out.print("]");
                }
                System.out.print("  ║  ║");
                lineSize = 4 + player.getBoard().getBoats(i).getName().length() + 3 + player.getBoard().getBoats(i).getSize() * 3;
                blanksToGenerate = 46 - lineSize;
                if (enemy.getBoard().getBoats(i).isSunk()) {
                    System.out.print(" - " + enemy.getBoard().getBoats(i).getName() + " ");
                    TextHelper.generateSpaceBlanks(blanksToGenerate);
                    for (int j = 0; j < enemy.getBoard().getBoats(i).getSize(); j++) {
                        System.out.print("[");
                        if (enemy.getBoard().getBoats(i).getCells(j).isShot()) {
                            System.out.print(TextHelper.ANSI_RED + "X" + TextHelper.ANSI_RESET);
                        } else {
                            System.out.print(" ");
                        }
                        System.out.print("]");
                    }
                    System.out.println(" ║");
                } else {
                    System.out.println("                                            ║");
                }
            }
        } else {
            System.out.println("║        Aucun navire pour le moment         ║");
        }
        System.out.println("╚════════════════════════════════════════════╝  ╚════════════════════════════════════════════╝");
        System.out.println();

        System.out.println("╔════════════════════════════════════════════╗  ╔════════════════════════════════════════════╗");
        System.out.println("║ " + TextHelper.ANSI_BLUE + "O" + TextHelper.ANSI_RESET + " : tir raté de votre adversaire           ║  ║ " + TextHelper.ANSI_BLUE + "O" + TextHelper.ANSI_RESET + " : tir raté que vous avez effectué        ║");
        System.out.println("║ " + TextHelper.ANSI_RED + "X" + TextHelper.ANSI_RESET + " : tir réussi de votre adversaire         ║  ║ " + TextHelper.ANSI_RED + "X" + TextHelper.ANSI_RESET + " : tir réussi que vous avez effectué      ║");
        System.out.println("║ " + TextHelper.ANSI_CYAN_BACKGROUND + " " + TextHelper.ANSI_BLACK_BACKGROUND + " : dernier tir de votre adversaire        ║  ║                                            ║");
        System.out.println("║ 1 à " + Config.getNbBoats() + " : vos navires                        ║  ║                                            ║");
        System.out.println( "╚════════════════════════════════════════════╝  ╚════════════════════════════════════════════╝");
        System.out.println();
    }

    /**
     * Adds a boat to the existing boat array
     * 
     * @param Boat boat
     * 
     * @return void
     */
    public void addBoat(Boat boat) {
        int ind = 0;
        while (this.boats[ind] != null) {
            ind++;
        }
        this.boats[ind] = boat;
        for (int i = 0; i < boat.getCells().length; i++) {
            this.board[boat.getCells()[i].getX()][boat.getCells()[i].getY()].addBoat(boat.getId());
        }
        nbBoats++;
    }

    /**
     * Checks that the boat doesn't have an immediate neighbor
     *
     * @param Cell[] cells
     * 
     * @return boolean
     */
    public boolean existsNeighbors(Cell[] cells) {

        /*
         * We take as parameter the potential cells occupied by the boat. 
         * We look at the cells of the two end points that we store in 4 variables.
         */
        int minX = cells[0].getX();
        int minY = cells[0].getY();
        int maxX = cells[cells.length - 1].getX();
        int maxY = cells[cells.length - 1].getY();

        int[][] cellsThatNeedTesting = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int[] c : cellsThatNeedTesting) {
                    int X = x + c[0];
                    int Y = y + c[1];

                    if (CoordinateHelper.isValid(X, Y) && this.board[X][Y].getId() > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks that the (simulated) coordinates of a boat don't exceed the limits of the board
     *
     * @param Cell[] cells
     * 
     * @return boolean
     */
    public boolean isInBoard(Cell[] cells) {
        for (Cell c : cells) {
            if (!CoordinateHelper.isValid(c.getX(), c.getY())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the coordinates of a boat overlap with another those of another boat
     *
     * @param Cell[] cells
     * 
     * @return boolean
     */
    public boolean existsOverlap(Cell[] cells) {
        for (int i = 0; i < cells.length; i++) {
            if (this.board[cells[i].getX()][cells[i].getY()].getId() != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes in a direction and a position and generates the list of the coordinates
     * of the cells that are concerned
     *
     * @param int    x
     * @param int    y
     * @param String direction
     * @param int    boatSize
     * @param int    boatId
     * 
     * @return Cell[]
     */
    public Cell[] generateBoatCoordinates(int x, int y, String direction, int boatSize, int boatId) {
        Cell[] response = new Cell[boatSize];
        for (int i = 0; i < boatSize; i++) {
            if (direction.equals("H")) {
                response[i] = new Cell(x + i, y);
            } else if (direction.equals("V")) {
                response[i] = new Cell(x, y + i);
            }
            response[i].addBoat(boatId);
        }
        return response;
    }

    /**
     * Gets the tab of boats of a player
     * 
     * @return Boat[]
     */
    public Boat[] getBoats() {
        return boats;
    }

    /**
     * Get a player boat
     * 
     * @param int boatId
     * 
     * @return Boat
     */
    public Boat getBoats(int boatId) {
        for (int i = 0; i < boats.length; i++) {
            if (boats[i].getId() == boatId) {
                return boats[i];
            }
        }
        return boats[0];
    }

    /**
     * Gets the tab of the board's cells
     * 
     * @return Cell[][]
     */
    public Cell[][] getCells() {
        return board;
    }

    /**
     * Fills up the board with empty cells
     * 
     * @param int w
     * @param int h
     * 
     * @return Cell[][]
     */
    private Cell[][] generateBlankBoard(int w, int h) {
        Cell[][] result = new Cell[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                result[i][j] = new Cell(i, j);
            }
        }
        return result;
    }

    /**
     * Return the number of boats
     * 
     * @return int
     */
    private int getNbBoats() {
        return nbBoats;
    }
}