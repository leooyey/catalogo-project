package com.catalogo.servlet;

import com.catalogo.dao.ItemMidiaDAO;
import com.catalogo.model.ItemMidia;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/listagem")
public class ListarItensServlet extends HttpServlet {

    private ItemMidiaDAO itemMidiaDAO;

    @Override
    public void init() throws ServletException {
        // Instancia o DAO para uso nos métodos doGet/doPost
        this.itemMidiaDAO = new ItemMidiaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<ItemMidia> listaItens = itemMidiaDAO.listarTodos();

            request.setAttribute("itens", listaItens);

            request.getRequestDispatcher("/listagem.jsp").forward(request, response);

        } catch (SQLException e) {
            System.err.println("Erro de SQL: " + e.getMessage());
            e.printStackTrace();

            request.setAttribute("mensagemErro", "Erro interno ao listar o catálogo. Verifique o banco de dados.");
            request.getRequestDispatcher("/listagem.jsp").forward(request, response);
        }
    }
}