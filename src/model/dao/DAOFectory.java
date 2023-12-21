package model.dao;

import model.dao.impl.SellerDaoImpl;

public class DAOFectory {
	public static SellerDAO createSellerDao() {
		return new SellerDaoImpl();
	}
}
