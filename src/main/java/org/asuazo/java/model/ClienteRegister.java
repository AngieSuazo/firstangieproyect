package org.asuazo.java.model;

public class ClienteRegister {

    private  String dni;
    private String name;
    private String lastname;
    private String email;
    private CuentaBancaria cliente_dni;

    public ClienteRegister(String dni, String name, String lastname, String email, CuentaBancaria cliente_dni) {
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.cliente_dni = cliente_dni;
    }

    @Override
    public String toString() {
        return  dni +
                " | " +
                name +
                " | " +
                lastname +
                " | " +
                 email +
                " | " +
                cliente_dni.getBankAccountId() +
                 " | " +
                cliente_dni.getBalance() +
                " | " +
                cliente_dni.getAccountType();
    }

    public ClienteRegister() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CuentaBancaria getBankAccount() {
        return cliente_dni;
    }

    public void setBankAccount(CuentaBancaria cliente_dni) {
        this.cliente_dni = cliente_dni;
    }
}
