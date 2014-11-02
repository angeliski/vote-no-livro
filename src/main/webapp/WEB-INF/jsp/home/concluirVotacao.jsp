<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-offset-2 col-md-8">
				<form class="form-usuario" role="form">
					<input type="text" class="form-control" placeholder="Nome" required
						autofocus> <input type="email" class="form-control"
						placeholder="Email" required autofocus>
					<button class="btn btn-lg btn-primary btn-block" type="submit">Confirmar</button>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function getCID() {
			return "${cid}";
		}
	</script>
</body>
</html>