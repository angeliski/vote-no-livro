<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div id="container-img">
		<c:forEach items="${livros}" var="livro" varStatus="i">
			<img title="${livro.nome}" alt="${livro.nome}" src="${livro.url}"
				class="img" data-id="${livro.id}" />
		</c:forEach>
	</div>
	<content tag="script">
	<script type="text/javascript">
		function getCID() {
			return "${cid}";
		}
		function redirect(){
			window.document.location = '${pageContext.request.contextPath}/usuario?cid='+ getCID();
		}
	</script>
	</content>
</body>
</html>