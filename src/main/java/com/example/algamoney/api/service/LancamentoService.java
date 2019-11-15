package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	private Lancamento lancamento = new Lancamento();
	
	private Pessoa pessoa = new Pessoa();

	public Lancamento savar(Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		this.pessoa = pessoa.get();
		if (pessoa == null || this.pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}

	
	public Lancamento buscar(Long codigo) {
		Optional<Lancamento> lancamentoPesquisado = lancamentoRepository.findById(codigo);
		
		if (lancamentoPesquisado.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return this.lancamento = lancamentoPesquisado.get();
	}


	public void delete(Long codigo) {
		lancamentoRepository.deleteById(codigo);
		
	}

}
