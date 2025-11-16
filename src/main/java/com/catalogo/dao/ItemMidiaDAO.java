package com.catalogo.dao;

import com.catalogo.model.ItemMidia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemMidiaDAO {

    //função para inserir os itens cadastrados
    public void inserir(ItemMidia item) throws SQLException {
        String sql = "INSERT INTO item_midia (titulo, autor_diretor, ano_lancamento, genero, sinopse, tipo_midia) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = FabricaDeConexoes.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getTitulo());
            stmt.setString(2, item.getAutorDiretor());
            stmt.setInt(3, item.getAnoLancamento());
            stmt.setString(4, item.getGenero());
            stmt.setString(5, item.getSinopse());
            stmt.setString(6, item.getTipoMidia());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir ItemMidia: " + e.getMessage());
            throw new SQLException("Não foi possível cadastrar o item. Verifique a conexão com o banco.", e);
        }
    }

    //função para listar os itens cadastrados
    public List<ItemMidia> listarTodos() throws SQLException {
        List<ItemMidia> itens = new ArrayList<>();
        String sql = "SELECT id, titulo, autor_diretor, ano_lancamento, genero, sinopse, tipo_midia FROM item_midia ORDER BY titulo";

        try (Connection conn = FabricaDeConexoes.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ItemMidia item = new ItemMidia(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor_diretor"),
                        rs.getInt("ano_lancamento"),
                        rs.getString("genero"),
                        rs.getString("sinopse"),
                        rs.getString("tipo_midia")
                );
                itens.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar ItemMidia: " + e.getMessage());
            throw new SQLException("Não foi possível listar os itens do catálogo.", e);
        }
        return itens;
    }

    //busca itens cadastrados por parâmetro
    public List<ItemMidia> buscar(String termo) throws SQLException {
        List<ItemMidia> itens = new ArrayList<>();
        String sql = "SELECT id, titulo, autor_diretor, ano_lancamento, genero, sinopse, tipo_midia FROM item_midia " +
                "WHERE titulo LIKE ? OR autor_diretor LIKE ?";

        String termoBusca = "%" + termo + "%";

        try (Connection conn = FabricaDeConexoes.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, termoBusca);
            stmt.setString(2, termoBusca);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemMidia item = new ItemMidia(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor_diretor"),
                            rs.getInt("ano_lancamento"),
                            rs.getString("genero"),
                            rs.getString("sinopse"),
                            rs.getString("tipo_midia")
                    );
                    itens.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ItemMidia: " + e.getMessage());
            throw new SQLException("Não foi possível realizar a busca no catálogo.", e);
        }
        return itens;
    }

    //função pra buscar os itens por id
    public ItemMidia buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM item_midia WHERE id = ?";
        ItemMidia item = null;

        try (Connection conn = FabricaDeConexoes.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    item = new ItemMidia(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor_diretor"),
                            rs.getInt("ano_lancamento"),
                            rs.getString("genero"),
                            rs.getString("sinopse"),
                            rs.getString("tipo_midia")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ItemMidia por ID: " + e.getMessage());
            throw new SQLException("Não foi possível localizar o item com ID: " + id, e);
        }
        return item;
    }

    //função pra atualizar itens jah existentes no banco
    public void atualizar(ItemMidia item) throws SQLException {
        String sql = "UPDATE item_midia SET titulo = ?, autor_diretor = ?, ano_lancamento = ?, " +
                "genero = ?, sinopse = ?, tipo_midia = ? WHERE id = ?";

        try (Connection conn = FabricaDeConexoes.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getTitulo());
            stmt.setString(2, item.getAutorDiretor());
            stmt.setInt(3, item.getAnoLancamento());
            stmt.setString(4, item.getGenero());
            stmt.setString(5, item.getSinopse());
            stmt.setString(6, item.getTipoMidia());
            stmt.setInt(7, item.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar ItemMidia: " + e.getMessage());
            throw new SQLException("Não foi possível atualizar o item no banco.", e);
        }
    }

    //função pra excluir um item cadastrado (aqui exclui por id)
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM item_midia WHERE id = ?";

        try (Connection conn = FabricaDeConexoes.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao excluir ItemMidia: " + e.getMessage());
            throw new SQLException("Não foi possível excluir o item do banco.", e);
        }
    }
}