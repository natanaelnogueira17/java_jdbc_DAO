package model.dao;

import db.DB;
import model.dao.impl.SellerDaoImpl;

public class DAOFectory {
	public static SellerDAO createSellerDao() {
		return new SellerDaoImpl(DB.getConnection());
	}
}
