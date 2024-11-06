package org.asuazo.java.repository;

import org.asuazo.java.model.ClienteRegister;
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
            ResultSet resultSet=statement.executeQuery("SELECT * FROM clientes"))
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
               prepareStatement("SELECT * FROM clientes WHERE dni = ?"))
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
        if (clienteRegister.getDni() != null  ) {
            sql= "UPDATE clientes SET email=? WHERE dni=?";

        } else {
            sql = "INSERT INTO  clientes(nombre,apellido,email) VALUES (?,?,?)";
        }
        try( PreparedStatement statement =getConnection().prepareStatement(sql)){


            //statement.setString(1, clienteRegister.getName());


            if (clienteRegister.getDni() != null ){
                statement.setString(1, clienteRegister.getDni());
                statement.setString(2, clienteRegister.getLastname());
            }else {
                statement.setString(3, clienteRegister.getEmail());
            }


            statement.executeUpdate();

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
        return client;
    }
}
