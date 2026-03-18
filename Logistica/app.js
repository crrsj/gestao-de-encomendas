
document.getElementById('cep').addEventListener('blur', function(e) {
    const cep = e.target.value.replace(/\D/g, '');
    
    if (cep.length === 8) {
        
        fetch(`https://viacep.com.br/ws/${cep}/json/`)
            .then(response => response.json())
            .then(data => {
                if (!data.erro) {
                    document.getElementById('logradouro').value = data.logradouro;
                    document.getElementById('bairro').value = data.bairro;
                    document.getElementById('localidade').value = data.localidade + " / " + data.uf;
                    // O campo complemento o usuário digita manualmente
                }
            });
    }
});



const API_URL = "http://localhost:8080/api/clientes";

document.getElementById('formCliente').addEventListener('submit', function(e) {
    e.preventDefault(); 

  
    const formData = new FormData(this);
    const clienteData = Object.fromEntries(formData.entries());

   
    const logradouro = document.getElementById('logradouro').value;
    if (!logradouro || logradouro === "Buscando..." || logradouro === "CEP não encontrado!") {
        alert("⚠️ Por favor, insira um CEP válido antes de salvar.");
        return;
    }

  
    fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(clienteData)
    })
    .then(response => {
        if (!response.ok) {
            // Se o backend retornar erro (ex: CEP que o ViaCEP não reconheceu)
            throw new Error("Erro ao salvar: Verifique os dados ou o CEP.");
        }
        return response.json();
    })
    .then(data => {
        alert("✅ Cliente cadastrado com sucesso!");
        bootstrap.Modal.getInstance(document.getElementById('modalCliente')).hide();
        this.reset();
     
        
       listarClientes(paginaAtual);
    })
    .catch(error => {
        alert("❌ " + error.message);
    });
});


let paginaAtual = 0;


function listarClientes(pagina = 0) {
    paginaAtual = pagina;
    
    fetch(`http://localhost:8080/api/clientes?page=${pagina}&size=5&sort=nome,asc`)
        .then(res => res.json())
        .then(data => {
            renderizarTabela(data.content); // data.content contém a lista de clientes
            renderizarPaginacao(data);
        })
        .catch(err => console.error("Erro ao listar clientes:", err));
}


function renderizarTabela(clientes) {
    const tbody = document.querySelector('#tabelaClientes tbody');
    tbody.innerHTML = '';

    clientes.forEach(cliente => {
        tbody.innerHTML += `
            <tr>
                <td><span class="badge bg-secondary">${cliente.rg}</span></td>
                <td><strong>${cliente.nome}</strong><br><small class="text-muted">${cliente.telefone}</small></td>
                <td>${cliente.localidade} / ${cliente.uf}</td>
                <td><span class="badge bg-info text-dark">${cliente.regiao}</span></td>
                <td>               

                 <button class="btn btn-sm btn-dark" onclick="verDetalhesCliente(${cliente.id})" title="Ver Detalhes">
                      <i class="fas fa-eye"></i>
                 </button>

                    <button class="btn btn-sm btn-info text-white" onclick="prepararEdicao(${cliente.id})">
                     <i class="fas fa-edit"></i>
                   </button>
                    <button class="btn btn-sm btn-outline-danger" onclick="excluirCliente(${cliente.id})">
                        <i class="fas fa-trash"></i>
                    </button>

                    <button class="btn btn-sm btn-success" onclick="abrirModalEncomendaComCliente(${cliente.id})" title="Adicionar Encomenda">
                      <i class="fas fa-plus-square"></i>
                    </button>

                </td>
            </tr>
        `;
    });
}


function renderizarPaginacao(data) {
    const paginacaoDiv = document.getElementById('paginacao');
    paginacaoDiv.innerHTML = `
        <button class="btn btn-sm btn-light" ${data.first ? 'disabled' : ''} onclick="listarClientes(${paginaAtual - 1})">Anterior</button>
        <span class="mx-3">Página ${data.number + 1} de ${data.totalPages}</span>
        <button class="btn btn-sm btn-light" ${data.last ? 'disabled' : ''} onclick="listarClientes(${paginaAtual + 1})">Próximo</button>
    `;
}


document.addEventListener('DOMContentLoaded', () => {
    listarClientes();
});



function prepararEdicao(id) {
    fetch(`http://localhost:8080/api/clientes/${id}`)
        .then(res => res.json())
        .then(cliente => {
          
            document.getElementById('edit-id').value = cliente.id;
            document.getElementById('edit-nome').value = cliente.nome;
            document.getElementById('edit-telefone').value = cliente.telefone;
            document.getElementById('edit-complemento').value = cliente.complemento || '';

           
            const modalEdicao = new bootstrap.Modal(document.getElementById('modalEditarCliente'));
            modalEdicao.show();
        })
        .catch(err => alert("Erro ao carregar cliente: " + err.message));
}


function executarPatchCliente() {
    const id = document.getElementById('edit-id').value;
    
  
    const dadosAtualizados = {
        nome: document.getElementById('edit-nome').value,
        telefone: document.getElementById('edit-telefone').value,
        complemento: document.getElementById('edit-complemento').value
    };

    fetch(`http://localhost:8080/api/clientes/${id}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dadosAtualizados)
    })
    .then(response => {
        if (!response.ok) throw new Error("Falha ao atualizar cliente.");
        return response.json();
    })
    .then(data => {
        alert("✅ Cliente atualizado com sucesso!");
      
        const modalElement = document.getElementById('modalEditarCliente');
        const modalInstance = bootstrap.Modal.getInstance(modalElement);
        modalInstance.hide();
        
        
        listarClientes(paginaAtual);
    })
    .catch(err => {
        alert("❌ Erro: " + err.message);
    });
}

function verDetalhesCliente(id) {
    
    fetch(`http://localhost:8080/api/clientes/${id}`)
        .then(res => {
            if (!res.ok) throw new Error("Cliente não encontrado");
            return res.json();
        })
        .then(cliente => {
          
            document.getElementById('detalhe-nome').innerText = cliente.nome;
            document.getElementById('detalhe-rg').innerText = `RG: ${cliente.rg}`;
            document.getElementById('detalhe-telefone').innerText = cliente.telefone || 'Não informado';
            document.getElementById('detalhe-logradouro').innerText = cliente.logradouro;
            document.getElementById('detalhe-bairro-cidade').innerText = `${cliente.bairro}, ${cliente.localidade} - ${cliente.uf}`;
            document.getElementById('detalhe-cep').innerText = `CEP: ${cliente.cep}`;
            document.getElementById('detalhe-complemento').innerText = cliente.complemento ? `Obs: ${cliente.complemento}` : 'Sem complemento';
            document.getElementById('detalhe-regiao').innerText = cliente.regiao;

         
            const modal = new bootstrap.Modal(document.getElementById('modalDetalhesCliente'));
            modal.show();
        })
        .catch(err => alert("❌ Erro ao buscar detalhes: " + err.message));
}

function excluirCliente(id) {
 
    if (confirm("⚠️ Tem certeza que deseja excluir este cliente? Todas as encomendas vinculadas também serão removidas.")) {
        
        fetch(`http://localhost:8080/api/clientes/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert("✅ Cliente removido com sucesso!");
            
                listarClientes(paginaAtual); 
            } else {
                throw new Error("Não foi possível excluir o cliente. Verifique se ele possui dependências.");
            }
        })
        .catch(err => {
            alert("❌ Erro: " + err.message);
            console.error(err);
        });
    }


}


function buscarClientePorRg() {
    const rg = document.getElementById('inputBuscaRg').value;

    if (!rg) {
        listarClientes(0);
        return;
    }

    fetch(`http://localhost:8080/api/clientes/rg?rg=${rg}`)
        .then(response => {
            if (!response.ok) {
              
                throw new Error("Cliente com este RG não foi encontrado.");
            }
            return response.json();
        })
        .then(cliente => {
           
            renderizarTabela([cliente]); 
            
        
            document.getElementById('paginacao').innerHTML = 
                '<span class="text-muted small">Resultado da busca por RG</span>';
        })
        .catch(error => {
            alert("⚠️ " + error.message);
            document.getElementById('inputBuscaRg').value = "";
        });
}


function prepararNovoCadastroEncomenda() {
 
    fetch('http://localhost:8080/api/clientes?size=100')
        .then(res => res.json())
        .then(data => {
            const select = document.getElementById('selectClientes');
            select.innerHTML = '<option value="">Selecione o cliente para vincular...</option>';
            
            const clientes = data.content || data;
            clientes.forEach(c => {
                select.innerHTML += `<option value="${c.id}">${c.nome} (RG: ${c.rg})</option>`;
            });

           
            new bootstrap.Modal(document.getElementById('modalEncomenda')).show();
        });
}


function renderizarBadges(encomenda) {
 
    const coresEmpresa = {
        'AMAZON': 'bg-dark text-warning',       
        'SHOPEE': 'bg-danger text-white',      
        'MERCADO_LIVRE': 'bg-warning text-dark', 
        'ALIEXPRESS': 'bg-secondary text-white' 
    };


    const badgeStatus = encomenda.status === 'ENTREGUE' 
        ? 'bg-success' 
        : 'bg-warning text-dark';

    return {
        empresa: `<span class="badge ${coresEmpresa[encomenda.empresa]}">${encomenda.empresa.replace('_', ' ')}</span>`,
        status: `<span class="badge ${badgeStatus}">${encomenda.status}</span>`
    };
}

function abrirModalEncomendaComCliente(clienteId) {
   
    fetch('http://localhost:8080/api/clientes?size=1000')
        .then(res => res.json())
        .then(data => {
            const select = document.getElementById('selectClientes');
            const clientes = data.content || data;
            
            select.innerHTML = '<option value="">Selecione...</option>';
            clientes.forEach(c => {
                const selected = c.id === clienteId ? 'selected' : '';
                select.innerHTML += `<option value="${c.id}" ${selected}>${c.nome}</option>`;
            });

          
            new bootstrap.Modal(document.getElementById('modalEncomenda')).show();
        });
}


function salvarEncomenda(event) {
    event.preventDefault(); 

  
    const selectCliente = document.getElementById('selectClientes');
    const clienteId = selectCliente.value;

  
    if (!clienteId) {
        alert("⚠️ Por favor, selecione um cliente primeiro!");
        return;
    }

    
    const payload = {
        descricao: document.getElementById('descricao').value.trim() || null,
        dataEntrega: document.getElementById('dataEntrega').value,
        pacotes: parseInt(document.getElementById('pacotes').value),
        empresa: document.getElementById('empresa').value,
        status: document.getElementById('status').value
    };

  
    fetch(`http://localhost:8080/api/encomendas/${clienteId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
          
            return response.text().then(text => { throw new Error(text || "Erro ao salvar") });
        }
    })
    .then(data => {
        alert("✅ Encomenda registrada com sucesso!");
        
     
        document.getElementById('formEncomenda').reset();
        const modalElement = document.getElementById('modalEncomenda');
        const modalInstance = bootstrap.Modal.getInstance(modalElement);
        modalInstance.hide();

      
        if (typeof listarEncomendas === "function") {
            listarEncomendas(0);
        }
    })
    .catch(error => {
        console.error("Erro na requisição:", error);
        alert("❌ Falha ao salvar: " + error.message);
    });
}


document.getElementById('formEncomenda').addEventListener('submit', salvarEncomenda);



function formatarEmpresa(empresa) {
    const cores = {
        'AMAZON': 'bg-dark text-warning',
        'SHOPEE': 'bg-danger text-white',
        'MERCADO_LIVRE': 'bg-warning text-dark',
        'ALIEXPRESS': 'bg-secondary text-white'
    };
    return `<span class="badge ${cores[empresa] || 'bg-light'} shadow-sm">${empresa.replace('_', ' ')}</span>`;
}

function formatarStatus(status) {
    const cor = status === 'ENTREGUE' ? 'bg-success' : 'bg-warning text-dark';
    const icone = status === 'ENTREGUE' ? 'fa-check-circle' : 'fa-clock';
    return `<span class="badge ${cor}"><i class="fas ${icone} me-1"></i>${status}</span>`;
}


function listarEncomendas(pagina = 0) {
    fetch(`http://localhost:8080/api/encomendas?page=${pagina}&size=5&sort=id,desc`)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('corpoTabelaEncomendas');
            tbody.innerHTML = '';

            const encomendas = data.content || data;

            encomendas.forEach(enc => {
               
                const descricaoSegura = enc.descricao && enc.descricao.trim() !== "" 
                    ? enc.descricao 
                    : '<em class="text-muted small">Sem descrição informada</em>';

                tbody.innerHTML += `
                    <tr>
                        <td>#${enc.id}</td>
                        <td><strong>${enc.cliente.nome}</strong></td>
                        <td>${descricaoSegura}</td>
                        <td>${formatarEmpresa(enc.empresa)}</td>
                        <td>${formatarStatus(enc.status)}</td>
                        <td>${new Date(enc.dataEntrega).toLocaleDateString()}</td>
                        <td class="text-center">${enc.pacotes} vol.</td>
                        <td class="text-center">
                            <div class="btn-group">
                                <button class="btn btn-sm btn-outline-primary" onclick="atualizarStatus(${enc.id})" title="Alterar Status">
                                    <i class="fas fa-sync-alt"></i>
                                </button>
                                <button class="btn btn-sm btn-outline-danger" onclick="excluirEncomenda(${enc.id})" title="Excluir">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                `;
            });
            renderizarPaginacaoEncomendas(data);
        })
        .catch(err => console.error("Erro ao listar encomendas:", err));
}


function renderizarPaginacaoEncomendas(data) {
    const paginacaoDiv = document.getElementById('paginacaoEncomendas');
    if (!paginacaoDiv) return;

   
    if (data.totalPages <= 1) {
        paginacaoDiv.innerHTML = '<small class="text-muted">Total de ' + data.totalElements + ' encomenda(s)</small>';
        return;
    }

    paginacaoDiv.innerHTML = `
        <nav aria-label="Navegação de encomendas">
            <ul class="pagination pagination-sm mb-0">
                <li class="page-item ${data.first ? 'disabled' : ''}">
                    <button class="page-link" onclick="listarEncomendas(${data.number - 1})">Anterior</button>
                </li>
                <li class="page-item disabled">
                    <span class="page-link text-dark fw-bold">Página ${data.number + 1} de ${data.totalPages}</span>
                </li>
                <li class="page-item ${data.last ? 'disabled' : ''}">
                    <button class="page-link" onclick="listarEncomendas(${data.number + 1})">Próximo</button>
                </li>
            </ul>
        </nav>
    `;
}


function atualizarStatus(id) {
    fetch(`http://localhost:8080/api/encomendas/${id}`)
        .then(res => res.json())
        .then(enc => {
         
            document.getElementById('editId').value = enc.id;
            document.getElementById('editDescricao').value = enc.descricao || "";
            document.getElementById('editStatus').value = enc.status;
            document.getElementById('editEmpresa').value = enc.empresa;
            document.getElementById('editData').value = enc.dataEntrega;
            document.getElementById('editPacotes').value = enc.pacotes;

          
            const m = new bootstrap.Modal(document.getElementById('modalEditarEncomenda'));
            m.show();
        })
        .catch(err => alert("Erro ao buscar encomenda: " + err.message));
}


document.getElementById('formEditarEncomenda').addEventListener('submit', function(e) {
    e.preventDefault();

    const id = document.getElementById('editId').value;
    
    const dadosAtualizados = {
        descricao: document.getElementById('editDescricao').value,
        status: document.getElementById('editStatus').value,
        empresa: document.getElementById('editEmpresa').value,
        dataEntrega: document.getElementById('editData').value,
        pacotes: parseInt(document.getElementById('editPacotes').value)
    };

    fetch(`http://localhost:8080/api/encomendas/${id}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dadosAtualizados)
    })
    .then(res => {
        if (!res.ok) throw new Error("Falha ao atualizar");
        return res.json();
    })
    .then(() => {
        alert("✅ Encomenda atualizada!");
        bootstrap.Modal.getInstance(document.getElementById('modalEditarEncomenda')).hide();
        listarEncomendas(0); 
    })
    .catch(err => alert(err.message));
});


document.addEventListener('DOMContentLoaded', function() {
    listarEncomendas(0);
});

function excluirEncomenda(id) {
  
    if (confirm(`⚠️ Tem certeza que deseja excluir a encomenda #${id}?`)) {
        
       
        fetch(`http://localhost:8080/api/encomendas/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
             
                alert("📦 Encomenda removida com sucesso!");
                listarEncomendas(0); 
            } else {
               
                return response.text().then(text => { throw new Error(text) });
            }
        })
        .catch(error => {
            console.error("Erro ao excluir:", error);
            alert("❌ Não foi possível excluir a encomenda: " + error.message);
        });
    }
}
