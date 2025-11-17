package com.catalogo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por fornecer as conexões com o banco de dados
 *
 * @author leooyey
 * @version 1.0
 * @since 2025-11-17
 */
public class FabricaDeConexoes {

    // banco pra conectar: 'catalogo_db'
    // SGBD: MySQL
    // verificar o user e senha, se está batendo com o banco
    private static final String URL = "jdbc:mysql://localhost:3306/catalogo_db";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    /**
     * Retorna a conexão com o banco de dados especificado
     *
     * @return Um objeto com a conexão.
     * @throws SQLException Caso falhe na conexão por algum motivo, indicando o motivo da falha.
     */
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