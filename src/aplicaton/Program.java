package aplicaton;

import java.util.Date;
import java.util.List;

import model.dao.DAOFectory;
import model.dao.DepartmentDAO;
import model.dao.SellerDAO;
import model.entity.Department;
import model.entity.Seller;

public class Program {

	public static void main(String[] args) {
		SellerDAO sellerDAO =  DAOFectory.createSellerDao();
		System.out.println("POR id = 3*********************************************");
		Seller  seller = sellerDAO.findById(3);
		System.out.println(seller);	
		
		
		System.out.println("DEPARTMENT = 2***********************************************");
		Department department = new Department(2, null);
		List<Seller>list = sellerDAO.findByDepartment(department);
		for (Seller s : list) {
			System.out.println(s);			
		}
		
		System.out.println("TODOS***********************************************");
		
		List<Seller>listAll = sellerDAO.findAll();
		for (Seller s : listAll) {
			System.out.println(s);			
		}
		
		
		System.out.println("INSERT*******************************************");
		
		
		//Seller seller = new Seller(null, "greg", "greg@gmail.com", new Date(), 4400.0, department);
		//sellerDAO.insert(seller);
		//System.out.println(seller.getId());
		
		
		System.out.println("UPDATE*********************************************");
		
		seller = sellerDAO.findById(1);
		//seller.setName("troquei o nome para testar update");
		//sellerDAO.update(seller);
		System.out.println(seller);
		
		System.out.println("DELETE*********************************************");
		 sellerDAO.deleteById(1);
		 List<Seller>listAll2 = sellerDAO.findAll();
			for (Seller s : listAll2) {
				System.out.println(s);			
			}
			
			
			
			
		System.out.println("trabalhando no departamento");
		
		
		DepartmentDAO departmentDAO = DAOFectory.createDepartmentDao();
		System.out.println("INSERT Department------  ");
		Department objDepart = new Department(6,"tools" );
		//departmentDAO.insert(objDepart);
		
		System.out.println("UPDATE Department--------");
		objDepart = departmentDAO.findById(6);
		System.out.println(objDepart);
		//objDepart.setName("tools - Draw");
		//departmentDAO.update(objDepart);
		System.out.println(objDepart);
		
		
		
		System.out.println("findAll Department--------");
		List<Department>listAllDep = departmentDAO.findAll();
		for (Department s : listAllDep) {
			System.out.println(s);			
		}
		
		
		System.out.println("deleteById Department--------");
		departmentDAO.deleteById(5);
		List<Department>allDep = departmentDAO.findAll();
		for (Department s : allDep) {
			System.out.println(s);			
		}
		
	}

}
