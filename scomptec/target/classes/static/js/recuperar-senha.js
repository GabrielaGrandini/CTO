const formulario = document.querySelector("#formRecuperacao");

formulario.addEventListener("submit", async (event) => {
    event.preventDefault();

    const email = document.querySelector("#email").value;

    try {
        const resposta = await fetch(
            `${API_URL}/api/usuarios/recuperar-senha`,
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ email })
            }
        );

        const mensagem = await resposta.text();

        alert(mensagem);

    } catch (erro) {
        console.error(erro);
        alert("Não foi possível realizar a recuperação de senha.");
    }
});