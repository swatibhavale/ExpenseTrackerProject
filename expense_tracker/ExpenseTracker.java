package expense_tracker;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ExpenseTracker {
	private List<Expense> expenses;
	private Map<String, Double> categoryExpenses;
	private List<String> addedHistory;
	private List<String> deletedHistory;

	public ExpenseTracker() {
		this.expenses = new ArrayList<>();
		this.categoryExpenses = new HashMap<>();
		this.addedHistory = new ArrayList<>();
		this.deletedHistory = new ArrayList<>();
	}

	public void addExpense(String description, double amount, String category, LocalDate date) {
		try {
			Expense expense = new Expense(description, amount, category, date);
			expenses.add(expense);

			// Update category-wise expenses
			categoryExpenses.put(category, categoryExpenses.getOrDefault(category, 0.0) + amount);

			// Add expense into added history
			String addedHistoryExpense = String.format("| %-20s | %-25s | Rs.%-15.2f | %-10s ", expense.getCategory(),
					expense.getDescription(), expense.getAmount(), expense.getDate());

			addedHistory.add(addedHistoryExpense);

			System.out.println("Expense added successfully...\n");
		} catch (Exception e) {
			System.out.println("An unexpected error occured. Please try again");
		}
	}

	// delete expense by index
	public void deleteExpense(int index) {
		if (index >= 0 && index < expenses.size()) {
			Expense deletedExpense = expenses.remove(index);
			categoryExpenses.merge(deletedExpense.getCategory(), -deletedExpense.getAmount(), Double::sum);

			// add expense into delete history
			String deletedHistoryExpense = String.format("| %-20s | %-25s | Rs.%-15.2f | %-10s ",
					deletedExpense.getCategory(), deletedExpense.getDescription(), deletedExpense.getAmount(),
					deletedExpense.getDate());

			deletedHistory.add(deletedHistoryExpense);
			addedHistory.removeIf(entry -> entry.contains(deletedExpense.getDescription()));
			System.out.println("Expense deleted successfully.");
		} else {
			System.out.println("Invalid Serial number. Please try again.");
		}
	}

//view Expense details
	public void viewExpenses() {
		if (expenses.isEmpty()) {
			System.out.println("No expenses to display.\n");
		} else {
			System.out.println(
					"------------------------------- Expense Details ------------------------------------------\n");

			printTableHeader();

			int count = 0;
			for (Expense ex : expenses) {
				System.out.printf("%-10s | %-20s | %-25s | Rs.%-12s | %-10s%n", count, ex.getCategory(),
						ex.getDescription(), ex.getAmount(),
						ex.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				count++;
			}
		}
	}

//view expense summary
	public void viewSummary() {
		if (expenses.isEmpty()) {
			System.out.println("No expenses to summarize.");
		} else {
			System.out
					.println("------------------------------- Expense Summary ------------------------------------\n");

			// Total expenses
			double totalExpenses = expenses.stream().mapToDouble(Expense::getAmount).sum();

			System.out.println("Total Expenses: Rs." + totalExpenses);
			// Category-wise expenses
			System.out.println("\nCategory-wise Expenses:");
			System.out.println("---------------------------------------------------");
			System.out.printf("%-10s | %-20s | %-12s%n", " Sr.No", "Category", "Amount");
			System.out.println("---------------------------------------------------");
			int count = 0;
			for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {

				System.out.printf("%-10s | %-20s | Rs. %-12s%n", count, entry.getKey(), entry.getValue());
				count++;
			}
		}
	}

//Search expense by category
	public void searchExpense(String cate) {
		boolean found = false;
		int count = 0;
		for (Expense search : expenses) {

			if (search.getCategory().equalsIgnoreCase(cate)) {
				if (count == 0) {
					printTableHeader();
				}

				System.out.printf("%-10s | %-20s | %-25s | Rs.%-12s | %-10s%n", count, search.getCategory(),
						search.getDescription(), search.getAmount(),
						search.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

				count++;
				found = true;

			}

		}
		if (!found) {
			System.out.println(cate + " not present. Please enter another category");
		}
	}

//view exense report
	public void generateExpenseReport(LocalDate startDate, LocalDate endDate) {
		System.out
				.println("------------------------------- Expense Report ------------------------------------------\n");
		List<Expense> reportExpenses = new ArrayList<>();
		for (Expense expense : expenses) {
			if (expense.getDate().isAfter(startDate) && expense.getDate().isBefore(endDate)) {
				reportExpenses.add(expense);
			}

		}
		if (reportExpenses.isEmpty()) {
			System.out.println("No expenses in the specified date range.");
		} else {
			int count = 0;
			printTableHeader();
			for (Expense expense : reportExpenses) {
				System.out.printf("%-10s | %-20s | %-25s | %-12s | %-10s%n", count, expense.getCategory(),
						expense.getDescription(), expense.getAmount(),
						expense.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				count++;
			}
		}

	}

	// view history of expense
	public void viewHistory() {
		if (addedHistory.isEmpty() && deletedHistory.isEmpty()) {
			System.out.println("No history to display.");
		} else {
			System.out.println(
					"------------------------------------- History --------------------------------------------");

			System.out.println("Added Expense : ");
			printTableHeader();
			int count1 = 0;
			for (String entry : addedHistory) {
				System.out.println(count1 + "       " + entry);
				count1++;

			}
			if (!deletedHistory.isEmpty()) {
				System.out.println("\nDeleted Expense : ");
				printTableHeader();
				int count2 = 0;
				for (String entry1 : deletedHistory) {
					System.out.println(count2 + "      " + entry1);
					count2++;
				}
			} else {
				System.out.println("\nNo Deleted Expense");
			}
		}
	}

	// print table header
	private void printTableHeader() {
		System.out
				.println("------------------------------------------------------------------------------------------");
		System.out.printf("%-10s | %-20s | %-25s | %-12s | %-10s%n", " Sr.No", "Category", "Description", "Amount",
				"Date");
		System.out
				.println("------------------------------------------------------------------------------------------");
	}

}
