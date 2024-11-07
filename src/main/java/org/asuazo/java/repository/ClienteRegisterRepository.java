package org.asuazo.java.repository;

import org.asuazo.java.model.ClienteRegister;
import org.asuazo.java.model.CuentaBancaria;
import org.asuazo.java.util.DataConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ClienteRegisterRepository implements Repository<ClienteRegister>{

   private Connection getConnection() throws SQLException {
       return DataConnection.getInstance();
   }
    @Override
    public List<ClienteRegister> list() {
       List<ClienteRegister> clients =new ArrayList<>();

       try (Statement statement = getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT c.*,b.saldo as saldo ,b.tipoCuenta as tipo FROM `clientes` as c " +
                    "inner join cuentabancaria as b ON (c.cuenta_dni=b.numeroCuenta)" ))
       {
           while (resultSet.next()){
              ClienteRegister client = createClient(resultSet);
              clients.add(client);


           }
       }
       catch (SQLException e) {
           e.printStackTrace();
       }
        return clients;

    }



    @Override
    public ClienteRegister byId(String dni) {
       ClienteRegister client =null;
       try (PreparedStatement statement =getConnection().
               prepareStatement("SELECT c.*,b.saldo as saldo ,b.tipoCuenta as tipo FROM `clientes` as c " +
                       "inner join cuentabancaria as b ON (c.cuenta_dni=b.numeroCuenta) WHERE c.dni = ?"))
       {
           statement.setString(1,dni);
           try (ResultSet resultSet = statement.executeQuery()) {
               if (resultSet.next()) {
                   client = createClient(resultSet);

               }

           }

       } catch (SQLException e) {
           e.printStackTrace();
       }
        return client;
    }

    @Override
    public void save(ClienteRegister clienteRegister) {

        String sql;
        boolean isUpdate = clienteRegister.getDni() != null;

        // Definir la consulta SQL de acuerdo a si es un UPDATE o INSERT
        if (isUpdate) {
            sql = "INSERT INTO clientes (dni, nombre, apellido, email, cuenta_dni) VALUES (?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE clientes SET nombre = ?, apellido = ?, email = ?, cuenta_dni=? WHERE dni = ?";
        }

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {

            // Asignar valores en el PreparedStatement según el tipo de operación
            if (isUpdate) {
                statement.setString(1, clienteRegister.getDni());
                statement.setString(2, clienteRegister.getName());
                statement.setString(3, clienteRegister.getLastname());
                statement.setString(4, clienteRegister.getEmail());
                statement.setString(5, (clienteRegister.getCuenta_dni().getBankAccountId()));
            } else {
                statement.setString(1, clienteRegister.getName());
                statement.setString(2, clienteRegister.getLastname());
                statement.setString(3, clienteRegister.getEmail());
                statement.setString(4, clienteRegister.getCuenta_dni().getBankAccountId());
                statement.setString(5, clienteRegister.getDni());
            }

            // Imprimir mensaje de depuración antes de la ejecución
            System.out.println("Ejecutando statement: " + statement);

            // Ejecutar la actualización
            int rowsAffected = statement.executeUpdate();
            System.out.println("Filas afectadas: " + rowsAffected);



               }catch (SQLException throwables){
                   throwables.printStackTrace();

               }

    }



    @Override
    public void delete(String dni) {

    }

    private ClienteRegister createClient(ResultSet resultSet) throws SQLException {
       //TODO: Revisar validaciones de DNI único y email en formato válido  (usar decorator?) todos los campos son obligatorios
        ClienteRegister client =new ClienteRegister();
        client.setDni(resultSet.getString("dni"));
        client.setName(resultSet.getString("nombre"));
        client.setLastname(resultSet.getString("apellido"));
        client.setEmail(resultSet.getString("email"));

        CuentaBancaria account =new CuentaBancaria();
        account.setBankAccountId(resultSet.getString("cuenta_dni"));
        account.setBalance(resultSet.getDouble("saldo"));
        account.setAccountType(resultSet.getString("tipo"));
        client.setCuenta_dni(account);


        return client;
    }








}
