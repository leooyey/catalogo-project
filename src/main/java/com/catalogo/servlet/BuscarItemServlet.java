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

//mapeamento é "/buscar", definido tbm no .jsp
@WebServlet("/buscar")
public class BuscarItemServlet extends HttpServlet {

    private ItemMidiaDAO itemMidiaDAO;

    @Override
    public void init() throws ServletException {
        this.itemMidiaDAO = new ItemMidiaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //aqui ele vai pegar o termo digitado na busca
        String termo = request.getParameter("termo");
        List<ItemMidia> itens;

        try {
            if (termo == null || termo.trim().isEmpty()) {
                //se nao foi digitado nada no termo, ele mostra a listagem completa
                response.sendRedirect("listagem");
                return;
            } else {
                //se nao, vai executar o metodo pra buscar pelo termo digitado
                itens = itemMidiaDAO.buscar(termo);
            }

            request.setAttribute("itens", itens);
            request.setAttribute("termoBusca", termo);

            request.getRequestDispatcher("/listagem.jsp").forward(request, response);

        //tratamento de exceções
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensagemErro", "Erro de SQL ao realizar a busca: " + e.getMessage());
            request.getRequestDispatcher("/listagem.jsp").forward(request, response);
        }
    }
}