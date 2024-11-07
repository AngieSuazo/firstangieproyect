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
                       "inner join cuentabancaria as b ON (c.cuenta_dni=b.numeroCuenta) WHERE dni = ?"))
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

       //HASTA AQUÍ GUARDA OK

        String sql = "INSERT INTO  clientes(dni,nombre,apellido,email,cuenta_dni) VALUES (?,?,?,?,?)";
        try ( PreparedStatement statement =getConnection().prepareStatement(sql)){
            statement.setString(1, clienteRegister.getDni());
            statement.setString(2, clienteRegister.getName());
            statement.setString(3, clienteRegister.getLastname());
            statement.setString(4, clienteRegister.getEmail());
            statement.setString(5,clienteRegister.getCuenta_dni().getBankAccountId());


            statement.executeUpdate();



//        String sql;
//        if (clienteRegister.getDni().  ) {
//            sql= "UPDATE clientes SET email=? WHERE dni=?, nombre=?, apellido=?";
//
//        } else {
//            sql = "INSERT INTO  clientes(dni,nombre,apellido,email) VALUES (?,?,?,?)";
//        }
//        try( PreparedStatement statement =getConnection().prepareStatement(sql)){
//
//
//            statement.setString(1, clienteRegister.getDni());
//            statement.setString(2, clienteRegister.getName());
//            statement.setString(3, clienteRegister.getLastname());
//           // statement.setString(4, clienteRegister.getEmail());
//
//            if (clienteRegister.getDni() != null && clienteRegister.getName() != null  &&
//                    clienteRegister.getLastname() != null ){
//
//                statement.setString(4, clienteRegister.getEmail());
//            }else {
//
//                statement.setString(4, new String(clienteRegister.getEmail()));
//            }
//
//
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
