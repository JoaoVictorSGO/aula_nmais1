package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> find(PageRequest pageRequest) {
		Page<Product> page = repository.findAll(pageRequest);  // 1ª busca: apenas os produtos (sem categorias)
		/**
		 * Essa segunda linha evita o problema do carregamento "preguiçoso" (LAZY).
		 * 
		 * Quando fazemos a primeira busca, a JPA carrega apenas os produtos.
		 * Mas, como o relacionamento com categorias é LAZY, ao acessar p.getCategories()
		 * mais tarde, a JPA faria uma nova query para cada produto — gerando o problema do N+1.
		 * 
		 * Aqui, usamos uma query customizada com JOIN FETCH que carrega todas as categorias 
		 * de todos os produtos de uma vez só. E como os produtos já estão em memória (o mesmo objeto),
		 * a JPA aproveita e associa as categorias sem fazer nenhuma nova query depois.
		 */
		repository.findProductsCategories(page.getContent()); // Ele faz uma busca de uma vez que retorna todas as catergorias
		return page.map(x -> new ProductDTO(x));
	}
}
