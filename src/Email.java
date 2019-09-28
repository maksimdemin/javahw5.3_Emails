import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private static final String REGEX_EMAIL = "^([A-z0-9_\\.-]+@[0-9a-z\\.-]+\\.[a-z]{2,6})$"; // создаем константу для проверки введенных email
    private static final String REGEX_HELP = "HELP"; // создаем константу для проверки команды HELP
    private static final String REGEX_LIST = "LIST"; // создаем константу для проверки команды LIST
    private static final String REGEX_CLEAR = "CLEAR"; // создаем константу для проверки команды CLEAR
    private static final String REGEX_ADD_EMAIL = "^(ADD)\\s*(.+)$"; // создаем константу для проверки регулярным выражением команды ADD "email"
    private static final String REGEX_ADD_MAIL_REPLACEALL = "(ADD)\\s*"; // создаем константу для вырезания нового email из введенной строки для команды ADD "email"
    private static final String REGEX_DELETE_MAIL = "^(DELETE)\\s*(.*)$"; // создаем константу для проверки регулярным выражением команды DELETE email
    private static final String REGEX_DELETE_MAIL_REPLACEALL = "(DELETE)\\s*"; // создаем константу для вырезания email из введенной строки для команды DELETE "email"

    private static TreeSet<String> myEmails = new TreeSet<>(); // создаем список уникальных элементов (электронных адресов)


    // метод, который загружает информацию при загрузке программы
    public static void welcomeHelpList() {
        System.out.println("\nYou are Wellcome! Your Email-list is empty.\n" +
                "To work with the list, please see the help section, the HELP command. " +
                "\nAvailable commands:\n" +
                "  command <LIST>           -> List of all my emails.\n" +
                "  command <CLEAR>          -> Delete all emails from list.\n" +
                "  command <ADD \"email\">    -> Add a new email to the the list." +
                " Only latin letters, numbers, underscore (\"_\"), point (\".\"), minus (\"-\") are allowed.\n" +
                "  command <DELETE N>       -> Delete email.\n" +
                "  command <EXIT>           -> STOP program.\n" +
                "All commands enter without < > and email without \" \"! For example:  ADD hello@skillbox.ru");
    }

    // метод проверки команд введеных из консоли
    public static void checkCommand(String command) {

        if (command.matches(REGEX_HELP)) {
            printHelp();
        } else if (command.matches(REGEX_LIST)) {
            printList();
        } else if (command.equals(REGEX_CLEAR)) {
            clearList();
        } else if (command.matches(REGEX_ADD_EMAIL)) {
            addEmail(command);
            //printList();
        } else if (command.matches(REGEX_DELETE_MAIL)) {
            deleteEmail(command);
        } else
            System.out.println("\'" + command + "\'" + " invalid command. Please, try again or see HELP (command HELP).");
    }


    private static void clearList() { // метод для удаления всего списка дел. Если надо начать весь список сначала.
        if (!myEmails.isEmpty()) {
            myEmails.clear();
            System.out.println("Your list is empty. You can create your list again.");
        } else
            System.out.println("Cannot delete an empty list. Please, create your list.");
    }

    private static void printHelp() { // метод для вывода в консоль информации о команде HELP
        System.out.println("List of available commands:\n" +
                "To work with the list, please see the help section, the HELP command. " +
                "\nAvailable commands:\n" +
                "  command <LIST>           -> List of all my emails.\n" +
                "  command <CLEAR>          -> Delete all emails from list.\n" +
                "  command <ADD \"email\">    -> Add a new email to the the list." +
                " Only latin letters, numbers, underscore (\"_\"), point (\".\"), minus (\"-\") are allowed.\n" +
                "  command <DELETE email>   -> Delete email.\n" +
                "  command <EXIT>           -> STOP program.\n" +
                "All commands enter without < > and email without \" \"! For example:  ADD hello@skillbox.ru");
    }

    private static void printListToConsole() { // метод для печати списка в консоль

        for (String mail : myEmails) {
            System.out.println("- " + mail);
        }
    }


    private static void printList() { // метод для печати полного списка дел
        if (myEmails.isEmpty()) {
            System.out.println("List emails is empty. Create new list emails.");
        } else
            System.out.println("Your list emails:");
        printListToConsole();
    }

    static void printListForExit() { // метод для печати полного списка дел при вводе команды EXIT (нужен когда список пуст)
        if (myEmails.isEmpty()) {
            System.out.println("Your list is empty.");
        } else
            System.out.println("Your list emails:");
        printListToConsole();
    }

    private static void addEmail(String command) { //метод для добавления нового email в список

        if (myEmails.contains(command.replaceAll(REGEX_ADD_MAIL_REPLACEALL, ""))) { // перед добавлением проверяем не является ли введенный email уже в списке
            System.out.println("This email already exists. Enter another email.");
        } else {
            Pattern pattern = Pattern.compile(REGEX_EMAIL); // создаем Pattern для задания как проверять строку
            Matcher matcher = pattern.matcher(command.replaceAll(REGEX_ADD_MAIL_REPLACEALL, "")); // создаем Matcher для поиска в вырезанной строке email по правилу, который заданв Pattern
            if (matcher.find()) {
                myEmails.add(command.replaceAll(REGEX_ADD_MAIL_REPLACEALL, ""));
                System.out.println("Email added successfully.");
            } else if (command.matches("^ADD\\s*$")) {
                System.out.println("You are trying to enter an email that has an empty name.");
            } else
                System.out.println("Invalid email. Try again. Only latin letters, numbers, underscore (\"_\"), point (\".\"), minus (\"-\") are allowed.");
        }
    }

    private static void deleteEmail(String command) { // метод для удаления email из списка
        if (myEmails.isEmpty()) {
            System.out.println("Sorry! List is empty. Create new list emails.");
        } else if (myEmails.contains(command.replaceAll(REGEX_DELETE_MAIL_REPLACEALL, ""))) { // проверяем имеется ли введенный email в списке
            myEmails.remove(command.replaceAll(REGEX_DELETE_MAIL_REPLACEALL, ""));

            printList();

        } else if (!myEmails.contains(command.replaceAll(REGEX_DELETE_MAIL_REPLACEALL, ""))) { // проверка на удаление несуществующего email
            System.out.println("\nYou are trying to delete a nonexistent email.");
        }
    }
}


