package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	/**
	 * Busca produtos junto com suas categorias de forma otimizada, evitando o problema do carregamento preguiçoso (LAZY).
	 * 
	 * Detalhes da query:
	 * 
	 * SELECT obj
	 * → Retorna objetos do tipo Product. "obj" é apenas um apelido dado à entidade na query.
	 * 
	 * FROM Product obj
	 * → Indica que estamos consultando a entidade Product (não a tabela diretamente).
	 * 
	 * JOIN FETCH obj.categories
	 * → Faz o JOIN com a lista de categorias do produto e usa FETCH para carregar tudo de uma vez só
	 *   (isso evita que o Hibernate faça uma consulta separada para cada produto ao acessar getCategories()).
	 * 
	 * WHERE obj IN :products
	 * → Filtra apenas os produtos que estão na lista passada como parâmetro.
	 *   Ou seja, carrega as categorias apenas desses produtos.
	 */
	
	@Query("SELECT obj FROM Product obj JOIN FETCH obj.categories WHERE obj IN :products")
	List<Product> findProductsCategories(List<Product> products);
}
