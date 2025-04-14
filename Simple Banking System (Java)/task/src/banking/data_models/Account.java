package banking.data_models;


public class Account {

    private int id;
    private String cardNumber;
    private String pin;
    private int balance;

    public Account() {
    }

    public Account(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) { this.pin = pin; }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void printCardInfo() {
        System.out.printf("""
                Your card number:
                %s
                Your card PIN:
                %s
                """, this.cardNumber, this.pin);
    }
}
