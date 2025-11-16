<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Item</title>
</head>
<body>
    <h1>Editar Item do Catálogo</h1>
    <p><a href="listagem">Voltar para a Lista</a></p>

    <%-- Bloco que exibe os erros de validação (igual ao cadastro) --%>
    <c:if test="${not empty erros}">
        <div style="color: red; border: 1px solid red; padding: 10px; margin-bottom: 15px;">
            <strong>Por favor, corrija os seguintes erros:</strong>
            <ul>
                <c:forEach var="erro" items="${erros}">
                    <li><c:out value="${erro}"/></li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <%-- O formulário envia os dados para o MESMO Servlet ("/editar") via POST --%>
    <form action="editar" method="POST">
        <fieldset>
            <legend>Editando Item: <c:out value="${item.titulo}"/></legend>

            <%-- CAMPO OCULTO (HIDDEN) PARA ENVIAR O ID --%>
            <input type="hidden" name="id" value="<c:out value='${item.id}'/>">

            <div>
                <label for="titulo">Título:</label>
                <input type="text" id="titulo" name="titulo" required
                       value="<c:out value='${item.titulo}'/>">
            </div>
            <br>
            <div>
                <label for="autorDiretor">Autor/Diretor:</label>
                <input type="text" id="autorDiretor" name="autorDiretor"
                       value="<c:out value='${item.autorDiretor}'/>">
            </div>
            <br>
            <div>
                <label for="ano">Ano Lançamento:</label>
                <input type="number" id="ano" name="ano" min="1800" max="2030"
                       value="<c:out value='${item.anoLancamento > 0 ? item.anoLancamento : ""}'/>">
            </div>
            <br>
            <div>
                <label for="genero">Gênero:</label>
                <input type="text" id="genero" name="genero"
                       value="<c:out value='${item.genero}'/>">
            </div>
            <br>
            <div>
                <label for="tipoMidia">Tipo:</label>
                <select id="tipoMidia" name="tipoMidia" required>
                    <option value="Livro" ${item.tipoMidia == 'Livro' ? 'selected' : ''}>Livro</option>
                    <option value="Filme" ${item.tipoMidia == 'Filme' ? 'selected' : ''}>Filme</option>
                    <option value="Série" ${item.tipoMidia == 'Série' ? 'selected' : ''}>Série</option>
                </select>
            </div>
            <br>
            <div>
                <label for="sinopse">Sinopse:</label><br>
                <textarea id="sinopse" name="sinopse" rows="4" cols="50"><c:out value='${item.sinopse}'/></textarea>
            </div>
            <br>
            <div>
                <button type="submit">Atualizar</button>
            </div>
        </fieldset>
    </form>
</body>
</html>