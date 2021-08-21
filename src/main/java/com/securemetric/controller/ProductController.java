package com.securemetric.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.securemetric.entity.Product;
import com.securemetric.model.MessageProduct;
import com.securemetric.service.ProductService;
import com.securemetric.util.FileUploadUtil;

@RestController
@RequestMapping("/product")
public class ProductController {

//	@Value("${uploadDir}")
//	private String uploadFolder;
	
	@Autowired
	private ProductService productService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());



	@GetMapping("/getproductbyid/{id}")
	public Optional<Product> getProductById(@PathVariable("id") Long id) {
		Optional<Product> product = productService.getProductById(id);
		if (product.isPresent()) {
			product.get();
			return product;
		} else {
			return null;
		}
	}

	@PostMapping("/createproduct")
	public ResponseEntity<MessageProduct> createProduct(@RequestParam("id") Long id,
														@RequestParam("name") String name,
														@RequestParam("description") String description,
														@RequestParam("price") int price,
														@RequestParam("image") MultipartFile multipartFile)
			throws IOException {
		try{
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			Product product = new Product();
			product.setId(id);
			product.setName(name);
			product.setDescription(description);
			product.setPrice(price);
			product.setImage(fileName);

			Date createDate = new Date();
			product.setCreateDate(createDate);

			Product newProduct = productService.saveProduct(product);

			logger.info("Save Product Successfully.");

			String uploadDirectory = "product-images/" + newProduct.getId();
			FileUploadUtil.saveFile(uploadDirectory, fileName, multipartFile);

			return new ResponseEntity<MessageProduct>(new MessageProduct("",
					Arrays.asList(newProduct), ""), HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<MessageProduct>(new MessageProduct("",
					null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@PostMapping("/createproductbymodel")
//	public ResponseEntity<MessageProduct> createProduct(@ModelAttribute("product") Product product,
//					@RequestParam("image") MultipartFile multipartFile) throws IOException {
//		try{
//			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//
//			product.setImage(fileName);
//
//			Date createDate = new Date();
//			product.setCreateDate(createDate);
//
//			Product newProduct = productService.saveProduct(product);
//
//			logger.info("Save Product Successfully.");
//
//			String uploadDirectory = "product-images/" + newProduct.getId();
//			FileUploadUtil.saveFile(uploadDirectory, fileName, multipartFile);
//
//			return new ResponseEntity<MessageProduct>(new MessageProduct("",
//					Arrays.asList(newProduct), ""), HttpStatus.OK);
//		} catch (Exception e){
//			return new ResponseEntity<MessageProduct>(new MessageProduct("",
//					null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
//	@PostMapping("/createproductFalse")
//	public ResponseEntity<MessageProduct> createProduct(@RequestParam("name") String name, @RequestParam("price") float price,
//					@RequestParam("description") String description, Model model, HttpServletRequest request,
//					@RequestParam("image") MultipartFile file){
//	
//		try {
//			String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
//			logger.info("Upload Directory: " + uploadDirectory);
//			String fileName = file.getOriginalFilename();
//			String filePath = Paths.get(uploadDirectory, fileName).toString();
//			logger.info("File name: " + fileName);
//			
//			if(fileName == null || fileName.contains("..")) {
//				model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence.");
//				return new ResponseEntity<MessageProduct>(new MessageProduct("Sorry! Filename contains invalid path sequence", 
//						null, ""), HttpStatus.BAD_REQUEST);
//			}
//			String[] names = name.split(",");
//			String[] descriptions = description.split(",");
//			Date createDate = new Date();
//			logger.info("Name: " + names[0] + " " + filePath);
//			logger.info("Description: " + descriptions[0]);
//			logger.info("Price: " + price);
//			
//			try {
//				File dir = new File(uploadDirectory);
//				if(!dir.exists()) {
//					logger.info("Folder Created");
//					dir.mkdirs();
//				}
//				
//				// Save the file locally
//				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
//				stream.write(file.getBytes());
//				stream.close();
//			} catch (Exception e) {
//				logger.error("in catch " + e);
//				e.printStackTrace();
//			}
//			
//			//byte[] imageData = file.getBytes();
//			Product product = new Product();
//			product.setName(names[0]);
//			//product.setImage(imageData);
//			product.setDescription(descriptions[0]);
//			product.setPrice(price);
//			product.setCreateDate(createDate);
//			productService.saveProduct(product);
//			
//			logger.info("HttpStatus === " + new ResponseEntity<>(HttpStatus.OK));
//			
//			return new ResponseEntity<MessageProduct>(new MessageProduct("Product created with file" + fileName, 
//					Arrays.asList(product), ""), HttpStatus.OK);
//		} catch (Exception e) {
//			logger.error("Exception " + e);
//			return new ResponseEntity<MessageProduct>(new MessageProduct("", null, e.getMessage()), 
//					HttpStatus.BAD_REQUEST);
//		}
//	}
	
	// To display image - VIEW
//	@GetMapping("/display/{id}")
//	public void showProduct(@PathVariable("id") Long id, HttpServletResponse response, Optional<Product> product) 
//			throws IOException {
//		
//		logger.info("id:: " + id);
//		product = productService.getProductById(id);
//		response.setContentType("product/jpeg, product/jpg, product/png, product/gif");
//		//response.getOutputStream().write(product.get().getImage());
//		response.getOutputStream().close();
//	}
	
	// Update product
	@PutMapping("/updateproduct")
	public ResponseEntity<MessageProduct> updateProductById(@RequestParam("id") Long id,
															@RequestParam("name") String name,
															@RequestParam("description") String description,
															@RequestParam("price") int price,
															@RequestParam("image") MultipartFile multipartFile){
		try {
			// Check product exist or not
			if(productService.checkProductExistedOrNot(id)) {
				Product productUpdate = productService.getProductById(id).get();
				productUpdate.setId(id);
				productUpdate.setName(name);
				productUpdate.setDescription(description);
				productUpdate.setPrice(price);

				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				productUpdate.setImage(fileName);

				Date date = new Date();
				productUpdate.setCreateDate(date);

				// Save changes
				productService.saveProduct(productUpdate);

				logger.info("Update Product Successfully.");
				String uploadDirectory = "product-images/" + productUpdate.getId();
				FileUploadUtil.saveFile(uploadDirectory, fileName, multipartFile);

				return new ResponseEntity<MessageProduct>(new MessageProduct("Update Successfully", 
						Arrays.asList(productUpdate), ""), HttpStatus.OK);
			} else {
				return new ResponseEntity<MessageProduct>(new MessageProduct("", null,
						""), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<MessageProduct>(new MessageProduct("Fail", null,
					e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteproduct/{id}")
	public ResponseEntity<MessageProduct> deleteProductById(@PathVariable Long id){
		try {
			// Check product exist or not
			if(productService.checkProductExistedOrNot(id)) {
				productService.deleteProduct(id);
				return new ResponseEntity<MessageProduct>(new MessageProduct("Delete Successfully", 
						null, ""), HttpStatus.OK);
			} else {
				return new ResponseEntity<MessageProduct>(new MessageProduct("Not found id= " + id, 
						null, ""), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<MessageProduct>(new MessageProduct("Failure", null,
					e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}




