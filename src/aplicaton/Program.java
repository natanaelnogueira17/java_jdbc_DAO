package aplicaton;

import java.util.Date;
import java.util.List;

import model.dao.DAOFectory;
import model.dao.SellerDAO;
import model.entity.Department;
import model.entity.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDAO sellerDAO =  DAOFectory.createSellerDao();
		//Seller  seller = sellerDAO.findById(3);
		//System.out.println(seller);	
		Department department = new Department(2, null);
		List<Seller>list = sellerDAO.findByDepartment(department);
		for (Seller s : list) {
			System.out.println(s);			
		}
		
		System.out.println("////////////////////////////////////////////////////////");
		
		List<Seller>listAll = sellerDAO.findAll();
		for (Seller s : listAll) {
			System.out.println(s);			
		}
		
		
		System.out.println("******************************************************");
		
		
		Seller seller = new Seller(null, "greg", "greg@gmail.com", new Date(), 4400.0, department);
		//sellerDAO.insert(seller);
		System.out.println(seller.getId());
		
		
		System.out.println("******************************************************");
		
		seller = sellerDAO.findById(1);
		seller.setName("troquei o nome para testar update");
		sellerDAO.update(seller);
		System.out.println(seller);
		
		 
	}

}
