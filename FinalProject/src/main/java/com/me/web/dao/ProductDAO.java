package com.me.web.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.me.web.pojo.Product;

public class ProductDAO extends DAO{
	
	public void addProduct(Product product) throws Exception {
		try {
		begin();
		getSession().save(product);
		commit();
		}catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while creating user: " + e.getMessage());
		}
	}
	
	public Product getProductById(int Id) throws Exception {
		
		try {
			begin();
			Product product = (Product)getSession().get(Product.class, Id);
			//commit();
			return product;
	} catch (HibernateException e) {
		rollback();
		throw new Exception("Exception while creating user: " + e.getMessage());
	}
	}
	
	public List<Product> getAllProducts() throws Exception{
		try {
			begin();
			Query query = getSession().createQuery("from Product");
			List<Product> products = query.list();
			//commit();
			return products;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while creating user: " + e.getMessage());
		}
	}

	public void deleteProduct(int Id) throws Exception {
		try {
			begin();
			getSession().delete(getProductById(Id));
			commit();
		}catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while creating user: " + e.getMessage());
		}
	}
	
	public void editProduct(Product product) throws Exception {
		try {
		begin();
		getSession().saveOrUpdate(product);
		commit();
		}catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while creating user: " + e.getMessage());
		}
	}
}
