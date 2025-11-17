package com.catalogo.dao;

import com.catalogo.model.ItemMidia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade ItemMidia.
 * Responsável pelo CRUD dos itens, com métodos de inserção, exclusão, busca e atualização
 *
 * @author leooyey
 * @version 1.0
 * @since 2025-11-17
 */
public class ItemMidiaDAO {

    /**
     * Insere um novo item no banco
     *
     * @param item objeto ItemMidia que tem os dados a serem inseridos.
     * @throws SQLException se ocorrer um erro durante a operação de INSERT
     */
    public void inserir(ItemMidia item) throws SQLException {
        String sql = "INSERT INTO item_midia (titulo, autor_diretor, ano_lancamento, genero, sinopse, tipo_midia) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        //aqui prepara o comando pra executar no banco
        try (Connection conn = FabricaDeConexoes.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getTitulo());
            stmt.setString(2, item.getAutorDiretor());
            stmt.setInt(3, item.getAnoLancamento());
            stmt.setString(4, item.getGenero());
            stmt.setString(5, item.getSinopse());
            stmt.setString(6, item.getTipoMidia());

            stmt.executeUpdate();

        //se tiver algum erro/exceção durante a execução do bloco acima, mostra qual foi a msg de erro
        } catch (SQLException e) {
            System.err.println("Erro ao inserir ItemMidia: " + e.getMessage());
            throw new SQLException("Não foi possível cadastrar o item. Verifique a conexão com o banco.", e);
        }
    }

    /**
     * Lista todos os itens cadastrados no banco
     *
     * @throws SQLException se ocorrer um erro durante a operação de SELECT
     */
    //função para listar os itens cadastrados
    public List<ItemMidia> listarTodos() throws SQLException {
        List<ItemMidia> itens = new ArrayList<>();
        String sql = "SELECT id, titulo, autor_diretor, ano_lancamento, genero, sinopse, tipo_midia FROM item_midia ORDER BY titulo";

        //aqui vai executar o comando declarado acima no banco, pra listar todos os itens cadastrados
        try (Connection conn = FabricaDeConexoes.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            //vai pegando um por um, pra mostrar na aplicação
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

        //se tiver alguma exceção na execução do bloco acima, mostra a mensagem de erro aqui
        } catch (SQLException e) {
            System.err.println("Erro ao listar ItemMidia: " + e.getMessage());
            throw new SQLException("Não foi possível listar os itens do catálogo.", e);
        }
        return itens;
    }

    /**
     * Busca um item no banco que contenha, no autor/diretor ou no título, o termo digitado pelo usuário
     * Numa lista, ele vai armazenando todos os itens que estão dentro do termo digitado
     *
     * @param termo string que vai conter o que o usuário digitou na barra de pesquisa no .jsp de listagem
     * @throws SQLException se ocorrer um erro durante a operação de SELECT
     */
    //busca itens cadastrados por parâmetro
    public List<ItemMidia> buscar(String termo) throws SQLException {
        List<ItemMidia> itens = new ArrayList<>();
        String sql = "SELECT id, titulo, autor_diretor, ano_lancamento, genero, sinopse, tipo_midia FROM item_midia " +
                "WHERE titulo LIKE ? OR autor_diretor LIKE ?";

        //aqui, ele define que a busca vai ser feita quando "titulo" ou "autor_diretor" a palavra/frase digitada em qualquer parte
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

    /**
     * Faz a busca de itens no banco pelo ID
     * Usado em consultas onde tem que filtrar certinho pelo ID
     *
     * @param id Id do item no banco
     * @throws SQLException se ocorrer um erro durante a operação de SELECT
     */
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

    /**
     * Atualiza as informações de um item já existente no banco
     *
     * @param item objeto ItemMidia que tem os dados a serem inseridos.
     * @throws SQLException se ocorrer um erro durante a operação de UPDATE
     */
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

    /**
     * Exclui um item no banco
     *
     * @param id Id do objeto que vai ser excluído
     * @throws SQLException se ocorrer um erro durante a operação de DELETE
     */
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