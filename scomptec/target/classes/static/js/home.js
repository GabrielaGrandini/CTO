const usuario = JSON.parse(
    localStorage.getItem("usuarioLogado")
);

if (!usuario) {
    window.location.href = "index.html";
}

document.querySelector("#nomeUsuario").textContent =
    usuario.nome;

document.querySelector("#tipoUsuario").textContent =
    usuario.tipo;

if (usuario.tipo !== "SCOMPTEC") {

    const linksAdministrativos = menuPrincipal.querySelectorAll(
        'a[href="cadastro-empresa.html"], a[href="cadastro-maquina.html"], a[href="cadastro-usuario.html"]'
    );

    linksAdministrativos.forEach(link => {
        link.remove();
    });
}

const menuPrincipalMobile = document.querySelector("#menuPrincipalMobile");

menuPrincipalMobile.innerHTML = menuPrincipal.innerHTML;

if (usuario.tipo !== "SCOMPTEC") {
    const linksAdministrativos = menuPrincipal.querySelectorAll(
    'a[href="cadastro-empresa.html"], a[href="cadastro-maquina.html"], a[href="cadastro-usuario.html"]'
);

    linksAdministrativos.forEach(link => {
        link.remove();
    });
}

async function carregarMaquinas() {

    let url = `${API_URL}/api/maquinas`;

if (usuario.tipo === "CLIENTE") {
    url = `${API_URL}/api/maquinas/empresa/${usuario.empresa.id}`;
}

    try {
        const resposta = await fetch(url, {
            credentials: "include"
        });

        if (!resposta.ok) {
            throw new Error("Erro ao buscar máquinas");
        }

        const maquinas = await resposta.json();

        mostrarMaquinas(maquinas);

    } catch (erro) {
        console.error(erro);
    }
}


function mostrarMaquinas(maquinas) {

    const lista = document.querySelector("#listaMaquinas");

    lista.innerHTML = "";

    if (maquinas.length === 0) {
        lista.innerHTML = `
            <div class="col-span-full text-center py-10">
                <p class="text-gray-500">
                    Nenhuma máquina encontrada.
                </p>
            </div>
        `;

        return;
    }

    maquinas.forEach(maquina => {

        const card = document.createElement("div");

        card.className = `
            bg-white rounded-2xl shadow-sm border border-gray-200
            p-6 hover:shadow-md transition
        `;

        card.innerHTML = `
            <div class="flex items-start justify-between gap-4">

                <div>
                    <p class="text-sm text-gray-500">
                        ${maquina.empresa.nome}
                    </p>

                    <h3 class="text-xl font-bold text-gray-900 mt-1">
                        ${maquina.nome}
                    </h3>
                </div>

                <span class="px-3 py-1 rounded-full text-xs font-semibold
                             bg-green-100 text-green-700">
                    Monitorada
                </span>

            </div>

            <div class="mt-6 space-y-3 text-sm">

                <div class="grid grid-cols-[110px_1fr] gap-3 items-center">
    <span class="text-gray-500">Marca</span>
    <span class="font-medium text-right">${maquina.marca}</span>
</div>

<div class="grid grid-cols-[110px_1fr] gap-3 items-center">
    <span class="text-gray-500">Modelo</span>
    <span class="font-medium text-right">${maquina.modelo}</span>
</div>

<div class="grid grid-cols-[110px_1fr] gap-3 items-center">
    <span class="text-gray-500">Setor</span>
    <span class="font-medium text-right">${maquina.setor}</span>
</div>

<div class="grid grid-cols-[110px_1fr] gap-3 items-center">
    <span class="text-gray-500">Linha</span>
    <span class="font-medium text-right">${maquina.linha}</span>
</div>

<div class="grid grid-cols-[110px_1fr] gap-3 items-center">
    <span class="text-gray-500">Identificação</span>
    <span class="font-medium text-right">${maquina.identificacao}</span>
</div>

            </div>

            <button
                class="w-full mt-6 py-3 rounded-lg
                       bg-green-700 text-white font-semibold
                       hover:bg-green-800 transition">
                Ver máquina
            </button>
        `;

        const botao = card.querySelector("button");

botao.addEventListener("click", () => {
    window.location.href = `maquina.html?id=${maquina.id}`;
});

        lista.appendChild(card);
    });
}

const abrirMenu = document.querySelector("#abrirMenu");
const menuMobile = document.querySelector("#menuMobile");

abrirMenu.addEventListener("click", () => {
    menuMobile.classList.remove("hidden");
});

menuMobile.addEventListener("click", (event) => {
    if (event.target === menuMobile) {
        menuMobile.classList.add("hidden");
    }
});

carregarMaquinas();