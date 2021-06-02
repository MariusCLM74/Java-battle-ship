package helpers;

/**
 * Set of methods and properties used for game coordinates
 */
public class CoordinateHelper {

    /**
     * Transforms a "letter" coordinate by its equivalent "number"
     *
     * @param String coordinate : the coordinate to transform
     * 
     * @return String
     */
    public static String letterCoordinateToNumber(String coordinate) {
        return String.valueOf(new String("ABCDEFGHIJ").indexOf(coordinate));
    }

    /**
     * Transforms a "number" coordinate by its "letter" equivalent
     *
     * @param int coordinate : the coordinate to transform
     * 
     * @return String
     */
    public static String numberCoordinateToLetter(int coordinate) {
        String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
        return letters[coordinate];
    }

    /**
     * Checks if the coordinate don't go out of the board
     *
     * @param int x
     * @param int y
     * 
     * @return boolean
     */
    public static boolean isValid(int x, int y) {
        return (x >= 0 && x <= 9 && y >= 0 && y <= 9);
    }
}