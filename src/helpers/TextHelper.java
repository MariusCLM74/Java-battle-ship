package helpers;

/**
 * Set of methods and properties used to display text
 */
public class TextHelper {

    // Constants to call to change the color of the text or background
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    /**
     * Displays spaces one after the other
     *
     * @param int spaceSize : the number of spaces to generate
     * 
     * @return void
     */
    public static void generateSpaceBlanks(int spaceSize) {
        for (int i = 0; i < spaceSize; i++) {
            System.out.print(" ");
        }
    }

}