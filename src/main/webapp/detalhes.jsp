<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalhes do Item</title>
    <style>
        /* Estilo simples para organizar os detalhes */
        .detalhe-campo { font-weight: bold; }
        .detalhe-bloco { margin-bottom: 10px; }
    </style>
</head>
<body>
    <h1>Detalhes do Item</h1>
    <p><a href="listagem">Voltar para a Lista</a></p>

    <%--
      Verifica se o objeto 'item' foi enviado pelo Servlet.
      Usamos <c:out> para exibir os dados com segurança (prevenção de XSS) [cite: 241]
    --%>
    <c:if test="${not empty item}">
        <h2><c:out value="${item.titulo}"/></h2>

        <div class="detalhe-bloco">
            <span class="detalhe-campo">ID:</span>
            <c:out value="${item.id}"/>
        </div>

        <div class="detalhe-bloco">
            <span class="detalhe-campo">Autor/Diretor:</span>
            <c:out value="${item.autorDiretor}"/>
        </div>

        <div class="detalhe-bloco">
            <span class="detalhe-campo">Ano Lançamento:</span>
            <c:out value="${item.anoLancamento}"/>
        </div>

        <div class="detalhe-bloco">
            <span class="detalhe-campo">Gênero:</span>
            <c:out value="${item.genero}"/>
        </div>

        <div class="detalhe-bloco">
            <span class="detalhe-campo">Tipo:</span>
            <c:out value="${item.tipoMidia}"/>
        </div>

        <div class="detalhe-bloco">
            <span class="detalhe-campo">Sinopse:</span><br>
            <%-- Usamos <c:out> para a sinopse também --%>
            <p><c:out value="${item.sinopse}"/></p>
        </div>

        <hr>
        <a href="editar?id=${item.id}">Editar este item</a> |
        <a href="excluir?id=${item.id}"
           onclick="return confirm('Tem certeza que deseja excluir o item: ${item.titulo}?');">Excluir este item</a>

    </c:if>

    <c:if test="${empty item}">
        <p style="color: red;">Item não encontrado.</p>
    </c:if>

</body>
</html>