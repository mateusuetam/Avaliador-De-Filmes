$(document).ready(function () {

    carregarFilmes();
    carregarAnalises();

    $("#analiseForm").on("submit", function (event) {
        event.preventDefault();
        salvarAnalise();
    });

    $("#cancelarEdicao").on("click", function () {
        limparFormulario();
    });

    selecionarFilmeDaUrl();
});


function carregarFilmes() {
    $.ajax({
        url: "/api/filmes",
        method: "GET",

        success: function (filmes) {
            const select = $("#filme");
            select.empty();
            select.append(`
                <option value="">
                    Selecione um filme
                </option>
            `);
            filmes.forEach(function (filme) {
                select.append(`
                    <option value="${filme.id}">
                        ${escaparHtml(filme.titulo)}
                    </option>
                `);
            });
        },
        error: function (xhr) {
            mostrarMensagem("Erro ao carregar filmes: " + obterErro(xhr), true);
        }
    });
}


function carregarAnalises() {
    $.ajax({
        url: "/api/analises",
        method: "GET",

        success: function (analises) {
            renderizarAnalises(analises);
        },
        error: function (xhr) {
            mostrarMensagem("Erro ao carregar análises: " + obterErro(xhr), true);
        }
    });
}


function renderizarAnalises(analises) {

    const tabela = $("#analisesTabela");

    tabela.empty();

    if (analises.length === 0) {
        tabela.append(`
            <tr>
                <td colspan="5"> Nenhuma análise cadastrada. </td>
            </tr>
        `);
        return;
    }

    analises.forEach(function (analise) {
        tabela.append(`
            <tr>
                <td>${analise.id}</td>
                <td>${escaparHtml(analise.filme.titulo)}</td>
                <td>${analise.nota}</td>
                <td>${escaparHtml(analise.comentario)}</td>

                <td class="acoes">
                    <button
                        class="botao botao-editar"
                        onclick="editarAnalise(${analise.id})">
                        Editar
                    </button>

                    <button
                        class="botao botao-excluir"
                        onclick="excluirAnalise(${analise.id})">
                        Excluir
                    </button>
                </td>
            </tr>
        `);
    });
}


function salvarAnalise() {

    const id = $("#analiseId").val();
    const dados = {
        filmeId: parseInt($("#filme").val()),
        nota: parseInt($("#nota").val()),
        comentario: $("#comentario").val().trim()
    };

    if (id) {
        $.ajax({
            url: "/api/analises/" + id,
            method: "PUT",
            contentType: "application/json",
            data: JSON.stringify(dados),

            success: function () {
                mostrarMensagem("Análise atualizada com sucesso.");
                limparFormulario();
                carregarAnalises();
            },
            error: function (xhr) {
                mostrarMensagem("Erro ao atualizar análise: " + obterErro(xhr), true);
            }
        });
    } else {
        $.ajax({
            url: "/api/analises",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(dados),

            success: function () {
                mostrarMensagem("Análise cadastrada com sucesso.");
                limparFormulario();
                carregarAnalises();
            },
            error: function (xhr) {
                mostrarMensagem("Erro ao cadastrar análise: " + obterErro(xhr), true);
            }
        });
    }
}


function editarAnalise(id) {
    $.ajax({
        url: "/api/analises/" + id,
        method: "GET",

        success: function (analise) {
            $("#analiseId").val(analise.id);
            $("#filme").val(analise.filme.id);
            $("#nota").val(analise.nota);
            $("#comentario").val(analise.comentario);
            $("#tituloFormulario").text("Editar análise");
            $("#cancelarEdicao").removeClass("oculto");

            window.scrollTo({
                top: 0,
                behavior: "smooth"
            });
        },
        error: function (xhr) {
            mostrarMensagem("Erro ao carregar análise: " + obterErro(xhr), true);
        }
    });
}


function excluirAnalise(id) {

    if (!confirm("Deseja realmente excluir esta análise?"))
        return;

    $.ajax({
        url: "/api/analises/" + id,
        method: "DELETE",

        success: function () {
            mostrarMensagem("Análise excluída com sucesso.");
            carregarAnalises();
        },
        error: function (xhr) {
            mostrarMensagem("Erro ao excluir análise: " + obterErro(xhr), true);
        }
    });
}


function selecionarFilmeDaUrl() {

    const parametros = new URLSearchParams(window.location.search);
    const filmeId = parametros.get("filmeId");

    if (filmeId)
        $("#filme").val(filmeId);
}


function limparFormulario() {
    $("#analiseForm")[0].reset();
    $("#analiseId").val("");
    $("#tituloFormulario").text("Cadastrar análise");
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
