package aplicaton;

import java.util.Iterator;
import java.util.List;

import model.dao.DAOFectory;
import model.dao.SellerDAO;
import model.entity.Department;
import model.entity.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDAO sellerDAO =  DAOFectory.createSellerDao();
		Seller  seller = sellerDAO.findById(3);
		//System.out.println(seller);	
		Department department = new Department(2, null);
		List<Seller>list = sellerDAO.findByDepartment(department);
		for (Seller s : list) {
			System.out.println(s);			
		}
	}

}
