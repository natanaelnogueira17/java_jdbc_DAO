package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoImpl;
import model.dao.impl.SellerDaoImpl;

public class DAOFectory {
	
	public static SellerDAO createSellerDao() {
		return new SellerDaoImpl(DB.getConnection());
	}
	
	public static DepartmentDAO createDepartmentDao() {
		return new DepartmentDaoImpl(DB.getConnection());
	}
}
