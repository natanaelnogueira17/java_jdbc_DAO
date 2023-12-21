package aplicaton;

import model.dao.DAOFectory;
import model.dao.SellerDAO;
import model.entity.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDAO sellerDAO =  DAOFectory.createSellerDao();
		Seller  seller = sellerDAO.findById(3);
		System.out.println(seller);		
	}

}
