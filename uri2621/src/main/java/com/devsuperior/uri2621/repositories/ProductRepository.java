package com.devsuperior.uri2621.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsuperior.uri2621.dto.ProductMinDTO;
import com.devsuperior.uri2621.entities.Product;
import com.devsuperior.uri2621.projections.ProductMinProjection;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query(value = "SELECT products.name FROM products "
				 + "INNER JOIN providers ON products.id_providers = providers.id "
				 + "WHERE "
				 + "products.amount BETWEEN :amountMin AND :amountMax AND "
				 + "UPPER(providers.name) LIKE CONCAT(:beginProviderName, '%')", nativeQuery = true)
	List<ProductMinProjection> search1(Integer amountMin, Integer amountMax, String beginProviderName);
	
	@Query(value = "SELECT new com.devsuperior.uri2621.dto.ProductMinDTO(obj.name) FROM Product obj "
				 + "WHERE "
				 + "obj.amount BETWEEN :amountMin AND :amountMax AND "
				 + "UPPER(obj.provider.name) LIKE :beginProviderName%")
	List<ProductMinDTO> search2(Integer amountMin, Integer amountMax, String beginProviderName);	
}