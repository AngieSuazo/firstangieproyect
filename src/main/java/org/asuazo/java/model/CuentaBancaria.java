package org.asuazo.java.model;

public class CuentaBancaria {

    private String bankAccountId;
    private Double balance;
    private String accountType;
    private String cliente_dni;

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getClienteDni() {
        return cliente_dni;
    }

    public void setClienteDni(String dni) {
        this.cliente_dni = dni;
    }
}
