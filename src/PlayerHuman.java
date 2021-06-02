import java.util.regex.Pattern;
import helpers.ConsoleHelper;
import helpers.CoordinateHelper;
import helpers.TextHelper;

/**
 * Represents a human player
 */
public class PlayerHuman extends Player {

    public PlayerHuman() {
        setPlayerName();
    }

    /**
     * Asks human player to shoot on an enemy cell
     * 
     * @param Player enemy
     * 
     * @return void
     */
    protected void shoot(Player enemy) {
        boolean error = true;
        int x = 0;
        int y = 0;

        ConsoleHelper.eraseConsole();
        board.showPlayBoard(this, enemy);

        while (error) {
            String input = "";
            System.out.print(playerName + ", sur quelle case souhaitez-vous tirer ? ");
            try {
                input = in.readLine();
            } catch (java.io.IOException e) {
                System.out.println("Une erreur est survenue : " + e);
            }
            if (Pattern.matches("[A-Ja-j][1-9]{1}[0]{0,1}", input)) {
                x = Integer.valueOf(input.substring(1)) - 1;
                y = Integer.valueOf(CoordinateHelper.letterCoordinateToNumber(input.substring(0, 1).toUpperCase()));
                if (CoordinateHelper.isValid(x, y)) {
                    if (!enemy.getBoard().getCell(x, y).isShot()) {
                        error = false;
                    } else {
                        System.out.println("Vous avez déjà tiré sur cette case.");
                    }
                } else {
                    System.out.println("Cette coordonnée est invalide.");
                }
            }
        }

        Cell targetCell = enemy.getBoard().getCell(x, y);
        int cellValue = targetCell.getId();

        targetCell.shoot();
        setLastCellShot(x, y);
        incrementStatNbTotalShot();

        if (cellValue > 0) {
            Boat boatHitted = enemy.getBoard().getBoats(cellValue);
            boatHitted.getCells(x, y).shoot();
            incrementStatNbSuccessfullShot();
            board.showPlayBoard(this, enemy);
            if (boatHitted.isSunk()) {
                incrementStatNbBoatShot();
                System.out.println("Félicitations, vous avez coulé le " + boatHitted.getName().toLowerCase() + " !");
            } else {
                System.out.println("Vous avez touché un navire de votre adversaire !");
            }
        } else {
            board.showPlayBoard(this, enemy);
            System.out.println("Dommage, réessayez au prochain tour !");
        }
        ConsoleHelper.sleep(4000);
    }

    /**
     * Asks the player to place his ships.
     * A check is made to ensure that the placement of the boats respects the rules of the game.
     * 
     * @return void
     */
    protected void placeBoats() {
        for (int i = 0; i < Config.getNbBoats(); i++) {
            boolean error = true;
            String input = "";
            ConsoleHelper.eraseConsole();
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                            Placement des bateaux                             ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println(playerName + ", vous devez placer vos navires sur la grille de jeu.");
            System.out.println("Vous devez pour cela indiquer l'orientation de votre bateau ainsi que son point d'application.");
            System.out.println("Si vous indiquez \"" + TextHelper.ANSI_GREEN + "V" + TextHelper.ANSI_RESET + ""+ TextHelper.ANSI_PURPLE + "C6" + TextHelper.ANSI_RESET+ "\" pour un bateau de 3 cases, le bateau sera orienté " + TextHelper.ANSI_GREEN + "verticalement"+ TextHelper.ANSI_RESET + ", débutera en " + TextHelper.ANSI_PURPLE + "C6" + TextHelper.ANSI_RESET+ " et se terminera en F6.");
            System.out.println("Si vous indiquez \"" + TextHelper.ANSI_GREEN + "H" + TextHelper.ANSI_RESET + ""+ TextHelper.ANSI_PURPLE + "I3" + TextHelper.ANSI_RESET+ "\" pour un bateau de 3 cases, le bateau sera orienté " + TextHelper.ANSI_GREEN+ "horizontalement" + TextHelper.ANSI_RESET + ", débutera en " + TextHelper.ANSI_PURPLE + "I3"+ TextHelper.ANSI_RESET + " et se terminera en I6.");
            System.out.println("Faites attention à ne pas sortir du plateau de jeu, à ne pas faire chevaucher deux bateaux et à ne pas coller deux bateaux ensembles, auquel cas vous devrez recommencer le placement de votre bateau.");
            System.out.println("Soyez stratégique ! Préparez-vous, la bataille va bientôt commencer ;)");
            System.out.println();
            System.out.println("════════════════════════════════════════════════════════════════════════════════");
            System.out.println();
            int boatSize = Integer.valueOf(Config.getBoatsConfig(i)[2]);
            board.showPersonnalBoard();
            System.out.println(playerName + ", vous devez placer votre " + Config.getBoatsConfig(i)[1] + " (" + boatSize + " cases).");
            while (error) {
                System.out.print("Veuillez choisir une direction et un point d'application (ex : VC6) : ");
                try {
                    input = in.readLine();
                } catch (java.io.IOException e) {
                    System.out.println("Une erreur est survenue : " + e);
                }
                if (Pattern.matches("^[HVhv][abcdefghijABCDEFGHIJ][1-9][0]{0,1}", input)) {
                    String direction = input.substring(0, 1).toUpperCase();
                    int x = Integer.valueOf(input.substring(2)) - 1;
                    int y = Integer.valueOf(CoordinateHelper.letterCoordinateToNumber(input.substring(1, 2).toUpperCase()));
                    Cell[] boatCoordinates = board.generateBoatCoordinates(x, y, direction, boatSize, Integer.valueOf(Config.getBoatsConfig(i)[0]));

                    System.out.println();
                    if (x < 0 || x > 9) {
                        System.out.println("La composante X de votre bateau est invalide.");
                    } else if (y < 0 || y > 9) {
                        System.out.println("La composante Y de votre bateau est invalide.");
                    } else if (!board.isInBoard(boatCoordinates)) {
                        System.out.println("Votre bateau sort du plateau.");
                    } else if (board.existsOverlap(boatCoordinates)) {
                        System.out.println("Votre bateau se chevauche avec un autre de vos bateaux.");
                    } else if (board.existsNeighbors(boatCoordinates)) {
                        System.out.println("Votre bateau ne doit pas être accolé avec un autre de vos bateaux.");
                    } else {
                        board.addBoat(new Boat(boatCoordinates, Integer.valueOf(Config.getBoatsConfig(i)[0]), Config.getBoatsConfig(i)[1]));
                        error = false;
                    }
                }
            }
            if (i == Config.getNbBoats() - 1) {
                ConsoleHelper.eraseConsole();
                board.showPersonnalBoard();
                System.out.println(playerName + ", vous avez placé tous vos navires. Voici un aperçu de votre grille de jeu.");
                ConsoleHelper.sleep(3500);
            }
        }
    }

    /**
     * Asks the player to choose his pseudo
     * 
     * @return void
     */
    protected void setPlayerName() {
        String input = "";
        boolean error = true;
        do {
            System.out.print("Comment souhaitez-vous que l'on vous appelle (3-12 caractères) ? ");
            try {
                input = in.readLine().replaceAll("\\s", "-");
            } catch (java.io.IOException e) {
                System.out.println("Une erreur est survenue : " + e);
            }
            if (Pattern.matches("[A-Za-z-éèï]+", input) && (input.length() >= 3) && (input.length() <= 12)) {
                error = false;
            }
        } while (error);
        this.playerName = input;
    }
}