<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Itens do Catálogo</title>
</head>
<body>
    <h1>Catálogo de Livros/Filmes</h1>

    <%-- Exibe mensagem de erro, se houver --%>
    <c:if test="${not empty mensagemErro}">
        <p style="color: red;"><strong>ERRO NO SISTEMA:</strong> ${mensagemErro}</p>
    </c:if>

    <p><a href="./index.jsp">Voltar para a Home</a></p>

    <%-- Verifica se a lista de itens (do Servlet) está vazia --%>
    <c:choose>
        <c:when test="${empty itens}">
            <p>O catálogo está vazio. Nenhum item encontrado no banco de dados.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="5" cellspacing="0">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Título</th>
                        <th>Autor/Diretor</th>
                        <th>Ano</th>
                        <th>Tipo</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- Itera sobre a lista de ItemMidia (variável 'itens' definida no Servlet) --%>
                    <c:forEach var="item" items="${itens}">
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.titulo}</td>
                            <td>${item.autorDiretor}</td>
                            <td>${item.anoLancamento}</td>
                            <td>${item.tipoMidia}</td>
                            <td>
                                <%-- Link futuro para a página de detalhes --%>
                                <a href="./detalhes?id=${item.id}">Detalhes</a> |
                                <a href="./editar?id=${item.id}">Editar</a> |
                                <a href="excluir?id=${item.id}" onclick="return confirm('Tem certeza que deseja excluir o item: ${item.titulo}?');">Excluir</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <p><a href="cadastro.jsp">Cadastrar Novo Item</a></p>

</body>
</html>