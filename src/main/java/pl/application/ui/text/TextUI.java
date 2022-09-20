package pl.application.ui.text;

import pl.application.domain.guest.Guest;
import pl.application.domain.guest.GuestService;
import pl.application.domain.reservation.Reservation;
import pl.application.domain.reservation.ReservationService;
import pl.application.exceptions.OnlyNumberException;
import pl.application.exceptions.PersistenceToFileException;
import pl.application.exceptions.WrongOptionException;
import pl.application.domain.room.Room;
import pl.application.domain.room.RoomService;
import pl.application.util.Properties;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TextUI {
    private final GuestService guestService = new GuestService();
    private final RoomService roomService = new RoomService();
    private final ReservationService reservationService = new ReservationService();

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
            boolean isMale = false;
            if (genderChoice == 1) {
                isMale = true;
            }
            Guest guest = guestService.createNewGuest(firstName, lastName, age, isMale);
            System.out.println("Dodano nowego gościa: " + guest.getInfo());
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
            System.out.println("Stworzono pokój o numerze: " + room.getInfo());
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

    public void showSystemInfo() {

        System.out.println("Witaj w systemie rezerwacji hotelu: " + Properties.HOTEL_NAME);
        System.out.println("Aktualna wersja systemu: " + Properties.SYSTEM_VERSION);
        System.out.println("Wersja deweloperska: " + Properties.IS_DEVELOPER_VERSION);
        System.out.println("\n==========================================\n");
    }

    public void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        try {
            performAction(scanner);
        } catch (WrongOptionException | OnlyNumberException | PersistenceToFileException e) {
            System.out.println("Wystąpił błąd: ");
            System.out.println("Kod błędu: " + e.getCode());
            System.out.println("Komunikat błędu: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Wystąpił błąd: ");
            System.out.println("Nieznany kod błędu");
            System.out.println("Komunikat błędu: " + e.getMessage());
        }
    }

    private void performAction(Scanner scanner) {
        int option = -1;
        while (option != 0) {
            option = getActionFromUser(scanner);
            if (option == 1) {
                readNewGuestData(scanner);
            } else if (option == 2) {
                readNewRoomData(scanner);
            } else if (option == 3) {
                showAllGuests();
            } else if (option == 4) {
                showAllRooms();
            } else if (option == 5) {
                removeGuest(scanner);
            } else if (option == 6) {
                editGuest(scanner);
            } else if (option == 7) {
                removeRoom(scanner);
            } else if (option == 8) {
                editRoom(scanner);
            } else if (option == 9) {
                createReservation(scanner);
            } else if (option == 0) {
                System.out.println("Koniec programu. Zapis danych.");
                this.guestService.saveAll();
                this.roomService.saveAll();
                this.reservationService.saveAll();
            } else {
                throw new WrongOptionException("Wrong option in main menu");
            }
        }
    }

    private void createReservation(Scanner scanner) {
        System.out.println("Od kiedy? (DD.MM.YYYY):");
        String fromAsString = scanner.next();
        LocalDate from = LocalDate.parse(fromAsString,Properties.DATE_FORMATTER);
        System.out.println("Do kiedy? (DD.MM.YYYY):");
        String toAsString = scanner.next();
        LocalDate to = LocalDate.parse(toAsString,Properties.DATE_FORMATTER);
        System.out.println("Podaj id pokoju");
        int roomId = scanner.nextInt();
        System.out.println("Podaj id gościa");
        int guestaId = scanner.nextInt();
        //TODO handle null reservation
        try{
            Reservation reservation = this.reservationService.createNewReservation(from,to,roomId,guestaId);
            if(reservation!=null){
                System.out.println("Udało się utworzyć rezerwację");
            }
        }catch (IllegalArgumentException e){
            System.out.println("Data zakończenia rezerwacji nie może być wcześniejsza niż data jej zakończenia");
        }

    }

    private void editRoom(Scanner scanner) {
        System.out.println("Podaj id pokoju do edycji");
        try {
            int id = scanner.nextInt();
            System.out.println("Podaj numer pokoju");
            int number = scanner.nextInt();
            int[] bedTypes = chooseBedType(scanner);
            roomService.editRoom(id,number, bedTypes);
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Only numbers allowed when editing room");
        }
    }

    private void editGuest(Scanner scanner) {
        System.out.println("Podaj id gościa do edycji");
        try {
            int id = scanner.nextInt();


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
            boolean isMale = false;
            if (genderChoice == 1) {
                isMale = true;
            }
            guestService.editGuest(id, firstName, lastName, age, isMale);

        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when editing guest");
        }
    }

    private void removeGuest(Scanner scanner) {
        System.out.println("Podaj id gościa do usunięcia");
        try {
            int id = scanner.nextInt();
            this.guestService.removeGuest(id);
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when entering id");
        }
    }
    private void removeRoom(Scanner scanner) {
        System.out.println("Podaj id pokoju do usunięcia");
        try {
            int id = scanner.nextInt();
            this.roomService.removeRoom(id);
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when entering id");
        }
    }

    private void showAllGuests() {
        List<Guest> guests = this.guestService.getAllGuests();

        for (Guest guest : guests) {
            System.out.println(guest.getInfo());
        }
    }

    private void showAllRooms() {
        List<Room> rooms = this.roomService.getAllRooms();

        for (Room room : rooms) {
            System.out.println(room.getInfo());
        }
    }

    private int getActionFromUser(Scanner scanner) {
        System.out.println("1. Dodaj nowego gościa");
        System.out.println("2. Dodaj nowy pokój");
        System.out.println("3. Wypisz wszystkich gości");
        System.out.println("4. Wypisz wszystkie pokoje");
        System.out.println("5. Usuń gościa");
        System.out.println("6. Edytuj dane gościa");
        System.out.println("7. Usuń pokój");
        System.out.println("8. Edytuj pokój");
        System.out.println("9. Stwórz rezerwację");
        System.out.println("0. Wyjście z programu. Zapis danych");
        System.out.println("Wybierz opcję: ");

        int actionNumber;

        try {
            actionNumber = scanner.nextInt();
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Only numbers allowed in main menu");
        }
        return actionNumber;
    }
}

