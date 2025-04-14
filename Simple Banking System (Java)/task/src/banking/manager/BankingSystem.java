package banking.manager;

import banking.data_models.Account;
import banking.generators.CardNumberGenerator;
import banking.generators.PinGenerator;
import banking.validator.LuhnAlgorithm;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingSystem {
    private DatabaseManager databaseManager;
    Scanner scanner = new Scanner(System.in);

    public BankingSystem(String url) {
        this.databaseManager = new DatabaseManager(url);
    }
    public void createAccount() {
        String cardNumber = CardNumberGenerator.generateCardNumber();
        String pin = PinGenerator.generatePin();
        Account account = new Account(cardNumber, pin);
        System.out.println("Your card has been created");
        account.printCardInfo();
        this.databaseManager.insertCard(cardNumber, pin);
    }

    public Account getAccount(String cardNumber) {
        Account account = new Account();
        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine();
        account = this.databaseManager.findCard(cardNumber, pin);
        return account;
    }

    public void logOut() {
        this.databaseManager.closeConnection();
    }

    public boolean addIncome(Account account, int income) {
        try {
            this.databaseManager.updateBalance(account.getCardNumber(), income);
        } catch (InputMismatchException e) {
            return false;
        }
        return true;
    }

    public void closeAccount(Account account) {
        this.databaseManager.closeAccount(account.getCardNumber());
    }

    public ReceiverCardStatus validateReceiverAccount(Account account, String receiverCardNumber) {
        if (account.getCardNumber().equals(receiverCardNumber)) {
            return ReceiverCardStatus.SAME_CARD;
        } else if (!LuhnAlgorithm.isValid(receiverCardNumber)) {
            return ReceiverCardStatus.INVALID_LUHNS;
        } else if (this.databaseManager.findCard(receiverCardNumber).equals(null)) {
            return ReceiverCardStatus.DOES_NOT_EXIST;
        } else {
            return ReceiverCardStatus.VALID;
        }
    }

    public int transfer(Account account, String receiverAccount) {
        System.out.println("Enter how much money you want to transfer:");
        int transferAmount = scanner.nextInt();
        scanner.nextLine();
        if (transferAmount > account.getBalance()) {
            System.out.println("Not enough money!");
        } else {
            this.databaseManager.updateBalance(account.getCardNumber(), -transferAmount);
            this.databaseManager.updateBalance(receiverAccount, transferAmount);
            System.out.println("Success!");
        }
        return transferAmount;
    }

    public enum ReceiverCardStatus {
        SAME_CARD,
        INVALID_LUHNS,
        DOES_NOT_EXIST,
        VALID
    }

}
