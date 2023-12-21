package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entity.Department;
import model.entity.Seller;

public class SellerDaoImpl implements SellerDAO {

	private Connection conn;

	public SellerDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller Seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller Seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT SELLER.*, DEPARTMENT.NAME AS DepName\r\n" + "FROM SELLER INNER JOIN DEPARTMENT\r\n"
							+ "ON SELLER.DEPARTMENTID = DEPARTMENT.ID\r\n" + "WHERE SELLER.ID=?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = instatiateDepartment(rs);
				Seller obj = instatiateSeller(dep, rs);
				return obj;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultset(rs);
			DB.closeStatement(st);
		}

	}

	private Seller instatiateSeller(Department dep, ResultSet rs) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setName(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instatiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT SELLER.*, DEPARTMENT.NAME AS DEPNAME\r\n"
					+ "FROM SELLER INNER JOIN DEPARTMENT\r\n"
					+ "ON SELLER.DEPARTMENTID = DEPARTMENT.ID\r\n"
					+ "WHERE SELLER.ID=?\r\n"
					+ "ORDER BY NAME;");
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			 List<Seller> listSeller = new ArrayList<>();
			 Map<Integer, Department > map = new HashMap<>();
			while (rs.next()) {
				Department dep =map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instatiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller obj = instatiateSeller(dep, rs);
				listSeller.add(obj);
			}
			return listSeller;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultset(rs);
			DB.closeStatement(st);
		}

	}

}
