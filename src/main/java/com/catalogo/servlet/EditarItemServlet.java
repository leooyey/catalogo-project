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

//mapeamento é "/editar", definido tbm no .jsp
@WebServlet("/editar")
public class EditarItemServlet extends HttpServlet {

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
            request.getRequestDispatcher("/editar.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("listagem?erro=IdInvalido");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erroGeral", "Erro de SQL ao buscar item: " + e.getMessage());
            request.getRequestDispatcher("/listagem.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String titulo = request.getParameter("titulo");
        String autorDiretor = request.getParameter("autorDiretor");
        String anoStr = request.getParameter("ano");
        String genero = request.getParameter("genero");
        String tipoMidia = request.getParameter("tipoMidia");
        String sinopse = request.getParameter("sinopse");

        List<String> erros = new ArrayList<>();
        int anoLancamento = 0;

        if (titulo == null || titulo.trim().isEmpty()) {
            erros.add("O 'Título' é obrigatório.");
        }

        if (tipoMidia == null || tipoMidia.trim().isEmpty()) {
            erros.add("O 'Tipo' é obrigatório.");
        }

        if (titulo != null && titulo.length() > 255) {
            erros.add("O 'Título' excede 255 caracteres.");
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

        ItemMidia item = new ItemMidia(id, titulo, autorDiretor, anoLancamento, genero, sinopse, tipoMidia);

        if (!erros.isEmpty()) {
            request.setAttribute("erros", erros);
            request.setAttribute("item", item); // Devolve o item (modificado)
            request.getRequestDispatcher("/editar.jsp").forward(request, response);
            return;
        }

        try {
            itemMidiaDAO.atualizar(item);
        } catch (SQLException e) {
            e.printStackTrace();
            erros.add("Erro de SQL ao ATUALIZAR no banco: " + e.getMessage());
            request.setAttribute("erros", erros);
            request.setAttribute("item", item);
            request.getRequestDispatcher("/editar.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("listagem");
    }
}