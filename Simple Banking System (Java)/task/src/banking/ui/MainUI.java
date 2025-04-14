package banking.ui;

import banking.data_models.Account;
import banking.manager.BankingSystem;

import java.util.Scanner;

public class MainUI {
    Scanner scanner = new Scanner(System.in);
    BankingSystem bankingSystem;

    public MainUI(String fileName) {
        bankingSystem = new BankingSystem(fileName);
    }

    public void mainMenu() {
        while (true) {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    bankingSystem.createAccount();
                    break;
                case "2":
                    boolean exit = loginMenu();
                    if (exit) {
                        System.out.println("Bye!");
                        bankingSystem.logOut();
                        return;
                    }
                    break;
                case "0":
                    System.out.println("Bye!");
                    bankingSystem.logOut();
                    return;
            }

        }
    }

    public boolean loginMenu() {
        Account account = new Account();
        System.out.println("Enter your card number:");
        String cardNumber = scanner.nextLine();
        account = bankingSystem.getAccount(cardNumber);
        if (account == null) {
            System.out.println("Wrong card number or PIN!");
            return false;
        }
        System.out.println("You have successfully logged in!");
        return balanceMenu(account);

    }

    public boolean balanceMenu(Account account) {
        while (true) {
            System.out.println("1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit");
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    System.out.printf("Balance: %d\n", account.getBalance());
                    break;
                case "2":
                    System.out.println("Enter income:");
                    int amount = scanner.nextInt();
                    scanner.nextLine();
                    bankingSystem.addIncome(account, amount);
                    System.out.println("Income was added!");
                    account.setBalance(account.getBalance() + amount);
                    break;
                case "3":
                    System.out.println("Transfer\n" +
                            "Enter card number:");
                    String receiverAccount = scanner.nextLine();
                    switch (bankingSystem.validateReceiverAccount(account, receiverAccount)) {
                        case SAME_CARD -> System.out.println("You can't transfer money to the same account!");
                        case INVALID_LUHNS -> System.out.println("Probably you made a mistake in the card number. Please try again!");
                        case DOES_NOT_EXIST -> System.out.println("Such a card does not exist.");
                        case VALID -> {
                            int transfer = bankingSystem.transfer(account, receiverAccount);
                            account.setBalance(account.getBalance() - transfer);
                        }

                    }
                    break;
                case "4":
                    bankingSystem.closeAccount(account);
                    System.out.println("The account has been closed!\n");
                    return false;
                case "5":
                    System.out.println("You have successfully logged out!");
                    return false;
                case "0":
                    bankingSystem.logOut();
                    return true;
            }
        }

    }
}
