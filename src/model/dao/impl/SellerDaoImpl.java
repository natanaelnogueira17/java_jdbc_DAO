package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	public void insert(Seller seller) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"insert into seller (Name, Email, BirthDate, BaseSalary, DepartmentId) values (?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1,seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			int rowsAffect = st.executeUpdate();
			if(rowsAffect>0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);					
				}
				DB.closeResultset(rs);
			}else {
				throw new DbException("nenhuma linha inserida");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Seller seller) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"update seller set Name = ?, Email=?, BirthDate=?, BaseSalary=?, DepartmentId=? where id = ?");
			st.setString(1,seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			st.setInt(6, seller.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st= conn.prepareStatement(
					"delete from seller where id = ?"
					);
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally{
			DB.closeStatement(st);
		}

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
		obj.setEmail(rs.getString("Email"));
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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT SELLER.*, DEPARTMENT.NAME AS DEPNAME\r\n"
					+ "FROM SELLER INNER JOIN DEPARTMENT\r\n"
					+ "ON SELLER.DEPARTMENTID = DEPARTMENT.ID\r\n"
					+ "ORDER BY NAME;");
			
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
