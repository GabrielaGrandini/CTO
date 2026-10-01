const selectEmpresa = document.querySelector("#empresa");
const tipoUsuario = document.querySelector("#tipo");
const empresaContainer = document.querySelector("#empresaContainer");


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


tipoUsuario.addEventListener("change", () => {

    if (tipoUsuario.value === "SCOMPTEC") {

        empresaContainer.style.display = "none";
        selectEmpresa.removeAttribute("required");

    } else {

        empresaContainer.style.display = "block";
        selectEmpresa.setAttribute("required", "true");

    }

});


const formulario = document.querySelector("#formUsuario");


formulario.addEventListener("submit", async (event) => {

    event.preventDefault();

    const usuario = {

        nome: document.querySelector("#nome").value,
        email: document.querySelector("#email").value,
        senha: document.querySelector("#senha").value,
        tipo: document.querySelector("#tipo").value

    };


    if (usuario.tipo === "CLIENTE") {

        usuario.empresa = {
            id: Number(selectEmpresa.value)
        };

    }


    try {

        const resposta = await fetch(`${API_URL}/api/usuarios`, {

            method: "POST",

            credentials: "include",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(usuario)

        });


        if (!resposta.ok) {
            throw new Error("Erro ao cadastrar usuário");
        }


        const usuarioCriado = await resposta.json();

        console.log("Usuário cadastrado:", usuarioCriado);

        alert("Usuário cadastrado com sucesso!");

        window.location.href = "home.html";


    } catch (erro) {

        console.error(erro);

        alert("Não foi possível cadastrar o usuário.");

    }

});


carregarEmpresas();