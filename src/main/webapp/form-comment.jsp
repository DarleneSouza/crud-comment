<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="nav-menu.jsp"%>
			
		<div id="container" class="container-fluid">
			<h3 class="page-header">Adicionar Comentario</h3>

			<form action="${pageContext.request.contextPath}/comment/${action}" method="POST">
				<input type="hidden" value="${comment.getId()}" name="commentId">
				<div class="row">
                    <div class="form-group col-md-6">
                        <label for="content">Conteúdo</label>
                            <input type="text" class="form-control" id="content" name="content" 
                                   autofocus="autofocus" placeholder="Conteúdo do comentario" 
                                   required oninvalid="this.setCustomValidity('Por favor, informe o conteúdo do comentario.')"
                                   oninput="setCustomValidity('')"
                                   value="${comment.getContent()}">
                        </div>
					<div class="form-group col-md-6">
					<label for="content">Post</label>
						<select id="post" class="form-control selectpicker" name="post" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o post.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty post ? "" : "selected"}>Selecione um post</option>
						  <c:forEach var="post" items="${posts}">
						  	<option value="${post.getId()}"  ${comment.getPost().getId() == post.getId() ? "selected" : ""}>
						  		${post.getContent()}
						  	</option>	
						  </c:forEach>
                        </select>
					</div>

					<div class="form-group col-md-6">
						<label for="user">Usuário</label>
						<select id="user" class="form-control selectpicker" name="user" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o usuário.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty post ? "" : "selected"}>Selecione um usuário</option>
						  <c:forEach var="user" items="${users}">
						  	<option value="${user.getId()}"  ${comment.getUser().getId() == user.getId() ? "selected" : ""}>
						  		${user.getName()}
						  	</option>
						  </c:forEach>
						</select>
					</div>
                    <div class="form-group col-md-6">
                        <label for="reacao">Reacao</label>
                            <select id="reacao" class="form-control selectpicker" name="reacao" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o usuário.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty post ? "" : "selected"}>Selecione uma Reacao</option>
						  <option value="gostei"  ${comment.getReacao == "gostei" ? "selected" : ""}>gostei</option>
						  <option value="amei"  ${comment.getReacao == "amei" ? "selected" : ""}>amei</option>
						  <option value="divetido"  ${comment.getReacao == "divetido" ? "selected" : ""}>divetido</option>
						  <option value="triste"  ${comment.getReacao == "triste" ? "selected" : ""}>triste</option>
						</select>
                        </div>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/comment" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty post ? "Alterar Comentario" : "Criar Comentario"}</button>
					</div>
				</div>
			</form>
		</div>

		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>
