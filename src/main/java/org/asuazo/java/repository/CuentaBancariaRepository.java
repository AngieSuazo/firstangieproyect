package org.asuazo.java.repository;

import org.asuazo.java.model.ClienteRegister;
import org.asuazo.java.model.CuentaBancaria;
import org.asuazo.java.util.DataConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaBancariaRepository implements Repository<CuentaBancaria>{

    private Connection getConnection() throws SQLException {
        return DataConnection.getInstance();
    }

    @Override

        public List<CuentaBancaria> list() {
            List<CuentaBancaria> cuentas =new ArrayList<>();

            try (Statement statement = getConnection().createStatement();
                 ResultSet resultSet=statement.executeQuery("SELECT * FROM cuentabancaria" ))
            {
                while (resultSet.next()){
                    CuentaBancaria cuenta = createAccount(resultSet);
                    cuentas.add(cuenta);


                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return cuentas;
    }

    @Override
    public CuentaBancaria byId(String dni) {
        CuentaBancaria account =null;
        try (PreparedStatement statement =getConnection().
                prepareStatement("SELECT * FROM cuentabancaria as c WHERE c.numeroCuenta=?"))
        {
            statement.setString(1,dni);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    account = createAccount(resultSet);

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public void save(CuentaBancaria cuentaBancaria) {
        String sql;
        boolean isUpdate = cuentaBancaria.getBankAccountId() != null;

        // Definir la consulta SQL de acuerdo a si es un UPDATE o INSERT
        if (isUpdate) {
            sql = "INSERT INTO cuentabancaria (numeroCuenta, saldo , tipoCuenta) VALUES (?, ?, ?)";
        } else {
            sql = "UPDATE clientes SET saldo = ?, tipoCuenta= ? WHERE numeroCuenta = ?";
        }

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {

            // Asignar valores en el PreparedStatement según el tipo de operación
            if (isUpdate) {
                statement.setString(1, cuentaBancaria.getBankAccountId());
                statement.setDouble(2, cuentaBancaria.getBalance());
                statement.setString(3, cuentaBancaria.getAccountType());

            } else {
                statement.setDouble(1, cuentaBancaria.getBalance());
                statement.setString(2, cuentaBancaria.getAccountType());
                statement.setString(3, cuentaBancaria.getBankAccountId());
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();

        }
    }

    private CuentaBancaria createAccount(ResultSet resultSet) throws SQLException {

        CuentaBancaria account = new CuentaBancaria();

        account.setBankAccountId(resultSet.getString("numeroCuenta"));
        double balance = resultSet.getDouble("saldo");
        String accountType = resultSet.getString("tipoCuenta");

        if ("ahorros".equalsIgnoreCase(accountType) && balance < 0) {
            throw new IllegalArgumentException("Las cuentas de ahorro no pueden tener un saldo negativo.");
        } else if ("corriente".equalsIgnoreCase(accountType) && balance < -500.00) {
            throw new IllegalArgumentException("Las cuentas corrientes no pueden tener un saldo menor a -500.00.");
        }


        account.setBalance(balance);
        account.setAccountType(accountType);

        return account;

    }



    }



