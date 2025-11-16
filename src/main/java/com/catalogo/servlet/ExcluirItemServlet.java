package com.catalogo.servlet;

import com.catalogo.dao.ItemMidiaDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/excluir")
public class ExcluirItemServlet extends HttpServlet {

    private ItemMidiaDAO itemMidiaDAO;

    @Override
    public void init() throws ServletException {
        this.itemMidiaDAO = new ItemMidiaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String erro = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            itemMidiaDAO.excluir(id);

        } catch (NumberFormatException e) {
            erro = "IDInvalido";
            e.printStackTrace();
        } catch (SQLException e) {
            erro = "ErroSQL";
            e.printStackTrace();
        }

        if (erro != null) {
            response.sendRedirect("listagem?erro=" + erro);
        } else {
            response.sendRedirect("listagem"); // Sucesso
        }
    }
}