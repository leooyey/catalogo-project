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

    <p>
        <a href="listagem">Ver Lista Completa</a> |
        <a href="cadastro.jsp">Adicionar Novo Item</a> |
        <a href="./index.jsp">Voltar para a Home</a>
    </p>

    <%-- implementação da barra de busca --%>
    <div style="margin: 20px 0; padding: 10px; border: 1px solid #ccc;">
        <form action="buscar" method="GET">
            <label for="termo">Buscar por Título ou Autor/Diretor:</label>
            <input type="text" id="termo" name="termo" value="<c:out value='${termoBusca}'/>">
            <button type="submit">Buscar</button>
        </form>
    </div>


    <%-- Exibe mensagem de erro, se houver --%>
    <c:if test="${not empty mensagemErro}">
        <p style="color: red;"><strong>ERRO NO SISTEMA:</strong> ${mensagemErro}</p>
    </c:if>

    <%-- aqui vai mostrar o termo digitado na busca --%>
    <c:if test="${not empty termoBusca}">
        <h2>Resultados da Busca por: "<c:out value='${termoBusca}'/>"</h2>
    </c:if>

    <c:choose>
        <c:when test="${empty itens}">
            <p>Nenhum item encontrado.</p>
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
                                <a href="detalhes?id=${item.id}">Detalhes</a> |
                                <a href="editar?id=${item.id}">Editar</a> |
                                <a href="excluir?id=${item.id}" onclick="return confirm('Tem certeza que deseja excluir o item: ${item.titulo}?');">Excluir</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

</body>
</html>