package com.me.web.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.me.web.dao.ProductDAO;
import com.me.web.pojo.Product;

@Controller
@RequestMapping(value="/*")
public class HomeController {

	@RequestMapping(value="/")
	public String home() {
		return "home";
	}
	
    @RequestMapping("/about")
    public String about() {
        return "about";
    }
    
	@RequestMapping(value="/productList")
	public String getProducts(ModelMap map, ProductDAO productDao) {
		
		try {
		List<Product> products = productDao.getAllProducts();
		map.addAttribute("products", products);
		}catch(Exception e) {
			
		}
		return "productList";
	}
	
	@RequestMapping(value="/productList/viewProduct/{productId}")
	public String viewProduct(@PathVariable String productId, ModelMap map, ProductDAO productDao) {
		try {
		Product product = productDao.getProductById(Integer.parseInt(productId));
		map.addAttribute("product",product);
		}catch(Exception e) {
			
		}
		return "viewProduct";
	}
	
	@RequestMapping(value="/admin")
	public String adminPage() {
		return "user-login";
	}
	
	@RequestMapping(value="/admin/productInventory")
	public String productInventory(ModelMap map, ProductDAO productDao) {
		try {
			List<Product> products = productDao.getAllProducts();
			map.addAttribute("products", products);
		}catch(Exception e) {
			
		}
		return "productInventory";
	}
	
	@RequestMapping(value="/admin/productInventory/addProduct")
	public String addProduct(ModelMap map) {
		Product product = new Product();
		map.addAttribute("product",product);
		return "addProduct";
	}
	
	@RequestMapping(value="/admin/productInventory/addProduct", method = RequestMethod.POST)
	public String addProductPost(@Valid @ModelAttribute("product") Product product, BindingResult result, ProductDAO productDao, HttpServletRequest request) {
		try {
		    if(result.hasErrors()) {
		    	return "addProduct";
		    }
		productDao.addProduct(product);
		
		MultipartFile productImage = product.getProductImage();
		File destFile = new File("C:\\uploads\\"+product.getProductId()+".png");
		if(productImage!=null && !productImage.isEmpty()) {
			productImage.transferTo(destFile);
		}
		}catch(Exception e) {
			
		}
		return "redirect:/admin/productInventory";
	}
	
	@RequestMapping(value="/admin/productInventory/deleteProduct/{productId}")
	public String deleteProduct(@PathVariable String productId, ModelMap map, ProductDAO productDao) {
		try {
         productDao.deleteProduct(Integer.parseInt(productId));
         File destFile = new File("C:\\uploads\\"+productId+".png");

         if(destFile.exists()) {
        	 destFile.delete();
         }
		}catch(Exception e) {
			
		}
		return "redirect:/admin/productInventory";
	}
	
	@RequestMapping(value="/admin/productInventory/editProduct/{productId}")
	public String editProduct(@PathVariable String productId, ModelMap map, ProductDAO productDao) {
		try {
		Product product =  productDao.getProductById(Integer.parseInt(productId));
		map.addAttribute(product);
		}catch(Exception e){
			
		}
		return "editProduct";
		}
	
	@RequestMapping(value="/admin/productInventory/editProduct",method = RequestMethod.POST)
	public String editProductPost(@Valid @ModelAttribute("product") Product product, BindingResult result, ProductDAO productDao, ModelMap map , HttpServletRequest request) {
		try {
		    if(result.hasErrors()) {
		    	return "editProduct";
		    }
	    MultipartFile productImage = product.getProductImage();
		File destFile = new File("C:\\uploads\\"+product.getProductId()+".png");
		if(productImage!=null && !productImage.isEmpty()) {
			productImage.transferTo(destFile);
		}
		productDao.editProduct(product);
		}catch(Exception e){
			
		}
		return "redirect:/admin/productInventory";
		}
}
