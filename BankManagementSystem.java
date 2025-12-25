// ================= IMPORT STATEMENTS =================
// File handling & object serialization
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// Collection framework
import java.util.HashMap;

// User input
import java.util.Scanner;

// ================= CUSTOM EXCEPTION =================
/*
 * Custom exception used when withdrawal amount
 * is greater than available balance
 */
class InsufficientBalanceException extends Exception {
    InsufficientBalanceException(String message) {
        super(message);
    }
}

// ================= ACCOUNT CLASS =================
/*
 * Account class represents a bank account.
 * Implements Serializable so that Account objects
 * can be saved into a file.
 */
class Account implements Serializable {

    // Account details
    private int accountNumber;
    private String holderName;
    private double balance;

    // Constructor
    Account(int accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    // Getter methods
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit money into account
    public void deposit(int amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    // Withdraw money from account
    public void withdraw(int amount) throws InsufficientBalanceException {
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance!");
        }
        balance -= amount;
    }

    // Display account details
    public void display() {
        System.out.println("\n--- Account Details ---");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Holder Name    : " + holderName);
        System.out.println("Balance        : " + balance);
    }
}

// ================= BANK CLASS =================
/*
 * Bank class handles all banking operations.
 * Uses HashMap to store accountNumber -> Account mapping.
 * Data is stored and retrieved using file handling.
 */
class Bank {

    // Stores all accounts (Key = Account Number, Value = Account object)
    private HashMap<Integer, Account> account = new HashMap<>();

    // File name where account data is stored
    private static final String FILE_NAME = "account.txt";

    // Constructor loads data from file
    public Bank() {
        loadAccounts();
    }

    // Create new account
    public void createAccount(int accNo, String name, double balance) {
        if (account.containsKey(accNo)) {
            System.out.println("Account already exists!");
            return;
        }
        account.put(accNo, new Account(accNo, name, balance));
        saveAccounts();
        System.out.println("Account Created Successfully!");
    }

    // Deposit money
    public void deposit(int accNo, int amount) {
        Account acc = account.get(accNo);
        if (acc != null) {
            acc.deposit(amount);
            saveAccounts();
            System.out.println("Amount Deposited Successfully!");
        } else {
            System.out.println("Account Not Found!");
        }
    }

    // Withdraw money
    public void withdraw(int accNo, int amount) {
        Account acc = account.get(accNo);
        if (acc != null) {
            try {
                acc.withdraw(amount);
                saveAccounts();
                System.out.println("Withdrawal Successful!");
            } catch (InsufficientBalanceException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Account Not Found!");
        }
    }

    // Display account details
    public void displayAccount(int accNo) {
        Account acc = account.get(accNo);
        if (acc != null) {
            acc.display();
        } else {
            System.out.println("Account Not Found!");
        }
    }

    // ================= FILE HANDLING =================

    // Save account data to file (Serialization)
    private void saveAccounts() {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(account);
        } catch (IOException e) {
            System.out.println("Error saving data!");
        }
    }

    // Load account data from file (Deserialization)
    @SuppressWarnings("unchecked")
    private void loadAccounts() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            account = (HashMap<Integer, Account>) in.readObject();
        } catch (Exception e) {
            // If file not found or error occurs, create empty HashMap
            account = new HashMap<>();
        }
    }
}

// ================= MAIN CLASS =================
/*
 * BankManagementSystem
 * Menu-driven console application for bank operations
 */
public class BankManagementSystem {

    public static void main(String[] args) {

        Bank bank = new Bank();
        Scanner sc = new Scanner(System.in);

        while (true) {
            // Menu
            System.out.println("\n------ * Bank Management System * ------");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Account Details");
            System.out.println("5. Exit");
            System.out.print("Choose Option: ");

            int choice;

            // Input validation for menu choice
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                sc.next(); // clear invalid input
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.print("Enter Account Number: ");
                    int accNo = sc.nextInt();
                    sc.nextLine(); // consume newline

                    System.out.print("Enter Holder Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Initial Balance: ");
                    double bal = sc.nextDouble();

                    bank.createAccount(accNo, name, bal);
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    accNo = sc.nextInt();

                    System.out.print("Enter Amount: ");
                    int depositAmt = sc.nextInt();

                    bank.deposit(accNo, depositAmt);
                    break;

                case 3:
                    System.out.print("Enter Account Number: ");
                    accNo = sc.nextInt();

                    System.out.print("Enter Amount: ");
                    int withdrawAmt = sc.nextInt();

                    bank.withdraw(accNo, withdrawAmt);
                    break;

                case 4:
                    System.out.print("Enter Account Number: ");
                    accNo = sc.nextInt();

                    bank.displayAccount(accNo);
                    break;

                case 5:
                    System.out.println("Thank you for using Bank Management System!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
