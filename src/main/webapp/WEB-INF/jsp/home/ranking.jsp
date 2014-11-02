<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:set var="usuarioNovo" value="${empty usuario.ultimaVisita }"
	scope="page" />
</head>
<body>
	<h4>
		Seja Bem Vindo
		<c:if test="${not usuarioNovo}">novamente</c:if>
		${usuario.nome}!
		<c:if test="${not usuarioNovo}">As coisas podem ter mudado desde a sua ultima visita.<br />
		</c:if>
		Obrigado pelos seus votos!
	</h4>
	<div id="ranking-livros" style="width: 100%; height: 400px;"></div>
	<content tag="jsCustom"> <script
		src="${pageContext.request.contextPath}/resources/js/highcharts.js"></script>
	</content>
	<content tag="script"> <script type="text/javascript">
		$(function() {
			// efeito visual
			Highcharts.getOptions().colors = Highcharts.map(Highcharts
					.getOptions().colors, function(color) {
				return {
					radialGradient : {
						cx : 0.5,
						cy : 0.3,
						r : 0.7
					},
					stops : [
							[ 0, color ],
							[
									1,
									Highcharts.Color(color).brighten(-0.3).get(
											'rgb') ] // darken
					]
				};
			});

			// construção efetiva do grafico
			$('#ranking-livros')
					.highcharts(
							{
								chart : {
									plotBackgroundColor : null,
									plotBorderWidth : null,
									plotShadow : false
								},
								title : {
									text : 'Ranking dos Livros'
								},
								tooltip : {
									pointFormat : '<b>{point.percentage:.1f}%</b>'
								},
								plotOptions : {
									pie : {
										allowPointSelect : true,
										cursor : 'pointer',
										showInLegend : true,
										dataLabels : {
											enabled : true,
											format : '<b>{point.name}</b>: {point.y}',
											style : {
												color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
														|| 'black'
											},
											connectorColor : 'silver'
										}
									}
								},
								series : [ {
									type : 'pie',
									name : 'Ranking dos Livros',
									data : getData()
								} ]
							});
		});

		function getData() {
			var data = [
					<c:forEach items="${livros}" var="livro" varStatus="i">
					['${livro.nome}', ${livro.total}], 
					</c:forEach>				
						];

			return data;
		}

		function getCID() {
			return "${cid}";
		}
	</script> </content>
</body>
</html>