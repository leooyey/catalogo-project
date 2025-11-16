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

@WebServlet("/detalhes")
public class DetalhesItemServlet extends HttpServlet {

    private ItemMidiaDAO itemMidiaDAO;

    @Override
    public void init() throws ServletException {
        this.itemMidiaDAO = new ItemMidiaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            ItemMidia item = itemMidiaDAO.buscarPorId(id);

            if (item == null) {
                response.sendRedirect("listagem?erro=ItemNaoEncontrado");
                return;
            }

            request.setAttribute("item", item);
            request.getRequestDispatcher("/detalhes.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("listagem?erro=IdInvalido");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erroGeral", "Erro de SQL ao buscar detalhes do item: " + e.getMessage());
            request.getRequestDispatcher("/listagem.jsp").forward(request, response);
        }
    }
}