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

/**
 * Servlet responsável por processar a requisição que vai mostrar os detalhes dos itens
 *
 * Mapeado para a URL: /detalhes
 * (Context Path + /detalhes -> ex: /catalogo/detalhes)
 *
 * @author leooyey
 * @version 1.0
 * @since 2025-11-17
 */
@WebServlet("/detalhes")
public class DetalhesItemServlet extends HttpServlet {

    private ItemMidiaDAO itemMidiaDAO;

    /**
     * Inicializa o Servlet e instancia o DAO de ItemMidia.
     * Chamado pelo Tomcat na inicialização.
     */
    @Override
    public void init() throws ServletException {
        this.itemMidiaDAO = new ItemMidiaDAO();
    }

    /**
     * Processa requisições as requisições com método GET
     * Usa a DAO para buscar o item correto pelo ID, para retornar para o user
     *
     * @param request objeto HttpServletRequest
     * @param response objeto HttpServletResponse
     * @throws ServletException se algum erro do servlet ocorrer
     * @throws IOException se algum erro de entrada/saída ocorrer
     */
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