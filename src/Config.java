/**
 * Config : game configuration file
 */
public abstract class Config {

    /**
     * The boats and their configuration
     * {:id, :name, :size}
     */
    private static final String[][] boats =
    {
        {"1", "Porte-avions", "5"},
        {"2", "Croiseur", "4",},
        {"3", "Contre-torpilleur", "3"},
        {"4", "Contre-torpilleur", "3"},
        {"5", "Torpilleur", "2"}
    };

    /**
     * Returns a given boat's configuration
     * 
     * @param int boatId
     *
     * @return String[]
     */
    public static String[] getBoatsConfig(int boatId) {
        if (boatId < 0 || boatId >= boats.length) {
            return new String[0];
        }
        return boats[boatId];
    }

    /**
     * Returns the configuration of every boat
     *
     * @return String[][]
     */
    public static String[][] getBoatsConfig() {
        return boats;
    }

    /**
     * Returns the number of boats
     *
     * @return int
     */
    public static int getNbBoats() {
        return boats.length;
    }
}