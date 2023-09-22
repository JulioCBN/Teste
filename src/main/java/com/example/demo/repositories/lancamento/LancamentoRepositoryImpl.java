package com.example.demo.repositories.lancamento;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.demo.models.Lancamento;
import com.example.demo.repositories.filter.LancamentoFilter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criterio = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criterio.from(Lancamento.class);
				
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criterio.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criterio);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	private Long total(LancamentoFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
	}

	private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (filter.getDescricao() != null) 
			predicates.add(builder.like(builder.lower(root.get("descricao")), "%" + filter.getDescricao().toLowerCase() + "%"));
		
		if (filter.getDataVencimentoDe() != null) 
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"), filter.getDataVencimentoDe()));
		
		if (filter.getDataVencimentoAte() != null)
			predicates.add(builder.lessThanOrEqualTo(root.get("dataVencimento"), filter.getDataVencimentoAte()));
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
