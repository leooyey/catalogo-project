package com.catalogo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaDeConexoes {

    // banco pra conectar: 'catalogo_db'
    // SGBD: MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/catalogo_db";

    // user do banco padrão criado foi 'root'
    private static final String USER = "root";

    // senha quando instalei o MySQL foi 'admin'
    private static final String PASSWORD = "admin";

    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar o driver JDBC do MySQL.");
            throw new SQLException("Driver JDBC não encontrado: " + e.getMessage());
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}