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
	
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
//	public List<Product> getAllProducts(){
//		return productRepository.findAll();
//	}

	public Page<Product> getAllProducts(int pageNo, String sortField, String sortDirection){
		int pageSize = 3;
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize,
				sortDirection.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		return productRepository.findAll(pageable);
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
	
	public Product updateUser(Product product) {
		return productRepository.save(product);
	}
	
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}
