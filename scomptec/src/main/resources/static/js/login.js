const formulario = document.querySelector("form");

formulario.addEventListener("submit", async (event) => {
    event.preventDefault();

    const email = document.querySelector("#email").value;
    const senha = document.querySelector("#senha").value;

    try {
  const resposta = await fetch(`${API_URL}/api/usuarios/login`, {
    method: "POST",
    credentials: "include",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        email: email,
        senha: senha
    })
});

        if (!resposta.ok) {
            throw new Error("Erro ao fazer login");
        }

        const usuario = await resposta.json();

        const usuarioLogado = {
    id: usuario.id,
    nome: usuario.nome,
    email: usuario.email,
    tipo: usuario.tipo,
    empresa: usuario.empresa
};

localStorage.setItem("usuarioLogado", JSON.stringify(usuarioLogado));

if (usuario.tipo === "SCOMPTEC") {
    window.location.href = "home.html";
} else if (usuario.tipo === "CLIENTE") {
    window.location.href = "home.html";
}

    } catch (erro) {
        console.error(erro);
        alert("Não foi possível fazer login.");
    }
});