package org.asuazo.java.model;

public class ClienteRegister {

    private  String dni;
    private String name;
    private String lastname;
    private String email;

    public ClienteRegister(String dni, String name, String lastname, String email) {
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    @Override
    public String toString() {
        return  dni +
                " | " +
                name +
                " | " +
                lastname +
                " | " +
                 email;
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
}