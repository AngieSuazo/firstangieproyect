package org.asuazo.java;

import org.asuazo.java.model.ClienteRegister;
import org.asuazo.java.model.CuentaBancaria;
import org.asuazo.java.repository.ClienteRegisterRepository;
import org.asuazo.java.repository.CuentaBancariaRepository;
import org.asuazo.java.repository.Repository;
import org.asuazo.java.util.DataConnection;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


         try (Connection conn= DataConnection.getInstance()){

             Repository<ClienteRegister> repository =new ClienteRegisterRepository();
             Repository<CuentaBancaria> repositoryB =new CuentaBancariaRepository();
//             System.out.println("----------------LISTAR----------------");
//             repository.list().forEach(System.out::println);




             Scanner sn = new Scanner(System.in);
             boolean salir = false;
             int opcion;
             String leer=null;

             System.out.println("¡BIENVENIDO A BANCO NTT DATA!");
             while (!salir) {

                 System.out.println("1. Registrarse Cliente NTT Data ");
                 System.out.println("2. Abrir Cuenta Bancaria");
                 System.out.println("3. Depositar Dinero");
                 System.out.println("4. Retirar Dinero");
                 System.out.println("5. Consultar Saldo");
                 System.out.println("6. Salir");

                 try {

                     System.out.println("Escribe una de las opciones");
                     opcion = sn.nextInt();

                     switch (opcion) {
                         case 1:
                             System.out.println("Has seleccionado la opcion 1--------Registrarse Cliente NTT Data--------");

                             System.out.println("-------REGISTRAR NUEVO CLIENTE--------");
                             ClienteRegister client = new ClienteRegister();
                             System.out.println("Escriba número de DNI");
                             client.setDni(sn.next());
                             System.out.println("Escriba nombre");
                             client.setName(sn.next());
                             System.out.println("Escriba apellido");
                             client.setLastname(sn.next());
                             System.out.println("Escriba email");
                             client.setEmail(sn.next());
//
                             System.out.println("-------REGISTRAR NUEVA CUENTA BANCARIA--------");
                             CuentaBancaria account =new CuentaBancaria();
                             System.out.println("Escriba número de ID");
                             account.setBankAccountId(sn.next());
                             System.out.println("Escriba monto");
                             account.setBalance(sn.nextDouble());
                             System.out.println("Escriba tipo de cuenta");
                             account.setAccountType(sn.next());
                             repositoryB.save(account);
                             repositoryB.list().forEach(System.out::println);
                             client.setCuenta_dni(account);
                             repository.save(client);
                             System.out.println("Cliente guardado con éxito");
                             repository.list().forEach(System.out::println);


                             break;
                         case 2:
                             System.out.println("Has seleccionado la opcion 2---------Abrir Cuenta Bancaria--------------");
                             System.out.println("-------REGISTRAR NUEVA CUENTA BANCARIA--------");
                             ClienteRegister clientB = new ClienteRegister();
                             CuentaBancaria accountB =new CuentaBancaria();
                             System.out.println("Escriba número de ID");
                             accountB.setBankAccountId(sn.next());
                             System.out.println("Escriba monto");
                             accountB.setBalance(sn.nextDouble());
                             System.out.println("Escriba tipo de cuenta");
                             accountB.setAccountType(sn.next());
                             repositoryB.save(accountB);
                             repositoryB.list().forEach(System.out::println);
                             clientB.setCuenta_dni(accountB);
                             repository.save(clientB);
                             System.out.println("Cliente guardado con éxito");
                             repository.list().forEach(System.out::println);


                             break;
                         case 3:
                             System.out.println("Has seleccionado la opcion 3------------Depositar Dinero-----------------");
                             break;
                         case 4:
                             System.out.println("Has seleccionado la opcion 4------------Retirar Dinero-------------------");
                             break;
                         case 5:
                             System.out.println("Has seleccionado la opcion 5------------Consultar Saldo------------------");
                             System.out.println("Escriba número de DNI");
                             System.out.println(repository.byId(sn.next()));

                             break;

                         case 6:
                             salir = true;
                             break;
                         default:
                             System.out.println("Solo números entre 1 y 5");
                     }
                 } catch (InputMismatchException e) {
                     System.out.println("Debes insertar un número");
                     sn.next();
                 }
             }




         }catch (SQLException e){
            e.printStackTrace();
         }







    }
}