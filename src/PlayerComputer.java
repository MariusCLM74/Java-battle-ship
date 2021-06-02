import java.util.Random;
import java.util.regex.Pattern;
import helpers.ConsoleHelper;
import helpers.CoordinateHelper;

/**
 * Represents a computer player
 */
public class PlayerComputer extends Player {

    private int difficulty;
    private Cell[] knownBoatCells = new Cell[0];
    private Cell[] potentialCells = new Cell[0];
    private int currentBoat = 0;

    public PlayerComputer() {
        setDifficulty();
        setPlayerName();
    }

    /**
     * Shoots an enemy cell determined by different algorithms for different levels of difficulties
     * 
     * @param Player enemy
     * 
     * @return void
     */
    protected void shoot(Player enemy) {
        for (int i = 0; i < 2; i++) {
            ConsoleHelper.eraseConsole();
            System.out.print(playerName + " est en train de jouer");
            ConsoleHelper.sleep(375);
            System.out.print(".");
            ConsoleHelper.sleep(375);
            System.out.print(".");
            ConsoleHelper.sleep(375);
            System.out.print(".");
        }
        System.out.println();

        boolean error = true;
        Cell targetCell = new Cell(-1, -1);

        while (error) {

            switch (difficulty) {
                case 1:
                    targetCell = enemy.getBoard().getCells()[(int) (Math.random() * 10)][(int) (Math.random() * 10)];
                    break;
                case 2:

                    if (currentBoat != 0) {
                        targetCell = potentialCells[(int) (Math.random() * potentialCells.length)];
                        if (knownBoatCells.length > 1) {
                            updateNonPotentialCells(knownBoatCells, enemy);
                        }
                    } else {
                        targetCell = enemy.getBoard()
                                .getCells()[(int) (Math.random() * 10)][(int) (Math.random() * 10)];
                    }
                    break;
                case 3:
                    int[] coordinates = nickBerryAlgortithm(enemy.getBoard());
                    targetCell = enemy.getBoard().getCells()[coordinates[0]][coordinates[1]];
                    break;
            }

            if (!targetCell.isShot() && targetCell.isPotential()) {
                if (targetCell.getId() > 0) {
                    currentBoat = targetCell.getId();
                    enemy.getBoard().getBoats(targetCell.getId()).getCells(targetCell.getX(), targetCell.getY())
                            .shoot();
                    knownBoatCells = cellArrayAdd(knownBoatCells, targetCell);
                    potentialCells = updatePotentialCells(knownBoatCells, enemy);
                    incrementStatNbSuccessfullShot();
                    if (enemy.getBoard().getBoats(targetCell.getId()).isSunk()) {
                        incrementStatNbBoatShot();
                        potentialCells = updatePotentialCells(knownBoatCells, enemy);
                        freezeCells(potentialCells, enemy);
                        currentBoat = 0;
                        knownBoatCells = new Cell[0];
                        potentialCells = new Cell[0];
                    }
                }
                setLastCellShot(targetCell.getX(), targetCell.getY());
                targetCell.shoot();
                error = false;
                incrementStatNbTotalShot();
            }
        }
    }

    /**
     * Adds boats automatically on the grid
     * 
     * @return void
     */
    protected void placeBoats() {
        ConsoleHelper.eraseConsole();
        System.out.println(playerName + " est en train de placer ses bateaux...");
        for (int i = 0; i < Config.getNbBoats(); i++) {
            Random rand = new Random();
            String direction = "";
            int x = -1;
            int y = -1;
            int boatSize = Integer.valueOf(Config.getBoatsConfig(i)[2]);
            boolean error = true;
            do {
                int randomDirection = rand.nextInt(2);
                if (randomDirection == 1) {
                    direction = "H";
                    int xLimit = 10 - boatSize;
                    x = rand.nextInt(xLimit + 1);
                    y = rand.nextInt(11);
                } else {
                    direction = "V";
                    int yLimit = -boatSize + 10;
                    x = rand.nextInt(yLimit + 1);
                    y = rand.nextInt(11);
                }
                Cell[] boatCoordinates = board.generateBoatCoordinates(x, y, direction, boatSize,
                        Integer.valueOf(Config.getBoatsConfig(i)[0]));
                if (board.isInBoard(boatCoordinates) && !board.existsOverlap(boatCoordinates)
                        && !board.existsNeighbors(boatCoordinates)) {
                    board.addBoat(new Boat(boatCoordinates, Integer.valueOf(Config.getBoatsConfig(i)[0]),
                            Config.getBoatsConfig(i)[1]));
                    error = false;
                }
            } while (error);
        }
        ConsoleHelper.sleep(2500);
        System.out.println(playerName + " a placé tous ses bateaux.");
        ConsoleHelper.sleep(2000);
    }

    /**
     * Sets the computer's name
     * 
     * @return void
     */
    protected void setPlayerName() {
        switch (difficulty) {
            case 1:
                playerName = "Emma";
                break;
            case 2:
                playerName = "Jean-Jacques";
                break;
            case 3:
                playerName = "Nick Berry";
                break;
        }
    }

    /**
     * Sets the computer level
     * 
     * @return void
     */
    private void setDifficulty() {
        String input = "";
        
        System.out.println("╔═══════════╗  ╔══════════════════╗  ╔══════════════╗");
        System.out.println("║ 1. Facile ║  ║ 2. Intermédiaire ║  ║ 3. Difficile ║");
        System.out.println("╚═══════════╝  ╚══════════════════╝  ╚══════════════╝");
        System.out.println();

        do {
            System.out.print("Quel ordinateur souhaitez-vous affronter ? ");
            try {
                input = in.readLine();
            } catch (java.io.IOException e) {
                System.out.println("Une erreur est survenue : " + e);
            }
        } while (!Pattern.matches("[123]", input));
        this.difficulty = Integer.valueOf(input);
    }

    /**
     * Adds a cell to a cell array
     * 
     * @param Cell[] array
     * @param Cell cell
     * 
     * @return Cell[]
     */
    private Cell[] cellArrayAdd(Cell[] array, Cell cell) {
        Cell[] result = new Cell[array.length + 1];
        for (int i = 0; i < result.length - 1; i++) {
            result[i] = array[i];
        }
        result[result.length - 1] = cell;
        return result;
    }

    /**
     * Useful for difficulty level 3 : updates all the necessary cells to a non
     * potential cell
     * 
     * @param Cell[] array
     * @param Player player
     * 
     * @return void
     */
    private void freezeCells(Cell[] array, Player player) {
        for (Cell c : array) {
            player.getBoard().getCell(c.getX(), c.getY()).updatePotential(false);
        }
    }

    /**
     * Determines the cells that can be a boat based on the known boat cells
     * 
     * @param Cell[] knownBoatCells
     * @param Player player
     * 
     * @return Cell[]
     */
    private Cell[] updatePotentialCells(Cell[] knownBoatCells, Player player) {

        Cell[] result = new Cell[0];

        int[][] testingCoordinates = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };

        System.out.println();

        for (Cell c : knownBoatCells) {
            for (int[] coordinates : testingCoordinates) {
                if (CoordinateHelper.isValid(c.getX() + coordinates[0], c.getY() + coordinates[1])) {

                    result = cellArrayAdd(result,
                            player.getBoard().getCell(c.getX() + coordinates[0], c.getY() + coordinates[1]));

                }
            }
        }
        return result;
    }

    /**
     * Sets the nearby cells to not potential
     * 
     * @param Cell[] array
     * @param Player player
     * 
     * @return void
     */
    private void updateNonPotentialCells(Cell[] array, Player player) {
        boolean vertical = array[0].getX() == array[1].getX();

        int[] tempCoordintates = { -1, 1 };

        if (vertical) {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < 2; j++) {
                    int x = array[i].getX() + tempCoordintates[j];
                    if (CoordinateHelper.isValid(x, array[i].getY())) {
                        player.getBoard().getCell(x, array[i].getY()).updatePotential(false);
                    }
                }
            }
        } else {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < 2; j++) {
                    int y = array[i].getY() + tempCoordintates[j];
                    if (CoordinateHelper.isValid(array[i].getX(), y)) {
                        player.getBoard().getCell(array[i].getX(), y).updatePotential(false);
                    }
                }
            }
        }

    }

    /**
     * Returns a vector with the positons determined by an algorithm inspired by the
     * Nick Berry algorithm. This algorithm uses a propabilistic approach,
     * calculating all the possible positions for every remaining boat and then
     * returning the cell coordinates that has the highest probabilty of containg a
     * ship. This may sometimes result in moves thant seem unrealistic or non
     * human-like but, statistically speaking, it will always be the best move.
     * 
     * On average, this algorithm needs 40 moves to find every ship on the board.
     * 
     * A more thourough explanation can be foud here :
     * https://www.datagenetics.com/blog/december32011/
     * 
     * In fact, the algorithm you see below is even more powerful that the one seen
     * on the internet because this one takes into consideration the fact that boats
     * cannot be touching so, for exemple, if it sees two cells touching each other
     * horizontally, it knows that it doesn't need to check above and below these
     * two cells because a boat cannot be there. So, in theory, this algorithm can
     * solve on average a puzzle in less than 40 shots.
     * 
     * @param Board board
     * 
     * @return int[]
     */
    private int[] nickBerryAlgortithm(Board board) {

        int[][] simplifiedBoard = new int[10][10];

        // simplify the board to the bare minimum, we don't need the id of the cell and
        // other things

        for (int x = 0; x < simplifiedBoard.length; x++) {
            for (int y = 0; y < simplifiedBoard[0].length; y++) {
                if (!board.getCell(x, y).isShot()) {
                    simplifiedBoard[x][y] = 0; // if no information is known on the cell, set to 1
                } else if (board.getCell(x, y).getId() == 0) {
                    simplifiedBoard[x][y] = 1; // if there is a miss, set to 1
                } else {
                    simplifiedBoard[x][y] = 2; // if the cell is a hit, set to 2
                }
            }
        }

        int numberOfBoatsLeft = 0;

        // if a boat is sunk, set all the surronding cells to 1: a boat cannot be there.
        // Calculate as well the number of boats left

        int[][] testingCoordinates = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };

        for (int i = 0; i < board.getBoats().length; i++) {
            if (!board.getBoats()[i].isSunk()) {
                numberOfBoatsLeft++;
            } else {
                for (Cell c : board.getBoats()[i].getCells()) {
                    for (int[] coordinates : testingCoordinates) {
                        if (CoordinateHelper.isValid(c.getX() + coordinates[0], c.getY() + coordinates[1])) {
                            simplifiedBoard[c.getX() + coordinates[0]][c.getY() + coordinates[1]] = 1;
                        }
                    }
                }
            }
        }

        // if we have two neighboring cells, another boat cannot be touching so set
        // these cells to 1: a boat cannot be there

        Cell[] everyShotCells = new Cell[0];

        for (int x = 0; x < simplifiedBoard.length; x++) {
            for (int y = 0; y < simplifiedBoard[0].length; y++) {

                if (board.getCell(x, y).isShot() && board.getCell(x, y).getId() != 0) {
                    everyShotCells = cellArrayAdd(everyShotCells, board.getCell(x, y));
                }

            }
        }

        for (int i = 0; i < everyShotCells.length; i++) {
            for (int j = 0; j < everyShotCells.length; j++) {
                if (neighbors(everyShotCells[i], everyShotCells[j]) && j != i) {

                    boolean vertical = everyShotCells[i].getX() == everyShotCells[j].getX();

                    if (vertical) {
                        if (CoordinateHelper.isValid(everyShotCells[i].getX() - 1, everyShotCells[i].getY())) {
                            simplifiedBoard[everyShotCells[i].getX() - 1][everyShotCells[i].getY()] = 1;
                            simplifiedBoard[everyShotCells[j].getX() - 1][everyShotCells[j].getY()] = 1;
                        }
                        if (CoordinateHelper.isValid(everyShotCells[i].getX() + 1, everyShotCells[i].getY())) {
                            simplifiedBoard[everyShotCells[i].getX() + 1][everyShotCells[i].getY()] = 1;
                            simplifiedBoard[everyShotCells[j].getX() + 1][everyShotCells[j].getY()] = 1;
                        }
                    } else {
                        if (CoordinateHelper.isValid(everyShotCells[i].getX(), everyShotCells[i].getY() - 1)) {
                            simplifiedBoard[everyShotCells[i].getX()][everyShotCells[i].getY() - 1] = 1;
                            simplifiedBoard[everyShotCells[j].getX()][everyShotCells[j].getY() - 1] = 1;
                        }
                        if (CoordinateHelper.isValid(everyShotCells[i].getX(), everyShotCells[i].getY() + 1)) {
                            simplifiedBoard[everyShotCells[i].getX()][everyShotCells[i].getY() + 1] = 1;
                            simplifiedBoard[everyShotCells[j].getX()][everyShotCells[j].getY() + 1] = 1;
                        }
                    }

                }
            }
        }
        // register the length of every boat left

        int[] lengthOfBoatsLeft = new int[numberOfBoatsLeft];

        int ind = 0;

        for (int i = 0; i < board.getBoats().length; i++) {
            if (!board.getBoats()[i].isSunk()) {
                lengthOfBoatsLeft[ind] = board.getBoats()[i].getSize();
                ind++;
            }
        }

        // create a board that registers the number of times you can place a boat in a
        // given cell

        int[][] probabilityBoard = new int[10][10];

        for (int x = 0; x < probabilityBoard.length; x++) {
            for (int y = 0; y < probabilityBoard[0].length; y++) {
                probabilityBoard[x][y] = 0;
            }
        }

        // fill said board

        for (int b = 0; b < numberOfBoatsLeft; b++) { // for each remaining boat
            for (int d = 0; d <= 1; d++) { // for each direction : horizontal or vertical
                for (int x = 0; x < probabilityBoard.length; x++) { // for each x position
                    for (int y = 0; y < probabilityBoard[0].length; y++) { // for each y position
                        boolean possible = true;
                        boolean containsNonSunkBoat = false;
                        for (int i = 0; i < lengthOfBoatsLeft[b]; i++) { // determin if you can place the boat here
                            if (d == 0) {
                                if (probabilityBoard.length - x >= lengthOfBoatsLeft[b]) {
                                    if (simplifiedBoard[x + i][y] == 1) {
                                        possible = false;
                                    } else if (simplifiedBoard[x + i][y] == 2) { // determin there is a hit cell in
                                                                                 // these coordinates
                                        containsNonSunkBoat = true;
                                    }
                                } else {
                                    possible = false;
                                }
                            } else { // same thing for other direction
                                if (probabilityBoard.length - y >= lengthOfBoatsLeft[b]) {
                                    if (simplifiedBoard[x][y + i] == 1) {
                                        possible = false;
                                    } else if (simplifiedBoard[x][y + i] == 2) {
                                        containsNonSunkBoat = true;
                                    }
                                } else {
                                    possible = false;
                                }
                            }
                        }
                        if (possible) { // if you can place a boat here
                            for (int i = 0; i < lengthOfBoatsLeft[b]; i++) {
                                if (d == 0) {
                                    if (containsNonSunkBoat) {
                                        probabilityBoard[x + i][y] += 15; // if a hit cell is in the mix, add more
                                                                          // probability
                                    } else {
                                        probabilityBoard[x + i][y]++; // else, add 1 to the probabilty
                                    }
                                } else { // same thing for other direction
                                    if (containsNonSunkBoat) {
                                        probabilityBoard[x][y + i] += 15;
                                    } else {
                                        probabilityBoard[x][y + i]++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // if a cell is hit, you must not set it as your taget cell: to do so, set the
        // probability of that cell to 0

        for (int x = 0; x < probabilityBoard.length; x++) {
            for (int y = 0; y < probabilityBoard[0].length; y++) {
                if (simplifiedBoard[x][y] == 2) {
                    probabilityBoard[x][y] = 0;
                }
            }
        }

        // register the coordinates of the cell that has the highest probability

        int finalX = -1;
        int finalY = -1;
        int maxProbability = 0;

        for (int x = 0; x < probabilityBoard.length; x++) {
            for (int y = 0; y < probabilityBoard[0].length; y++) {
                if (probabilityBoard[x][y] >= maxProbability) {
                    finalX = x;
                    finalY = y;
                    maxProbability = probabilityBoard[x][y];
                }
            }
        }

        int[] result = { finalX, finalY };
        return result;
    }

    /**
     * Returns if two cells are neighbors
     * 
     * @param Cell cell1
     * @param Cell cell2
     * 
     * @return boolean
     */
    private boolean neighbors(Cell cell1, Cell cell2) {
        int[][] neighborCells1 = { { 0 + cell1.getX(), 1 + cell1.getY() }, { 0 + cell1.getX(), -1 + cell1.getY() },
                { -1 + cell1.getX(), 0 + cell1.getY() }, { 1 + cell1.getX(), 0 + cell1.getY() } };

        for (int[] coor1 : neighborCells1) {
            if (coor1[0] == cell2.getX() && coor1[1] == cell2.getY()) {
                return true;
            }
        }
        return false;
    }
}