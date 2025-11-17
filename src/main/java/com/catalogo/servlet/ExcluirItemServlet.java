package com.catalogo.servlet;

import com.catalogo.dao.ItemMidiaDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet responsável por processar a requisição que irão excluir itens
 *
 * Mapeado para a URL: /excluir
 * (Context Path + /excluir -> ex: /catalogo/excluir)
 *
 * @author leooyey
 * @version 1.0
 * @since 2025-11-17
 */
@WebServlet("/excluir")
public class ExcluirItemServlet extends HttpServlet {

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
     * Pega o ID informado no parametro da requisição, e passa para a DAO pelo método excluir, pra deletar do banco
     * Realiza validações pra ver se o item com aquele ID existe no banco
     *
     * @param request objeto HttpServletRequest
     * @param response objeto HttpServletResponse
     * @throws ServletException se algum erro do servlet ocorrer
     * @throws IOException se algum erro de entrada/saída ocorrer
     */
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