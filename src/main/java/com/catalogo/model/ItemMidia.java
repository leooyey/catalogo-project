package com.catalogo.model;


/**
 * Classe que representa um item, sendo ele um Livro, Filme ou Série.
 * Implementados na classe: atributos, construtores e "getters e setters"
 *
 * @author leooyey
 * @version 1.0
 * @since 2025-11-17
 */
public class ItemMidia {

    private int id;
    private String titulo;
    private String autorDiretor;
    private int anoLancamento;
    private String genero;
    private String sinopse;
    private String tipoMidia;


    public ItemMidia() {
    }

    /**
     * Construtor sem o ID, sendo esse ID gerado pelo banco de forma sequencial e automática
     *
     * @param titulo título do item
     * @param autorDiretor autor (para Livros) ou diretor (para Filmes e Séries).
     * @param anoLancamento ano de lançamento.
     * @param genero gênero do item.
     * @param sinopse descrição curta do item.
     * @param tipoMidia tipo (podendo ser 'Livro', 'Filme' ou 'Série').
     */
    public ItemMidia(String titulo, String autorDiretor, int anoLancamento, String genero, String sinopse, String tipoMidia) {
        this.titulo = titulo;
        this.autorDiretor = autorDiretor;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
        this.sinopse = sinopse;
        this.tipoMidia = tipoMidia;
    }

    /**
     * Construtor com o ID, esse sendo usado nas consultas ao banco de dados
     *
     * @param titulo título do item
     * @param autorDiretor autor (para Livros) ou diretor (para Filmes e Séries).
     * @param anoLancamento ano de lançamento.
     * @param genero gênero do item.
     * @param sinopse descrição curta do item.
     * @param tipoMidia tipo (podendo ser 'Livro', 'Filme' ou 'Série').
     */
    public ItemMidia(int id, String titulo, String autorDiretor, int anoLancamento, String genero, String sinopse, String tipoMidia) {
        this.id = id;
        this.titulo = titulo;
        this.autorDiretor = autorDiretor;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
        this.sinopse = sinopse;
        this.tipoMidia = tipoMidia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutorDiretor() {
        return autorDiretor;
    }

    public void setAutorDiretor(String autorDiretor) {
        this.autorDiretor = autorDiretor;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getTipoMidia() {
        return tipoMidia;
    }

    public void setTipoMidia(String tipoMidia) {
        this.tipoMidia = tipoMidia;
    }
}