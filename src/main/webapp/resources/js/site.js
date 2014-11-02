$(function() {
	adicionarEvento();
});

function adicionarEvento() {
	jQuery(".img").each(function(indice, elemento) {
		jQuery(elemento).on("click.realizaVotacao", function() {
			realizaVotacao(elemento)
		});
	});
}

function realizaVotacao(obj) {
	var $livro = jQuery(obj);
	var id = $livro.attr('data-id');
	// remove o evento da imagem para o usuario nao votar varias vezes antes
	// do ajax terminar
	jQuery(".img").off("click.realizaVotacao");

	// esconde livro
	$livro.hide("slow", votarNoLivro(id, $livro));
}

function votarNoLivro(id, $livro) {
	var data = JSON.stringify({
		livro : {
			"id" : id
		}
	});

	var url = "voto?cid=" + getCID();
	jQuery.ajax({
		url : url,
		data : data,
		type : "POST",
		contentType : 'application/json',
		dataType : 'JSON'
	}).done(
			function(data) {
				// remove a imagem para causar um efeito melhor
				$livro.detach();

				if (data === undefined || data == "") {
					// indica que não existem mais livros para votar
					redirect();
					return;
				}
				// reajusta elemento com o novo livro vindo do servidor
				$livro.attr("data-id", data.id).attr("title", data.nome).attr(
						"src", data.url);
				// recoloca a imagem no DOM
				$livro.appendTo("#container-img");

				// exibe novamente
				$livro.show("slow");
				// recoloca o evento para o usuario votar novamente
				adicionarEvento();

			}).fail(function(jqXHR, textStatus, errorThrown) {
		console.log(jqXHR);
		console.log(textStatus);
		console.log(errorThrown);
	});
}