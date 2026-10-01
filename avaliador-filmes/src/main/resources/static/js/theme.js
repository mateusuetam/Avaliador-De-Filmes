$(document).ready(function () {

    aplicarTema();
    $("#alternarTema").on("click", function () {
        alternarTema();
    });
});


function obterCookie(nome) {

    const cookies = document.cookie.split(";");

    for (let cookie of cookies) {
        cookie = cookie.trim();
        if (cookie.startsWith(nome + "="))
            return cookie.substring(nome.length + 1);
    }

    return null;
}


function definirCookie(nome, valor) {
    document.cookie = nome + "=" + valor + "; Max-Age=31536000" + "; Path=/" + "; SameSite=Lax";
}


function aplicarTema() {

    const tema = obterCookie("tema");
    const botao = $("#alternarTema");
    const icone = botao.find(".icone-tema");
    const texto = botao.find(".texto-tema");

    if (tema === "escuro") {
        $("body").addClass("tema-escuro");
        icone.text("☀");
        texto.text("Tema claro");
    } else {
        $("body").removeClass("tema-escuro");
        icone.text("☾");
        texto.text("Tema escuro");
    }
}


function alternarTema() {

    const body = $("body");

    if (body.hasClass("tema-escuro")) {
        definirCookie("tema", "claro");
    } else {
        definirCookie("tema", "escuro");
    }

    aplicarTema();
}
