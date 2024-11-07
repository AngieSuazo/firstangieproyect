package org.asuazo.java.repository;

import com.mysql.cj.xdevapi.Client;
import org.asuazo.java.model.ClienteRegister;
import org.asuazo.java.model.CuentaBancaria;
import org.asuazo.java.util.DataConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.sql.SQLException;

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

               }catch (SQLException throwables){
                   throwables.printStackTrace();

               }

    }



    private ClienteRegister createClient(ResultSet resultSet) throws SQLException {

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

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );


    private static boolean isEmailValid(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }


    private void isDniUnique(SQLException error) throws Exception {
        // Código de error para PRIMARY KEY DUPLICATED
        if ("1062".equals(error.getSQLState())) {

            throw new IllegalArgumentException(error.getMessage());
        }

        System.out.println(error);

        throw new RuntimeException("DNI duplicado");
    }





}
