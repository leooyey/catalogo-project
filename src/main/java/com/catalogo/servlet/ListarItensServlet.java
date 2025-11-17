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
 * Servlet responsável por processar a requisição que irá retornar os itens cadastrados
 *
 * Mapeado para a URL: /listagem
 * (Context Path + /listagem -> ex: /catalogo/listagem)
 *
 * @author leooyey
 * @version 1.0
 * @since 2025-11-17
 */
@WebServlet("/listagem")
public class ListarItensServlet extends HttpServlet {

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
     * Usa a DAO para listar todos os itens cadastrados
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
            //aqui vai pegar os itens cadastrados, listando-os
            List<ItemMidia> listaItens = itemMidiaDAO.listarTodos();

            //aqui ele vai colocar esses itens no atributo da requisição
            request.setAttribute("itens", listaItens);

            //aqui ele manda de volta pro jsp correspondente
            request.getRequestDispatcher("/listagem.jsp").forward(request, response);

        } catch (SQLException e) {
            //faz o tratamento de erros
            System.err.println("Erro de SQL: " + e.getMessage());
            e.printStackTrace();

            request.setAttribute("mensagemErro", "Erro interno ao listar o catálogo. Verifique o banco de dados.");
            request.getRequestDispatcher("/listagem.jsp").forward(request, response);
        }
    }
}