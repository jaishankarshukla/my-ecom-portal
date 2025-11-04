package com.my.poc.myecom.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.my.poc.myecom.model.Product;
import com.my.poc.myecom.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository  prodRepo;

	
	public List<Product> getAllProducts() {
		List<Product>  allProds = prodRepo.findAll();
		System.out.println("In Service GetAllProducts count: " + allProds.size());
		return allProds;
	}


	public Product getProductById(int id) {
		
		return prodRepo.findById(id).orElse(new Product(-1));
	}


	public Product addProduct(Product product, MultipartFile image) throws IOException {
		product.setImageName(image.getOriginalFilename());
		product.setImageType(image.getContentType());
		product.setImageData(image.getBytes());
		return prodRepo.save(product);
	}


	public Product addOrUpdateProduct(Product product, MultipartFile image) throws IOException {
		product.setImageName(image.getOriginalFilename());
		product.setImageType(image.getContentType());
		product.setImageData(image.getBytes());
		return prodRepo.save(product);
	}

	public void deleteProduct(int id) {
		prodRepo.deleteById(id);
		
	}


	public List<Product> searchProducts(String keyword) {
		return prodRepo.searchProducts(keyword);
	}
	


}
