package helpers;

/**
 * Set of useful methods and properties for console and display
 */
public class ConsoleHelper {
    
    /**
     * Clears the console screen
     *
     * @return void
     */
    public static void eraseConsole() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("Linux")){
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("cmd", "/c", "clear").inheritIO().start().waitFor();
            }
        } catch (final Exception e){
            System.out.println("Erreur : " + e);
        }
    }

    /**
     * Pauses in program execution
     * 
     * @param long millies
     *
     * @return void
     */
    public static void sleep(long millies) {
        try {
            Thread.sleep(millies);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}