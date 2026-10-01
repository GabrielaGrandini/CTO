const selectEmpresa = document.querySelector("#empresa");

async function carregarEmpresas() {

    try {

        const resposta = await fetch(`${API_URL}/api/empresas`, {
    credentials: "include"
});
        if (!resposta.ok) {
            throw new Error("Erro ao buscar empresas");
        }

        const empresas = await resposta.json();

        selectEmpresa.innerHTML = `
            <option value="">
                Selecione uma empresa
            </option>
        `;

        empresas.forEach(empresa => {

            const option = document.createElement("option");

            option.value = empresa.id;
            option.textContent = empresa.nome;

            selectEmpresa.appendChild(option);

        });

    } catch (erro) {

        console.error(erro);

        selectEmpresa.innerHTML = `
            <option value="">
                Não foi possível carregar as empresas
            </option>
        `;
    }
}


const formulario = document.querySelector("#formMaquina");

formulario.addEventListener("submit", async (event) => {

    event.preventDefault();

    const maquina = {

        nome: document.querySelector("#nome").value,
        marca: document.querySelector("#marca").value,
        modelo: document.querySelector("#modelo").value,
        setor: document.querySelector("#setor").value,
        linha: document.querySelector("#linha").value,
        identificacao: document.querySelector("#identificacao").value,

        empresa: {
            id: Number(document.querySelector("#empresa").value)
        }

    };


    try {

        const resposta = await fetch(`${API_URL}/api/maquinas`, {

    method: "POST",

    credentials: "include",

    headers: {
        "Content-Type": "application/json"
    },

    body: JSON.stringify(maquina)

});

        if (!resposta.ok) {
            throw new Error("Erro ao cadastrar máquina");
        }


        const maquinaCriada = await resposta.json();

        console.log("Máquina cadastrada:", maquinaCriada);

        alert("Máquina cadastrada com sucesso!");

        window.location.href = "home.html";


    } catch (erro) {

        console.error(erro);

        alert("Não foi possível cadastrar a máquina.");

    }

});


carregarEmpresas();