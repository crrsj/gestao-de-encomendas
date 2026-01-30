package br.com.entregadores.service;

import br.com.entregadores.config.ViaCepConfig;
import br.com.entregadores.dto.ClienteDTO;
import br.com.entregadores.entity.Cliente;
import br.com.entregadores.excessoes.ClienteNaoEncontrado;
import br.com.entregadores.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ViaCepConfig viaCepConfig;
    private final ModelMapper modelMapper;

    public ClienteDTO salvarCliente(ClienteDTO clienteDTO) {

        ClienteDTO dadosEndereco = viaCepConfig.buscarEnderecoPor(clienteDTO.getCep());
        clienteDTO.setLogradouro(dadosEndereco.getLogradouro());
        clienteDTO.setBairro(dadosEndereco.getBairro());
        clienteDTO.setLocalidade(dadosEndereco.getLocalidade());
        clienteDTO.setUf(dadosEndereco.getUf());
        clienteDTO.setEstado(dadosEndereco.getEstado());
        clienteDTO.setRegiao(dadosEndereco.getRegiao());
        var cliente = modelMapper.map(clienteDTO, Cliente.class);
        var clienteSalvo = clienteRepository.save(cliente);
        return modelMapper.map(clienteSalvo, ClienteDTO.class);
    }
    public Page<ClienteDTO> listarClientes(Pageable pageable){
        return clienteRepository.findAll(pageable)
                .map(cliente->modelMapper
                .map(cliente, ClienteDTO.class));
    }

    public ClienteDTO buscarClientePorId(Long id){
        var cliente = clienteRepository.findById(id)
                .orElseThrow(()-> new ClienteNaoEncontrado("Cliente não encontrado!"));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Transactional
    public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteDTO){
        var cliente = clienteRepository.findById(id).orElseThrow(()-> new ClienteNaoEncontrado("Cliente não encontrado!"));
        modelMapper.map(clienteDTO,cliente);
        var clienteAtualizado = clienteRepository.save(cliente);
        return modelMapper.map(clienteAtualizado, ClienteDTO.class);
    }

    public ClienteDTO buscarPorRg(String rg){
        return clienteRepository.findByRg(rg)
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .orElseThrow(() -> new ClienteNaoEncontrado("Cliente não encontrado com o RG: " + rg));
    }

    public Page<ClienteDTO> buscarPorRegiao(String regiao, Pageable pageable){
        Page<Cliente> clientesPage = clienteRepository.findByRegiao(regiao, pageable);
        return clientesPage.map(cliente -> modelMapper.map(cliente, ClienteDTO.class));
    }

    public void excluirCliente(Long id){
        var cliente = clienteRepository.findById(id).orElseThrow(()-> new ClienteNaoEncontrado("Cliente não encontrado!"));
        clienteRepository.delete(cliente);
    }
}
