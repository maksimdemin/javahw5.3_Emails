import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Email.welcomeHelpList(); // вначале запускаем информационный метод с разделом справки

        while (true) {

            Scanner scan = new Scanner(System.in);
            System.out.println("\nEnter the command: ");
            String command = scan.nextLine();

            command = command.replaceFirst("^\\s+", ""); // удаляем все пробелы в начале введенной строки
            command = command.replaceAll("\\s+", " "); // ищем все пробелы и заменяем их на один пробел
            command = command.replaceFirst("\\s+$", ""); // удаляем все пробелы в конце введенной строки

            if (!command.matches("EXIT")) {

                Email.checkCommand(command); // вызов метода из класса Email (отсюда начинается проверка введенных команд)
            } else {
                Email.printListForExit(); // запускаем специальный метод для команды EXIT
                System.out.println("Goodbye! See you later.");
                break;
            }
        }
    }
}
