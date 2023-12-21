package aplicaton;

import java.util.Date;

import model.dao.DAOFectory;
import model.dao.SellerDAO;
import model.entity.Department;
import model.entity.Seller;

public class Program {

	public static void main(String[] args) {
		Department obj =  new Department(1,"books");
		Seller seller = new Seller(1, "bob", "email@email", new Date(), 3000.0, obj);
		
		SellerDAO sellerDAO =  DAOFectory.createSellerDao();
		
		System.out.println(obj);
		System.out.println(seller);
	}

}
