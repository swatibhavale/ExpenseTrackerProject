package expense_tracker;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@FunctionalInterface
interface AuthFun {
	boolean authenticate(String username, String Password);
}

public class Main {
	public boolean validate(String username, String password) {
		AuthFun authFun = (u, p) -> u.equals("admin") && p.equals("admin123");
		return authFun.authenticate(username, password);
	}

	public static void main(String[] args) {
		try {
			ExpenseTracker expenseTracker = new ExpenseTracker();
			Main m1 = new Main();
			Scanner scanner = new Scanner(System.in);

			System.out.println("\nYou are not Login. Please Login First.\n");
			System.out.print("User Name: ");
			String username = scanner.nextLine();

			System.out.print("Password : ");

			String password = scanner.nextLine();

			boolean result = m1.validate(username, password);
			if (result == true) {
				System.out.println("\nLogin successfully....");
				System.out.println(
						"\n**********************************************************************************");
				System.out.println("\n                     WELCOME TO EXPENSE TRACKER                    ");
				System.out.println(
						"\n**********************************************************************************");
				System.out.println("Please Select the Options that u want to preform from below list.\n");
				while (true) {

					System.out.println("\n1. Add your Expense");
					System.out.println("2. View your Expenses");
					System.out.println("3. Delete your Expense");

					System.out.println("4. Search Expenses by category");
					System.out.println("5. View Summary");
					System.out.println("6. View Expense Report(Weekly,Monthly,Yearly)");
					System.out.println("7. View History of your Expenses");
					System.out.println("8. Logout\n");

					System.out.print("Enter your choice: ");
					int choice = scanner.nextInt();

					switch (choice) {
					case 1:
						System.out.println("Enter expense category: ");
						String category;
						category = scanner.nextLine();
						category = scanner.nextLine();

						System.out.println("Enter expense description: ");

						String description = scanner.nextLine();

						System.out.print("Enter expense amount: Rs.");
						double amount = scanner.nextDouble();
						System.out.println("Enter expense date (dd-MM-yyyy): ");
						scanner.nextLine();
						String dateStr = scanner.nextLine();
						LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
						expenseTracker.addExpense(description, amount, category, date);
						break;
					case 2:
						expenseTracker.viewExpenses();
						break;
					case 3:
						System.out.print("Enter the Serial Number of the expense to delete: ");
						int indexToDelete = scanner.nextInt();
						expenseTracker.deleteExpense(indexToDelete);
						break;

					case 4:
						System.out.print("Enter the category : ");
						String cate = scanner.nextLine();
						while (scanner.hasNextLine()) {
							cate = scanner.nextLine();
							expenseTracker.searchExpense(cate);
							break;
						}
						break;

					case 5:
						expenseTracker.viewSummary();
						break;
					case 6:
						System.out.print("Enter start date (dd-MM-yyyy): ");
						scanner.nextLine();
						String startDateStr = scanner.nextLine();
						LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

						System.out.print("Enter end date (dd-MM-yyyy): ");
						String endDateStr = scanner.nextLine();
						LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

						expenseTracker.generateExpenseReport(startDate, endDate);
						break;
					case 7:
						expenseTracker.viewHistory();
						break;
					case 8:

						System.out.println("Logout SuccessFully... Visit Again!");
						System.exit(0);
						break;
					default:
						System.out
								.println("Invalid Option. Please choose the options according to list and Try again.");
					}
				}
			} else {
				System.out.println("Invalid username and Password. Please Login again!");
			}
		} catch (Exception e) {
			System.out.println("An unexpected error occurred. Exiting the application.");
		}

	}

}
