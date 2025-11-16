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

@WebServlet("/cadastrar")
public class CadastrarItemServlet extends HttpServlet {

    private ItemMidiaDAO itemMidiaDAO;

    @Override
    public void init() throws ServletException {
        this.itemMidiaDAO = new ItemMidiaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String titulo = request.getParameter("titulo");
        String autorDiretor = request.getParameter("autorDiretor");
        String anoStr = request.getParameter("ano");
        String genero = request.getParameter("genero");
        String tipoMidia = request.getParameter("tipoMidia");
        String sinopse = request.getParameter("sinopse");

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

        if (!erros.isEmpty()) {
            ItemMidia itemComErro = new ItemMidia(titulo, autorDiretor, anoLancamento, genero, sinopse, tipoMidia);

            request.setAttribute("erros", erros);
            request.setAttribute("item", itemComErro);

            request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
            return;
        }

        ItemMidia novoItem = new ItemMidia(titulo, autorDiretor, anoLancamento, genero, sinopse, tipoMidia);

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