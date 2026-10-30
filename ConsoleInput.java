import java.util.Scanner;

/** Wrapper for validated command-line input. */
public class ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);

    public int readInt(String prompt, int minimum, int maximum) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                if (value >= minimum && value <= maximum) {
                    return value;
                }
            } else {
                scanner.next(); // Consume invalid input
            }
            System.out.println("Please enter an integer between " + minimum + " and " + maximum + ".");
        }
    }

    public boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.next().trim().toLowerCase();
            if (input.equals("y") || input.equals("n")) {
                return input.equals("y");
            }
            System.out.println("Please enter 'y' or 'n'.");
        }
    }

    public void close() { scanner.close(); }
}
