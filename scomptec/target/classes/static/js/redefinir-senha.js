const formulario = document.querySelector("#formRedefinirSenha");

formulario.addEventListener("submit", async (event) => {
    event.preventDefault();

    const novaSenha = document.querySelector("#novaSenha").value;
    const confirmarSenha = document.querySelector("#confirmarSenha").value;

    if (novaSenha !== confirmarSenha) {
        alert("As senhas não coincidem.");
        return;
    }

    const parametros = new URLSearchParams(window.location.search);
    const token = parametros.get("token");

    if (!token) {
        alert("Token de recuperação não encontrado.");
        return;
    }

    try {
        const resposta = await fetch(
            `${API_URL}/api/usuarios/redefinir-senha`,
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    token: token,
                    novaSenha: novaSenha
                })
            }
        );

        const mensagem = await resposta.text();

        alert(mensagem);

        if (resposta.ok && mensagem === "Senha redefinida com sucesso.") {
            window.location.href = "index.html";
        }

    } catch (erro) {
        console.error(erro);
        alert("Não foi possível redefinir a senha.");
    }
});