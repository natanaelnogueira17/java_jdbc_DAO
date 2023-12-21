package aplicaton;

import java.util.Date;

import model.entity.Department;
import model.entity.Seller;

public class Program {

	public static void main(String[] args) {
		Department obj =  new Department(1,"books");
		Seller seller = new Seller(1, "bob", "email@email", new Date(), 3000.0, obj);
		
		System.err.println(obj);
		System.err.println(seller);
	}

}
