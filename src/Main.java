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

                System.out.println("\n1.Add Branch  2.Add Book  3.Add User");
                System.out.println("4.Search  5.Checkout  6.Return");
                System.out.println("7.Transfer  8.Recommend  9.Exit");

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
                        System.exit(0);


                    default:
                        System.out.println("Invalid choice!");
                }
            }

            catch (Exception e) {
                System.out.println("Invalid input! Please enter correct data type.");
                sc.nextLine();
            }
        }
    }
}