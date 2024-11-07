package org.asuazo.java;

import org.asuazo.java.model.ClienteRegister;
import org.asuazo.java.model.CuentaBancaria;
import org.asuazo.java.repository.ClienteRegisterRepository;
import org.asuazo.java.repository.Repository;
import org.asuazo.java.util.DataConnection;

import java.sql.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("¡BIENVENIDO A BANCO NTT DATA!");
        System.out.println("Elija la operación que desea realizar: ");
        System.out.println(" 1.Registrarse Cliente NTT Data ");
        System.out.println(" 2.Abrir Cuenta Bancaria ");
        System.out.println(" 3.Depositar Dinero ");
        System.out.println(" 4.Retirar Dinero");
        System.out.println(" 5.Consultar Saldo");




         try (Connection conn= DataConnection.getInstance()){

             Repository<ClienteRegister> repository =new ClienteRegisterRepository();
             System.out.println("----------------LISTAR----------------");
             repository.list().forEach(System.out::println);


             System.out.println("-----------OBTENER POR ID ---------");
             System.out.println(repository.byId("72704321"));

             System.out.println("-------REGISTRAR NUEVO CLIENTE--------");
             ClienteRegister client = new ClienteRegister();
             client.setDni("72704326");
             client.setName("juan");
             client.setLastname("lopez");
             client.setEmail("juan@gmail.com");

             CuentaBancaria account =new CuentaBancaria();
             account.setBankAccountId("11111111111115");
             account.setBalance(3.5);
             account.setAccountType("ahorros");

             client.setCuenta_dni(account);

             repository.save(client);

             System.out.println("Cliente guardado con éxito");
             repository.list().forEach(System.out::println);

             System.out.println("----------------LISTAR----------------");
             repository.list().forEach(System.out::println);



         }catch (SQLException e){
            e.printStackTrace();
         }





    }
}