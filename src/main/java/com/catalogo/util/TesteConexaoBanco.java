package com.catalogo.util;

import com.catalogo.dao.FabricaDeConexoes;
import java.sql.Connection;
import java.sql.SQLException;

// classe criada para testar a conexão com o MySQL, banco catalogo.db
public class TesteConexaoBanco {

    public static void main(String[] args) {
        Connection conexao = null;
        try {
            System.out.println("Tentando estabelecer a conexão :)");

            conexao = FabricaDeConexoes.getConexao();

            if (conexao != null) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso.");

                System.out.println("URL: " + conexao.getMetaData().getURL());
                System.out.println("Usuário: " + conexao.getMetaData().getUserName());
            } else {
                System.err.println("Falha! Conexão nula");
            }

        } catch (SQLException e) {
            System.err.println("Erro Fatal de Conexão!");
            System.err.println("Código SQLState: " + e.getSQLState());
            System.err.println("Detalhe: Verifique se o MySQL Server está rodando, se a senha está correta na FabricaDeConexoes.java e se o banco 'catalogo_db' foi criado no DBeaver.");
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                    System.out.println("Conexão fechada.");
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão.");
                }
            }
        }
    }
}