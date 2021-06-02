import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import helpers.ConsoleHelper;
import helpers.TextHelper;

/**
 * Represents a game
 */
public class Game {

    private Player player1;
    private Player player2;
    private int playerPlay;
    private String playerWinner;
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Setup game
     * 
     * @return void
     */
    public void init() {
        printGameLauncher();

        player1 = new PlayerHuman();

        String opponentType = setOpponentType();

        if (opponentType.equals("P")) {
            System.out.println("C'est noté ! Vous allez jouer contre un autre joueur.");
            System.out.println("D'ailleurs, demandons quelques infos au deuxième joueur.");
            System.out.println();
            player2 = new PlayerHuman();
        } else {
            System.out.println("C'est noté ! Vous allez jouer contre un ordinateur.");
            System.out.println();
            player2 = new PlayerComputer();
        }

        ConsoleHelper.eraseConsole();
        System.out.println("Le placement des bateaux va débuter !");
        ConsoleHelper.sleep(2500);

        player1.placeBoats();
        player2.placeBoats();

        setFirstPlayer();
    }
    
    /**
     * Takes care of turns
     * 
     * @return void
     */
    public void play() {
        do {
            if (playerPlay == 1) {
                ConsoleHelper.eraseConsole();
                if (player1 instanceof PlayerHuman) {
                    System.out.println(player1.getPlayerName() + ", c'est à vous de jouer !");
                    ConsoleHelper.sleep(2000);
                }
                player1.shoot(player2);
                playerWinner = player1.getPlayerName();
                playerPlay = 2;

            } else {
                ConsoleHelper.eraseConsole();
                if (player2 instanceof PlayerHuman) {
                    System.out.println(player2.getPlayerName() + ", c'est à vous de jouer !");
                    ConsoleHelper.sleep(2000);
                }
                player2.shoot(player1);
                playerWinner = player2.getPlayerName();
                playerPlay = 1;
            }
        } while (!over());
    }

    /**
     * Shows winner's name and game stats
     * 
     * @return void
     */
    public void end() {
        ConsoleHelper.eraseConsole();
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
        System.out.print("║");
        TextHelper.generateSpaceBlanks((80 - (50 + playerWinner.length())) / 2);
        System.out.print(playerWinner + " a gagné la partie ! Félicitations à vous deux !");
        TextHelper.generateSpaceBlanks((80 - (50 + playerWinner.length())) / 2);
        System.out.println("║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
        player1.printStats();
        player2.printStats();
        System.out.println();
        System.out.println("     ╔══════════════════════╗  ╔════════════════════════╗  ╔══════════════════╗  ╔════════════════════╗");
        System.out.println("     ║ 0. Retourner au menu ║  ║ 1. Relancer une partie ║  ║ 2. Règles du jeu ║  ║ 3. En savoir  plus ║");
        System.out.println("     ╚══════════════════════╝  ╚════════════════════════╝  ╚══════════════════╝  ╚════════════════════╝");
    }

    /**
     * Checks if the game is over
     * 
     * @return boolean
     */
    private boolean over() {
        boolean response1 = true;
        boolean response2 = true;
        for (int i = 0; i < player1.getBoard().getBoats().length; i++) {
            if (!player1.getBoard().getBoats()[i].isSunk()) {
                response1 = false;
            }
        }
        for (int i = 0; i < player2.getBoard().getBoats().length; i++) {
            if (!player2.getBoard().getBoats()[i].isSunk()) {
                response2 = false;
            }
        }
        return (response1 || response2);
    }

    /**
     * Sets the type of the opponent
     * 
     * @return String
     */
    private String setOpponentType() {
        String input = "";
        System.out.println();
        System.out.println("╔═══════════╗  ╔═══════════════╗");
        System.out.println("║ P. Joueur ║  ║ C. Ordinateur ║");
        System.out.println("╚═══════════╝  ╚═══════════════╝");
        System.out.println();
        do {
            System.out.print("Quel type d'adversaire souhaitez-vous affronter ? ");
            try {
                input = in.readLine();
            } catch (java.io.IOException e) {
                System.out.println("Une erreur est survenue : " + e);
            }
        } while (!Pattern.matches("[CcPp]{1}", input));
        return input.toUpperCase();
    }

    /**
     * Randomly sets the order of play between the two players
     *
     * @return void
     */
    private void setFirstPlayer() {
        this.playerPlay = (int) Math.round(Math.random() * 2 + 1);
    }

    /**
     * Shows the game launcher
     *
     * @return void
     */
    private void printGameLauncher() {
        ConsoleHelper.eraseConsole();
        ConsoleHelper.sleep(350);
        System.out.println(" ____            _             _   _   _                                            _        ");
        ConsoleHelper.sleep(350);
        System.out.println("|  _ \\          | |           (_) | | | |                                          | |       ");
        ConsoleHelper.sleep(350);
        System.out.println("| |_) |   __ _  | |_    __ _   _  | | | |   ___     _ __     __ _  __   __   __ _  | |   ___ ");
        ConsoleHelper.sleep(350);
        System.out.println("|  _ <   / _` | | __|  / _` | | | | | | |  / _ \\   | '_ \\   / _` | \\ \\ / /  / _` | | |  / _ \\");
        ConsoleHelper.sleep(350);
        System.out.println("| |_) | | (_| | | |_  | (_| | | | | | | | |  __/   | | | | | (_| |  \\ V /  | (_| | | | |  __/ ");
        ConsoleHelper.sleep(350);
        System.out.println("|____/   \\__,_|  \\__|  \\__,_| |_| |_| |_|  \\___|   |_| |_|  \\__,_|   \\_/    \\__,_| |_|  \\___|");
        ConsoleHelper.sleep(350);
        System.out.println();
        System.out.println("Bienvenue dans la bataille navale !");
        System.out.println();
    }
}