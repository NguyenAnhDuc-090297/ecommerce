package com.securemetric.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
//import java.util.List;
import java.util.Optional;

import com.securemetric.util.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.securemetric.entity.Product;
import com.securemetric.service.ProductService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

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
    public String show(Model model) {

        return pageView(model, 1, "name", "asc");
    }

    @GetMapping("/product/{pageNo}")
    public String pageView(Model model, @PathVariable("pageNo") int pageNo,
						   @Param("sortField") String sortField,
						   @Param("sortDirection") String sortDirection){
        Page<Product> page = productService.getAllProducts(pageNo, sortField, sortDirection);
        List<Product> productList = page.getContent();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirection", sortDirection);
		model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "des" : "asc");

        model.addAttribute("productList", productList);
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






