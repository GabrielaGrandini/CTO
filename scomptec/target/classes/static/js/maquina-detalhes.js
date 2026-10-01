const parametros = new URLSearchParams(window.location.search);
const id = parametros.get("id");

if (!id) {
    window.location.href = "home.html";
}

async function carregarMaquina() {

    try {

        const resposta = await fetch(
    `${API_URL}/api/maquinas/${id}`,
    {
        credentials: "include"
    }
);

        if (!resposta.ok) {
            throw new Error("Erro ao buscar máquina");
        }

        const maquina = await resposta.json();

        document.querySelector("#empresaMaquina").textContent =
            maquina.empresa.nome;

        document.querySelector("#nomeMaquina").textContent =
            maquina.nome;

        document.querySelector("#marcaMaquina").textContent =
            maquina.marca;

        document.querySelector("#modeloMaquina").textContent =
            maquina.modelo;

        document.querySelector("#identificacaoMaquina").textContent =
            maquina.identificacao;

        document.querySelector("#setorMaquina").textContent =
            maquina.setor;

        document.querySelector("#linhaMaquina").textContent =
            maquina.linha;

           const respostaLeituras = await fetch(
    `${API_URL}/api/leituras/maquina/${id}`,
    {
        credentials: "include"
    }
);

if (!respostaLeituras.ok) {
    throw new Error("Erro ao buscar leituras");
}

const leituras = await respostaLeituras.json();

if (leituras.length > 0) {

    const ultimaLeitura = leituras[0];

    document.querySelector("#temperaturaMaquina").textContent =
        `${ultimaLeitura.temperatura} °C`;

    document.querySelector("#vibracaoMaquina").textContent =
        ultimaLeitura.vibracao;

    document.querySelector("#statusMaquina").textContent =
        "Dados recebidos";

    document.querySelector("#statusMaquina").className =
        "px-3 py-1 rounded-full text-xs font-semibold bg-green-100 text-green-700";
}


    } catch (erro) { console.error("ERRO COMPLETO:", erro); }
}

carregarMaquina();