package expense_tracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Expense {
	private String description;
	private double amount;
	private String category;
	private LocalDate date;

	public Expense(String description, double amount, String category, LocalDate date) {
		this.description = description;
		this.amount = amount;
		this.category = category;
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public String getCategory() {
		return category;
	}

	public LocalDate getDate() {
		return date;
	}

	@Override
	public String toString() {
		return category + " " + description + " " + amount + " "
				+ date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
