package br.com.entregadores.service;

import br.com.entregadores.dto.EncomendaDTO;
import br.com.entregadores.entity.Encomenda;
import br.com.entregadores.excessoes.ClienteNaoEncontrado;
import br.com.entregadores.excessoes.EncomendaNaoEncontrada;
import br.com.entregadores.repository.ClienteRepository;
import br.com.entregadores.repository.EncomendaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncomendaService {

    private final ModelMapper modelMapper;
    private final ClienteRepository clienteRepository;
    private final EncomendaRepository encomendaRepository;

    public EncomendaDTO salvarEncomenda(Long clienteId,EncomendaDTO encomendaDTO){
        var cliente = clienteRepository.findById(clienteId)
                .orElseThrow(()-> new ClienteNaoEncontrado("Cliente não encontrado!"));
        var encomenda = modelMapper.map(encomendaDTO, Encomenda.class);
        encomenda.setCliente(cliente);
        var encomendaSalva = encomendaRepository.save(encomenda);
        return modelMapper.map(encomendaSalva,EncomendaDTO.class);
    }

    public Page<EncomendaDTO>listarEncomendas(Pageable pageable){
        return encomendaRepository.findAll(pageable)
                .map(encomenda->modelMapper.map(encomenda,EncomendaDTO.class));
    }

    public EncomendaDTO buscarEncomendaPorId(Long id){
        var encomenda = encomendaRepository.findById(id)
                .orElseThrow(()-> new EncomendaNaoEncontrada("Encomenda não encontrada."));
        return modelMapper.map(encomenda, EncomendaDTO.class);
    }

    @Transactional
    public EncomendaDTO atualizarEncomenda(Long id, EncomendaDTO encomendaDTO){
        var encomenda = encomendaRepository.findById(id)
                .orElseThrow(()-> new EncomendaNaoEncontrada("Encomenda não encontrada."));
        modelMapper.map(encomendaDTO,encomenda);
        var encomendaSalva = encomendaRepository.save(encomenda);
        return modelMapper.map(encomendaSalva, EncomendaDTO.class);
    }

    public void excluirEncomenda(Long id){
        var encomenda = encomendaRepository.findById(id)
                .orElseThrow(()-> new EncomendaNaoEncontrada("Encomenda não encontrada."));
        encomendaRepository.delete(encomenda);
    }
}
