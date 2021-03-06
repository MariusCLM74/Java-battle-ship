import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import helpers.ConsoleHelper;

/**
 * Represents the game launcher
 */
public class Menu {

    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Ask user to select a menu option
     * 
     * @return int
     */
    public int selectOption() {
        String input = "";
        do {
            System.out.print("     Que souhaitez-vous faire ? ");
            try {
                    input = in.readLine();
            } catch (java.io.IOException e) {
                    System.out.println("Une erreur est survenue : " + e);
            }
        } while (!Pattern.matches("[0123]", input));
        return Integer.valueOf(input);
    }

    /**
     * Show main menu
     * 
     * @return void
     */
    public void showMenu() {
        ConsoleHelper.eraseConsole();
        System.out.println();
        System.out.println("                                     |__");
        System.out.println("                                     |\\/");
        System.out.println("                                     ---");
        System.out.println("                                     / | [");
        System.out.println("                              !      | |||");
        System.out.println("                            _/|     _/|-++'");
        System.out.println("                        +  +--|    |--|--|_ |-");
        System.out.println("                     { /|__|  |/\\__|  |--- |||__/");
        System.out.println("                    +---------------___[}-_===_.'____                 /\"");
        System.out.println("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _");
        System.out.println(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7");
        System.out.println("|                                                                     BB-61/");
        System.out.println(" \\_________________________________________________________________________|");
        System.out.println();
        System.out.println("????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        System.out.println("???                      Bienvenue sur la bataille navale                        ???");
        System.out.println("????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        System.out.println();
        System.out.println("     ????????????????????????????????????????????????????????????????????????  ????????????????????????????????????????????????????????????  ??????????????????????????????????????????????????????????????????");
        System.out.println("     ??? 1. Lancer une partie ???  ??? 2. R??gles du jeu ???  ??? 3. En savoir  plus ???");
        System.out.println("     ????????????????????????????????????????????????????????????????????????  ????????????????????????????????????????????????????????????  ??????????????????????????????????????????????????????????????????");
        System.out.println();
    }

    /**
     * Show game rules
     * 
     * @return void
     */
    public void showRules() {
        ConsoleHelper.eraseConsole();
        System.out.println();
        System.out.println("????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        System.out.println("???                       Bataille navale : r??gles du jeu                        ???");
        System.out.println("????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        System.out.println();
        System.out.println("     Vous allez affronter un adversaire dans une bataille sans r??pit sur la mer des Cara??bes.");
        System.out.println();
        System.out.println("     Vous disposerez tous les deux d'une flotte compos??e de "+ Config.getNbBoats() + " navires :");
        for (int i = 0; i < Config.getBoatsConfig().length; i++) {
            String plurial = Integer.valueOf(Config.getBoatsConfig()[i][2]) > 1 ? "s" : "";
            System.out.println("    - 1 " + Config.getBoatsConfig()[i][1] + " ("        + Config.getBoatsConfig()[i][2] + " case" + plurial + ")");
        }
        System.out.println();
        System.out.println("     Votre objectif est simple : d??truire le plus rapidement possible la flotte de votre adversaire, avant que ce dernier ne d??truise la v??tre.");
        System.out.println("     Cependant, votre adversaire utilise une technique de camouflage derni??re g??n??ration qui rend impossible la d??tection de ses navires sur vos radars.");
        System.out.println("     Il va donc falloir ??tre strat??gique pour ??viter de gaspiller des torpilles !");
        System.out.println("     Vous disposez d'ailleurs de 100 torpilles que vous pouvez envoyer en direction de votre adversaire.");
        System.out.println();
        System.out.println("     Pr??parez votre strat??gie et incarnez d??s maintenant un capitaine de flotte maritime !");
        System.out.println();
        System.out.println("     ????????????????????????????????????????????????????????????????????????  ????????????????????????????????????????????????????????????????????????  ????????????????????????????????????????????????????????????  ??????????????????????????????????????????????????????????????????");
        System.out.println("     ??? 0. Retourner au menu ???  ??? 1. Lancer une partie ???  ??? 2. R??gles du jeu ???  ??? 3. En savoir  plus ???");
        System.out.println("     ????????????????????????????????????????????????????????????????????????  ????????????????????????????????????????????????????????????????????????  ????????????????????????????????????????????????????????????  ??????????????????????????????????????????????????????????????????");
        System.out.println();
        System.out.println();
    }

    /**
     * Show game and license information
     * 
     * @return void
     */
    public void showAbout() {
        ConsoleHelper.eraseConsole();
        System.out.println();
        System.out.println("????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        System.out.println("???                         En savoir plus sur ce projet                         ???");
        System.out.println("????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        System.out.println();
        System.out.println("     Ce jeu a ??t?? d??velopp?? dans le cadre du projet de fin d'ann??e du cours Algorithmique et programmation 1 & 2 dispens?? en premi??re ann??e de FIMI ?? l'INSA Lyon.");
        System.out.println("     Il utilise les notions d'algorithmique et de programmation en Java d??couvertes tout au long de l'ann??e mais ??galement d'autres notions d??couvertes dans un cadre extra-scolaire.");
        System.out.println();
        System.out.println("     --------------- Comment jouer ? ---------------");
        System.out.println();
        System.out.println("     Pour jouer ?? la bataille navale, t??l??chargez le dossier \\src et ex??cutez le fichier MaClassePrincipale.java.");
        System.out.println("     Le jeu se joue int??gralement ?? la console.");
        System.out.println("     Le jeu est disponible en version fran??aise uniquement.");
        System.out.println("     Il est possible de modifier le nombre de bateaux de chaque joueur ainsi que leur propri??t??s en ??ditant le fichier de configuration \\src\\Config.java.");
        System.out.println();
        System.out.println("     ------------------- Cr??dits -------------------");
        System.out.println();
        System.out.println("     2021 - R??utilisation et modification autoris??e");
        System.out.println("     Derni??re mise ?? jour le 02/06/2021");
        System.out.println();
        System.out.println("     @Marius Chalumeau - D??veloppeur");
        System.out.println("     @Lucile Bonnefoy - D??veloppeur");
        System.out.println("     @Jeremy Banks - D??veloppeur");
        System.out.println();
        System.out.println("     ????????????????????????????????????????????????????????????????????????  ????????????????????????????????????????????????????????????????????????  ????????????????????????????????????????????????????????????  ??????????????????????????????????????????????????????????????????");
        System.out.println("     ??? 0. Retourner au menu ???  ??? 1. Lancer une partie ???  ??? 2. R??gles du jeu ???  ??? 3. En savoir  plus ???");
        System.out.println("     ????????????????????????????????????????????????????????????????????????  ????????????????????????????????????????????????????????????????????????  ????????????????????????????????????????????????????????????  ??????????????????????????????????????????????????????????????????");
        System.out.println();
    }
}