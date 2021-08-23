package com.securemetric.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.securemetric.entity.Product;
import com.securemetric.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	private static int pageSize = 3;

	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	public Product updateProduct(Product product) {
		return productRepository.save(product);
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	public Optional<Product> getProductById(Long id){
		return productRepository.findById(id);
	}

	public boolean checkProductExistedOrNot(Long id) {
		if(productRepository.existsById(id)) {
			return true;
		}
		return false;
	}

	public Page<Product> getProductsPage(int pageNo){
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return productRepository.findAll(pageable);
	}

	public Page<Product> getProductsPageAndSort(int page, String sortField, String sortDirection){
		Pageable pageable = PageRequest.of(page - 1, pageSize,
				sortDirection.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());

		return productRepository.findAll(pageable);
	}

	public Page<Product> searchProductPage(int page, String keyword){
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return productRepository.searchProduct(keyword, pageable);
	}

	public Page<Product> searchProductAndPageSort(int page, String sortField, String sortDirection, String keyword){
		Pageable pageable = PageRequest.of(page - 1, pageSize,
				sortDirection.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		if(keyword != null) {
			return productRepository.searchProduct(keyword, pageable);
		} else {
			return productRepository.findAll(pageable);
		}
	}
}
