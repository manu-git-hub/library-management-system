import com.airtribe.java.Repository.*;
import com.airtribe.java.Service.LibraryService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookRepository bRepo = new BookRepository();
        PatronRepository pRepo = new PatronRepository();
        BranchRepository brRepo = new BranchRepository();
        ReservationRepository rRepo = new ReservationRepository();

        LibraryService service =
                new LibraryService(bRepo, pRepo, brRepo, rRepo);

        while (true) {

            try {

                System.out.println("\n" + "=".repeat(46));
                System.out.println("          LIBRARY MANAGEMENT SYSTEM");
                System.out.println("=".repeat(46));
                System.out.println("  [1] Add Branch         [7] Transfer Book");
                System.out.println("  [2] Add Book           [8] Recommend Books");
                System.out.println("  [3] Add User           [9] Update Book");
                System.out.println("  [4] Search Books       [10] Update User");
                System.out.println("  [5] Checkout Book      [11] Remove Book");
                System.out.println("  [6] Return Book        [12] Exit");
                System.out.println("-".repeat(46));
                System.out.print("  Enter choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        System.out.print("Branch ID: ");
                        String bid = sc.nextLine();
                        System.out.print("Name: ");
                        String bname = sc.nextLine();

                        service.addBranch(bid, bname);
                        break;

                    case 2:
                        System.out.print("Branch ID: ");
                        String bId = sc.nextLine();

                        System.out.print("Title: ");
                        String title = sc.nextLine();

                        System.out.print("Author: ");
                        String author = sc.nextLine();

                        System.out.print("ISBN: ");
                        String isbn = sc.nextLine();

                        System.out.print("Year: ");
                        int year = sc.nextInt();

                        System.out.print("Quantity: ");
                        int qty = sc.nextInt();
                        sc.nextLine();

                        service.addBook(bId, title, author, isbn, year, qty);
                        break;

                    case 3:
                        System.out.print("User ID: ");
                        String uid = sc.nextLine();

                        System.out.print("Name: ");
                        String uname = sc.nextLine();

                        service.addPatron(uid, uname);
                        break;

                    case 4:
                        System.out.print("Branch ID: ");
                        String sb = sc.nextLine();

                        System.out.print("Keyword: ");
                        String kw = sc.nextLine();

                        service.search(sb, kw);
                        break;

                    case 5:
                        System.out.print("Branch ID: ");
                        String cb = sc.nextLine();

                        System.out.print("User ID: ");
                        String pu = sc.nextLine();

                        System.out.print("ISBN: ");
                        String ci = sc.nextLine();

                        service.checkout(cb, pu, ci);
                        break;

                    case 6:
                        System.out.print("Branch ID: ");
                        String rb = sc.nextLine();

                        System.out.print("User ID: ");
                        String rpu = sc.nextLine();

                        System.out.print("ISBN: ");
                        String ri = sc.nextLine();

                        service.returnBook(rb, rpu, ri);
                        break;

                    case 7:
                        System.out.print("From Branch: ");
                        String fb = sc.nextLine();

                        System.out.print("To Branch: ");
                        String tb = sc.nextLine();

                        System.out.print("ISBN: ");
                        String ti = sc.nextLine();

                        System.out.print("Quantity: ");
                        int tq = sc.nextInt();
                        sc.nextLine();

                        service.transfer(fb, tb, ti, tq);
                        break;

                    case 8:
                        System.out.print("User ID: ");
                        String rid = sc.nextLine();

                        System.out.print("Branch ID: ");
                        String rbid = sc.nextLine();

                        service.recommend(rid, rbid);
                        break;

                    case 9:
                        System.out.print("Branch ID: ");
                        String ub_bid = sc.nextLine();
                        System.out.print("ISBN: ");
                        String ub_isbn = sc.nextLine();
                        System.out.print("New Title: ");
                        String ub_title = sc.nextLine();
                        System.out.print("New Author: ");
                        String ub_author = sc.nextLine();
                        service.updateBook(ub_bid, ub_isbn, ub_title, ub_author);
                        break;

                    case 10:
                        System.out.print("User ID: ");
                        String up_pid = sc.nextLine();
                        System.out.print("New Name: ");
                        String up_name = sc.nextLine();
                        service.updatePatron(up_pid, up_name);
                        break;

                    case 11:
                        System.out.print("Branch ID: ");
                        String rm_bid = sc.nextLine();
                        System.out.print("ISBN: ");
                        String rm_isbn = sc.nextLine();
                        service.removeBook(rm_bid, rm_isbn);
                        break;

                    case 12:
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

            catch (java.util.InputMismatchException e) {
                System.out.println("\n[!] Invalid input. Please enter the correct data type (e.g. number where requested).");
                sc.nextLine(); 
            }
        }
    }
}