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

/**
 * Servlet responsável por processar a requisição que irá buscar os itens cadastrados conforme o termo digitado na barra de pesquisa da listagem
 *
 * Mapeado para a URL: /buscar
 * (Context Path + /buscar -> ex: /catalogo/buscar)
 *
 * @author leooyey
 * @version 1.0
 * @since 2025-11-17
 */
@WebServlet("/buscar")
public class BuscarItemServlet extends HttpServlet {

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
     * Processa requisições com método GET
     * Usa a DAO para listar todos os itens cadastrados que estejam contamplados pelo termo digitado
     *
     * @param request objeto HttpServletRequest
     * @param response objeto HttpServletResponse
     * @throws ServletException se algum erro do servlet ocorrer
     * @throws IOException se algum erro de entrada/saída ocorrer
     */
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