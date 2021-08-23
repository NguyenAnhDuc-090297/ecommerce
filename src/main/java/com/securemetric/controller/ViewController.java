package com.securemetric.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.securemetric.entity.Product;
import com.securemetric.service.ProductService;

@Controller
public class ViewController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = {"/", "/home"})
	public String addProductPage() {
		return "index";
	}

    @GetMapping("/product")
    public String productPageandSort(Model model,
						   @RequestParam(value = "page", required = false) Integer page,
						   @RequestParam(value = "sortField", required = false) String sortField,
						   @RequestParam(value = "sortDirection", required = false) String sortDirection){

		Page<Product> pageOfProduct = null;
		List<Product> productList = null;
		int totalPages = 0;

		if(page == null) {
			page = 1;
			pageOfProduct = productService.getProductsPage(page);
			productList = pageOfProduct.getContent();
			totalPages = pageOfProduct.getTotalPages();
		} else {
			if (sortField == null && sortDirection == null) {
				pageOfProduct = productService.getProductsPage(page);
				productList = pageOfProduct.getContent();
				totalPages = pageOfProduct.getTotalPages();
			} else {
				pageOfProduct = productService.getProductsPageAndSort(page, sortField, sortDirection);
				productList = pageOfProduct.getContent();
				totalPages = pageOfProduct.getTotalPages();
				model.addAttribute("sortField", sortField);
				model.addAttribute("sortDirection", sortDirection);
				model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "des" : "asc");
			}
		}

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", pageOfProduct.getTotalElements());
        model.addAttribute("productList", productList);

        return "product";
    }

	@GetMapping("/product/search")
	public String searchPageAndSort(Model model,
							 @RequestParam(value = "keyword") String keyword,
							 @RequestParam(value = "page", required = false) Integer page,
							 @RequestParam(value = "sortField", required = false) String sortField,
							 @RequestParam(value = "sortDirection", required = false) String sortDirection){

		Page<Product> pageOfProduct = null;
		List<Product> productList = null;
		int totalPages = 0;

		if(page == null) {
			page = 1;
			pageOfProduct = productService.searchProductPage(page, keyword);
			productList = pageOfProduct.getContent();
			totalPages = pageOfProduct.getTotalPages();
		} else {
			if(sortField == null && sortDirection == null) {
				pageOfProduct = productService.searchProductPage(page, keyword);
				productList = pageOfProduct.getContent();
				totalPages = pageOfProduct.getTotalPages();
			} else {
				pageOfProduct = productService.searchProductAndPageSort(page, sortField, sortDirection, keyword);
				productList = pageOfProduct.getContent();
				totalPages = pageOfProduct.getTotalPages();
				model.addAttribute("sortField", sortField);
				model.addAttribute("sortDirection", sortDirection);
				model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "des" : "asc");
			}
		}

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", pageOfProduct.getTotalElements());
		model.addAttribute("keyword", keyword);
		model.addAttribute("productList", productList);
		model.addAttribute("search", "search");

		return "product";
	}

	@GetMapping("/productdetails")
	public String showProductDetails(@RequestParam("id") Long id, Optional<Product> product, Model model) {
		try {
			logger.info("Id :: " + id);
			if(id != 0) {
				product = productService.getProductById(id);
				
				logger.info("product :: " + product);
				if(product.isPresent()) {
					model.addAttribute("id", product.get().getId());
					model.addAttribute("name", product.get().getName());
					model.addAttribute("description", product.get().getDescription());			
					model.addAttribute("price", product.get().getPrice());
					
					
					return "productDetails";
				}
				return "redirect:/home";
			} 
			return "redirect:/home";
		} catch (Exception e) {
			logger.error("" + e);
			return "redirect:/home";
		}
	}
}






