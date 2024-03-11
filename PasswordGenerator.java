import java.util.Random;
import java.util.Scanner;

public class PasswordGenerator {

    public static class Password {
        private String password;

        public Password(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public int calculateScore() {
            int score = 0;
            String uppers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String lowers = "abcdefghijklmnopqrstuvwxyz";

            if (password.length() < 8) {
                score += 10;
            } else if (password.length() >= 16) {
                score -= 10;
            }

            if (!password.equals(password.toLowerCase())) {
                for (int i = 0; i < password.length(); i++) {
                    if (uppers.indexOf(password.charAt(i)) != -1) {
                        score += 1;
                        break;
                    }
                }
            } else if (!password.equals(password.toUpperCase())) {
                for (int i = 0; i < password.length(); i++) {
                    if (lowers.indexOf(password.charAt(i)) != -1) {
                        score += 1;
                        break;
                    }
                }
            }

            if (password.matches(".*[0-9].*")) {
                score += 1;
            }

            if (password.matches(".*[!@#$%^&*()-_=+\\/~?].*")) {
                score += 1;
            }

            return score;
        }
    }

    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+\\/~?";

    public static Password generatePassword(int length, boolean includeUpper, boolean includeLower, boolean includeNum, boolean includeSym) {
        StringBuilder pool = new StringBuilder();

        if (includeUpper) {
            pool.append(UPPERCASE_LETTERS);
        }
        if (includeLower) {
            pool.append(LOWERCASE_LETTERS);
        }
        if (includeNum) {
            pool.append(NUMBERS);
        }
        if (includeSym) {
            pool.append(SYMBOLS);
        }

        String poolString = pool.toString();
        int poolSize = poolString.length();

        StringBuilder generatedPassword = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char randomChar = poolString.charAt(random.nextInt(poolSize));
            generatedPassword.append(randomChar);
        }

        return new Password(generatedPassword.toString());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEnter 1 - Password Generator");
            System.out.println("Enter 2 - Password Strength Check");
            System.out.println("Enter 3 - Useful Information");
            System.out.println("Enter 4 - Quit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    passwordGenerator(scanner);
                    break;
                case 2:
                    passwordStrengthCheck(scanner);
                    break;
                case 3:
                    usefulInfo();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void passwordGenerator(Scanner scanner) {
        System.out.println();
        System.out.println("Hello, welcome to the Password Generator :) answer the following questions by Yes or No \n");
        boolean includeUpper = getYesNoResponse(scanner, "Do you want Uppercase letters \"ABCD...\" to be used? ");
        boolean includeLower = getYesNoResponse(scanner, "Do you want Lowercase letters \"abcd...\" to be used? ");
        boolean includeNum = getYesNoResponse(scanner, "Do you want Numbers \"1234...\" to be used? ");
        boolean includeSym = getYesNoResponse(scanner, "Do you want Symbols \"!@#$...\" to be used? ");

        if (!includeUpper && !includeLower && !includeNum && !includeSym) {
            System.out.println("You have selected no characters to generate your password. At least one of your answers should be Yes");
            return;
        }

        System.out.println("Great! Now enter the length of the password");
        int length = scanner.nextInt();
        Password password = generatePassword(length, includeUpper, includeLower, includeNum, includeSym);
        System.out.println("Generated Password: " + password.getPassword());
    }

    private static boolean getYesNoResponse(Scanner scanner, String question) {
        System.out.print(question);
        String input = scanner.next();
        scanner.nextLine(); // Consume newline character
        return input.equalsIgnoreCase("yes");
    }

    private static void passwordStrengthCheck(Scanner scanner) {
        System.out.println();
        System.out.print("Enter your password: ");
        String input = scanner.nextLine();
        Password password = new Password(input);
        System.out.println("Strength: " + password.calculateScore());
    }

    private static void usefulInfo() {
        System.out.println();
        System.out.println("1-Use a minimum password length of 8 or more characters if permitted");
        System.out.println("2-Include lowercase and uppercase alphabetic characters, numbers and symbols if permitted");
        System.out.println("3-Generate passwords randomly where feasible");
        System.out.println("4-Avoid using the same password twice (e.g., across multiple user accounts and/or software systems)");
        System.out.println("5-Avoid character repetition, keyboard patterns, dictionary words, letter or number sequences,\nusernames, relative or pet names, romantic links (current or past) and biographical information (e.g., ID numbers, ancestors' names or dates).");
        System.out.println("6-Avoid using information that the user's colleagues and/or acquaintances might know to be associated with the user");
        System.out.println("7-Do not use passwords which consist wholly of any simple combination of the aforementioned weak components");
    }
}
