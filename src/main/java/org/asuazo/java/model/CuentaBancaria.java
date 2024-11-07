package org.asuazo.java.model;

public class CuentaBancaria {

    private String bankAccountId;
    private Double balance;
    private String accountType;

    public CuentaBancaria(String bankAccountId, Double balance, String accountType) {
        this.bankAccountId = bankAccountId;
        this.balance = balance;
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return  bankAccountId +
                " | " +
                balance +
                " | " +
                accountType ;
    }

    public CuentaBancaria() {
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
