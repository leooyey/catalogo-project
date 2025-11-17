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
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet responsável por processar a requisição que irá cadastrar itens novos
 *
 * Mapeado para a URL: /cadastrar
 * (Context Path + /cadastrar -> ex: /catalogo/cadastrar)
 *
 * @author leooyey
 * @version 1.0
 * @since 2025-11-17
 */
@WebServlet("/cadastrar")
public class CadastrarItemServlet extends HttpServlet {

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
     * Processa requisições com método POST
     * Usa a DAO para listar todos os itens cadastrados que estejam contamplados pelo termo digitado
     * Vai realizar as validações necessárias dos campos do item a ser cadastrado
     *
     * @param request objeto HttpServletRequest que vai ter os dados do item
     * @param response objeto HttpServletResponse
     * @throws ServletException se algum erro do servlet ocorrer
     * @throws IOException se algum erro de entrada/saída ocorrer
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //pega os dados digitados na requisição
        String titulo = request.getParameter("titulo");
        String autorDiretor = request.getParameter("autorDiretor");
        String anoStr = request.getParameter("ano");
        String genero = request.getParameter("genero");
        String tipoMidia = request.getParameter("tipoMidia");
        String sinopse = request.getParameter("sinopse");

        //começa as validações
        List<String> erros = new ArrayList<>();
        int anoLancamento = 0;

        if (titulo == null || titulo.trim().isEmpty()) {
            erros.add("O campo 'Título' é obrigatório.");
        }

        if (tipoMidia == null || tipoMidia.trim().isEmpty()) {
            erros.add("O campo 'Tipo' é obrigatório.");
        }

        if (titulo != null && titulo.length() > 255) {
            erros.add("O 'Título' não pode ter mais que 255 caracteres.");
        }

        if (autorDiretor != null && autorDiretor.length() > 255) {
            erros.add("O 'Autor/Diretor' não pode ter mais que 255 caracteres.");
        }

        if (genero != null && genero.length() > 100) {
            erros.add("O 'Gênero' não pode ter mais que 100 caracteres.");
        }

        if (tipoMidia != null && tipoMidia.length() > 50) {
            erros.add("O 'Tipo' não pode ter mais que 50 caracteres.");
        }

        try {
            if (anoStr != null && !anoStr.trim().isEmpty()) {
                anoLancamento = Integer.parseInt(anoStr);
            }
        } catch (NumberFormatException e) {
            erros.add("O 'Ano Lançamento' deve ser um número válido.");
        }

        //se tiver erro, retorna pro usuário
        if (!erros.isEmpty()) {
            ItemMidia itemComErro = new ItemMidia(titulo, autorDiretor, anoLancamento, genero, sinopse, tipoMidia);

            request.setAttribute("erros", erros);
            request.setAttribute("item", itemComErro);

            request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
            return;
        }

        //insere na DAO o item
        ItemMidia novoItem = new ItemMidia(titulo, autorDiretor, anoLancamento, genero, sinopse, tipoMidia);

        //tenta executar o metodo "inserir" da DAO, pra cadastrar o item no banco
        try {
            itemMidiaDAO.inserir(novoItem);
        } catch (SQLException e) {
            e.printStackTrace();
            erros.add("Erro inesperado de SQL ao salvar no banco: " + e.getMessage());
            request.setAttribute("erros", erros);
            request.setAttribute("item", novoItem);
            request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("listagem");
    }
}