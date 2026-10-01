const formulario = document.querySelector("#formEmpresa");

formulario.addEventListener("submit", async (event) => {
    event.preventDefault();

    const empresa = {
        nome: document.querySelector("#nome").value,
        cnpj: document.querySelector("#cnpj").value,
        email: document.querySelector("#email").value,
        telefone: document.querySelector("#telefone").value,
        endereco: document.querySelector("#endereco").value
    };

    try {
        const resposta = await fetch(`${API_URL}/api/empresas`, {
    method: "POST",
    credentials: "include",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify(empresa)
});

        if (!resposta.ok) {
            throw new Error("Erro ao cadastrar empresa");
        }

        const empresaCriada = await resposta.json();

        console.log("Empresa cadastrada:", empresaCriada);

        alert("Empresa cadastrada com sucesso!");

        window.location.href = "home.html";

    } catch (erro) {
        console.error(erro);
        alert("Não foi possível cadastrar a empresa.");
    }
});