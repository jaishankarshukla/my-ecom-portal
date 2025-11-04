package com.my.poc.myecom.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.my.poc.myecom.model.Product;
import com.my.poc.myecom.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

		@Autowired
		private ProductService productService ;
		
		@GetMapping("/products")
		public ResponseEntity<List<Product>>  getAllProducts(){
			
			List<Product> allProds = productService.getAllProducts();
			System.out.println("In Controller GetAllProducts : " + allProds.size());	
			//allProds.stream().forEach(p -> System.out.println(p));
			ResponseEntity<List<Product>>  responseEntity = new ResponseEntity<List<Product>>(allProds, HttpStatus.ACCEPTED);
			return responseEntity;
		}
		
		// @GetMapping("/products/{id}")
		// public ResponseEntity<Product>  getProductById(@PathVariable int id){
			
		// 	Product product = productService.getProductById(id);
		// 	System.out.println("In Controller getProductById : " + product.getId());	
		// 	//ResponseEntity<Product>  responseEntity = new ResponseEntity<Product>(product, HttpStatus.OK);
			
		// 	if (product.getId() >0)
		// 		return new ResponseEntity<Product>(product, HttpStatus.OK);
		// 	else
		// 		return new ResponseEntity<Product>(product, HttpStatus.NOT_FOUND);
		// }


		
		@PostMapping("/product")
		public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
			Product storedProd = null;
			
			try {
					productService.addOrUpdateProduct(product, imageFile);
					return new ResponseEntity<>(storedProd, HttpStatus.CREATED);
			}
			catch(IOException ex)
			{
				return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		@GetMapping("/product/{id}")
		public ResponseEntity<Product> getProductById(@PathVariable int id) {
			Product product = productService.getProductById(id);
			if (product.getId() > 0) {
				return new ResponseEntity<>(product, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}

		@GetMapping("/product/{productId}/image")
		public ResponseEntity<byte[]> getProductImage(@PathVariable int productId) {
			Product product = productService.getProductById(productId);
			if (product.getId() > 0) {
				return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		
		@PutMapping("/product/{id}")
		public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile){
			Product updatedProd=null;
			
			try {
				updatedProd=productService.addOrUpdateProduct(product, imageFile);
				//TODO: Return Updated Product, if needed.
				return new ResponseEntity<>("Updated Successful", HttpStatus.OK);
			}
			catch(IOException exp) {
				return new ResponseEntity<>(exp.getMessage(), HttpStatus.BAD_REQUEST);
			}
		
		}
		
		@DeleteMapping("/product/{id}")
		public ResponseEntity<String> deleteProduct(@PathVariable int id){
			Product prod = productService.getProductById(id);
			if (prod!=null) {
				productService.deleteProduct(id);
				return new ResponseEntity<>("Product Deleted", HttpStatus.OK);
			}
			else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		@GetMapping("/products/search")
		public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
			System.out.println("Search Products : Searching keyword = " + keyword);
			
			List<Product>  searchRes = productService.searchProducts(keyword);
			//Print the ids of products for debugging
			searchRes.stream().forEach(prod -> System.out.println(prod.getId()));
			
			return new ResponseEntity<>(searchRes, HttpStatus.OK);
		}

}
