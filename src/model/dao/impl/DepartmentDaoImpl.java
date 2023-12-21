package model.dao.impl;

import java.sql.Connection;
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
import model.dao.DepartmentDAO;
import model.entity.Department;
import model.entity.Seller;

public class DepartmentDaoImpl implements DepartmentDAO{
	Connection conn;
	
	public DepartmentDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department department) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"insert into Department (Id, Name) values (?,?);"
					,Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, department.getId());
			st.setString(2, department.getName());
			int rowsAffect = st.executeUpdate();
			if(rowsAffect > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);		
				}
				DB.closeResultset(rs);
			}else {
				throw new DbException("nenhuma linha inserida");
			}	
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department department) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
						"update department set Name = ? where id = ?"
					);
			st.setString(1, department.getName());
			st.setInt(2, department.getId());
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
					"delete from department where id = ?"
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
	public Department findById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"select * from department where Id = ?"
					);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				Department department = new Department();
				department.setId(rs.getInt("Id"));
				department.setName(rs.getString("Name"));
				DB.closeResultset(rs);
				return department;
			}
			return null;
			
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select * from department");
			rs = st.executeQuery();
			List<Department> list = new ArrayList<>();
			while(rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("Id"));
				dep.setName(rs.getString("Name"));
				list.add(dep);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultset(rs);
			DB.closeStatement(st);
		}
	}

}
