package pl.application.ui.text;

import pl.application.domain.guest.Guest;
import pl.application.domain.guest.GuestService;
import pl.application.exceptions.OnlyNumberException;
import pl.application.exceptions.WrongOptionException;
import pl.application.domain.room.Room;
import pl.application.domain.room.RoomService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TextUI {
    private GuestService guestService = new GuestService();
    private RoomService roomService = new RoomService();

    private void readNewGuestData(Scanner scanner) {
        System.out.println("Tworzymy nowego gościa");
        try {
            System.out.println("Podaj imię gościa");
            String firstName = scanner.next();
            System.out.println("Podaj nazwisko gościa");
            String lastName = scanner.next();
            System.out.println("Podaj wiek gościa");
            int age = scanner.nextInt();
            System.out.println("Podaj płeć gościa:");
            System.out.println("1. Mężczyzna");
            System.out.println("2. Kobieta");
            int genderChoice = scanner.nextInt();
            if (genderChoice != 1 && genderChoice != 2) {
                throw new WrongOptionException("Wrong option in gender selection");
            }
            Guest guest = guestService.createNewGuest(firstName, lastName, age, genderChoice);
            System.out.println(guest.getInfo());
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Only numbers allowed when choosing guest age");
        }
    }

    private void readNewRoomData(Scanner scanner) {
        System.out.println("Tworzymy nowy pokój");
        try {
            System.out.println("Podaj numer pokoju");
            int number = scanner.nextInt();
            int[] bedTypes = chooseBedType(scanner);
            Room room = roomService.createNewRoom(number, bedTypes);
            System.out.println(room.getInfo());
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Only numbers allowed when creating new room");
        }
    }

    private int[] chooseBedType(Scanner scanner) {
        System.out.println("Podaj ilość lózek w pokoju");
        int bedNumber = scanner.nextInt();
        int[] bedTypes = new int[bedNumber];

        for (int i = 0; i < bedNumber; i++) {
            System.out.println("Podaj typ łóżka:");
            System.out.println("1. Pojedyncze");
            System.out.println("2. Podwójne");
            System.out.println("3. Królewskie");

            int bedTypeOption = scanner.nextInt();

            bedTypes[i] = bedTypeOption;
        }
        return bedTypes;
    }

    public void showSystemInfo(String hotelName, int systemVersion, boolean isDeveloperVersion) {

        System.out.println("Witaj w systemie rezerwacji hotelu: " + hotelName);
        System.out.println("Aktualna wersja systemu: " + systemVersion);
        System.out.println("Wersja deweloperska: " + isDeveloperVersion);
        System.out.println("\n==========================================\n");
    }

    public void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        try {
            performAction(scanner);
        } catch (WrongOptionException | OnlyNumberException e) {
            System.out.println("Wystąpił błąd: ");
            System.out.println("Kod błędu: " + e.getCode());
            System.out.println("Komunikat błędu: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Wystąpił błąd: ");
            System.out.println("Nieznany kod błędu");
            System.out.println("Komunikat błędu: " + e.getMessage());
        } finally {
            System.out.println("Wychodzę z aplikacji");
        }
    }

    private void performAction(Scanner scanner) {
        int option = getActionFromUser(scanner);
        if (option == 1) {
            readNewGuestData(scanner);
        } else if (option == 2) {
            readNewRoomData(scanner);
        } else if (option == 3) {
            System.out.println("Wybrano opcję 3");
        } else {
            throw new WrongOptionException("Wrong option in main menu");
        }
    }

    private int getActionFromUser(Scanner scanner) {
        System.out.println("1. Dodaj nowego gościa");
        System.out.println("2. Dodaj nowy pokój");
        System.out.println("3. Wyszukaj gościa");
        System.out.println("Wybierz opcję: ");

        int actionNumber = 0;

        try {
            actionNumber = scanner.nextInt();
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Only numbers allowed in main menu");
        }
        return actionNumber;
    }
}

