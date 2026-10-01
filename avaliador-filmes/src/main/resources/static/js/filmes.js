$(document).ready(function () {

    carregarFilmes();

    $("#filmeForm").on("submit", function (event) {
        event.preventDefault();
        salvarFilme();
    });

    $("#cancelarEdicao").on("click", function () {
        limparFormulario();
    });
});


function carregarFilmes() {
    $.ajax({
        url: "/api/filmes",
        method: "GET",
        success: function (filmes) {
            renderizarFilmes(filmes);
        },
        error: function (xhr) {
            mostrarMensagem("Erro ao carregar os filmes: " + obterErro(xhr), true);
        }
    });
}


function renderizarFilmes(filmes) {

    const tabela = $("#filmesTabela");

    tabela.empty();

    if (filmes.length === 0) {

        tabela.append(`<tr>
                <td colspan="6"> Nenhum filme cadastrado. </td>
            </tr>
        `);
        return;
    }

    filmes.forEach(function (filme) {

        const assistido = filme.assistido ? "Sim" : "Não";

        tabela.append(`
            <tr>
                <td>${filme.id}</td>
                <td>${escaparHtml(filme.titulo)}</td>
                <td>${escaparHtml(filme.diretor)}</td>
                <td>${filme.anoLancamento}</td>
                <td>${assistido}</td>

                <td class="acoes">

                    <button
                        class="botao botao-editar"
                        onclick="editarFilme(${filme.id})">
                        Editar
                    </button>

                    <button
                        class="botao botao-excluir"
                        onclick="excluirFilme(${filme.id})">
                        Excluir
                    </button>

                    <a
                        class="botao"
                        href="/analises?filmeId=${filme.id}">
                        Análises
                    </a>

                </td>
            </tr>
        `);
    });
}


function salvarFilme() {

    const id = $("#filmeId").val();

    const filme = {
        titulo: $("#titulo").val(),
        diretor: $("#diretor").val(),
        anoLancamento: parseInt($("#anoLancamento").val()),
        assistido: $("#assistido").is(":checked")
    };

    if (id) {
        $.ajax({
            url: "/api/filmes/" + id,
            method: "PUT",
            contentType: "application/json",
            data: JSON.stringify(filme),

            success: function () {
                mostrarMensagem("Filme atualizado com sucesso.");
                limparFormulario();
                carregarFilmes();
            },
            error: function (xhr) {
                mostrarMensagem("Erro ao atualizar filme: " + obterErro(xhr), true);
            }
        });

    } else {
        $.ajax({
            url: "/api/filmes",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(filme),

            success: function () {
                mostrarMensagem("Filme cadastrado com sucesso.");
                limparFormulario();
                carregarFilmes();
            },
            error: function (xhr) {
                mostrarMensagem("Erro ao cadastrar filme: " + obterErro(xhr), true);
            }
        });
    }
}


function editarFilme(id) {
    $.ajax({
        url: "/api/filmes/" + id,
        method: "GET",

        success: function (filme) {
            $("#filmeId").val(filme.id);
            $("#titulo").val(filme.titulo);
            $("#diretor").val(filme.diretor);
            $("#anoLancamento").val(filme.anoLancamento);
            $("#assistido").prop("checked", filme.assistido);
            $("#tituloFormulario").text("Editar filme");
            $("#cancelarEdicao").removeClass("oculto");

            window.scrollTo({
                top: 0,
                behavior: "smooth"
            });
        },
        error: function (xhr) {
            mostrarMensagem("Erro ao carregar filme: " + obterErro(xhr), true);
        }
    });
}


function excluirFilme(id) {

    if (!confirm("Deseja realmente excluir este filme?"))
        return;

    $.ajax({
        url: "/api/filmes/" + id,
        method: "DELETE",

        success: function () {
            mostrarMensagem("Filme excluído com sucesso.");
            carregarFilmes();
        },
        error: function (xhr) {
            mostrarMensagem("Erro ao excluir filme: " + obterErro(xhr), true);
        }
    });
}


function limparFormulario() {
    $("#filmeForm")[0].reset();
    $("#filmeId").val("");
    $("#tituloFormulario").text("Cadastrar filme");
    $("#cancelarEdicao").addClass("oculto");
}


function mostrarMensagem(mensagem, erro = false) {

    const elemento = $("#mensagem");

    elemento
            .removeClass("sucesso erro")
            .addClass(erro ? "erro" : "sucesso")
            .text(mensagem)
            .stop(true, true)
            .fadeIn();

    setTimeout(function () {
        elemento.fadeOut();
    }, 4000);
}


function obterErro(xhr) {
    if (xhr.responseText)
        return xhr.responseText;
    return "erro desconhecido.";
}


function escaparHtml(texto) {
    return $("<div>").text(texto ?? "").html();
}
