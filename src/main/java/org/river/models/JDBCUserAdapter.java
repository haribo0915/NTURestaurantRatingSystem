package org.river.models;

import org.river.entities.Role;
import org.river.entities.User;
import org.river.exceptions.ResourceNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCUserAdapter implements UserAdapter {
    @Override
    public User createUser(User user) {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	
    	String sqlUpdate = "insert into user (role_id, name, account, password, "
    			+ "email, department) values (?, ?, ?, ?, ?, ?)";
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlUpdate);
    		stat.setInt(1, user.getRoleId());
    		stat.setString(2, user.getName());
    		stat.setString(3, user.getAccount());
    		stat.setString(4, user.getPassword());
    		if (user.getEmail() == null)
    			stat.setString(5, "");
    		else
    			stat.setString(5, user.getEmail());
    		stat.setString(6, user.getDepartment());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		//throw new CreateException("createUser error");
    	}
    	
    	String sqlQuery = "select u.id from user u where account=?";
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, user.getAccount());
    		ResultSet rs = stat.executeQuery();
    		rs.next();
    		return new User(rs.getInt("id"), user.getRoleId(), 
 				   user.getName(), user.getAccount(), user.getPassword(), 
 				   user.getEmail(), user.getDepartment());
    	} catch(Exception e) {
    		//default return type
    		return new User(-1, -1, 
  				   "", "", "", 
  				   "", "");
    		
    		//throw new CreateException("createRole error: fail to insert");
    	}
    }

    @Override
    public User updateUser(User user) {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "UPDATE user SET role_id=?,name=?,account=?,"
    			+ "password=?,email=?,department=? where id=?";
    	
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, user.getRoleId());
    		stat.setString(2, user.getName());
    		stat.setString(3, user.getAccount());
    		stat.setString(4, user.getPassword());
    		if (user.getEmail() == null)
    			stat.setString(5, "");
    		else
    			stat.setString(5, user.getEmail());
    		stat.setString(6, user.getDepartment());
    		stat.setInt(7, user.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		// throw new UpdateException("updateUser error");
    	}
    	return new User(user.getId(), user.getRoleId(), 
				   user.getName(), user.getAccount(), user.getPassword(), 
				   user.getEmail(), user.getDepartment());
    }

    @Override
    public User deleteUser(User user) {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "delete from user where id=?";
    	
    	// Delete
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, user.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		// throw new DeleteException("deleteUser error");
    	}
    	return new User(user.getId(), user.getRoleId(), 
				   user.getName(), user.getAccount(), user.getPassword(), 
				   user.getEmail(), user.getDepartment());
    }

    @Override
    public User queryUser(String account, String password) throws ResourceNotFoundException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	User out = null;
    	
    	// Fetch account
    	try {
    		String sqlQuery = "select * from user where account=?";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, account);
    		ResultSet rs = stat.executeQuery();
    		rs.next();
    		
    		// check password
	    	if (password.equals(rs.getString("password"))) {
				out = new User(rs.getInt("id"), rs.getInt("role_id"),
						rs.getString("name"), account, password,
						rs.getString("email"), rs.getString("department"));
			}
    	}
    	catch(Exception e) {
    		
    	}
    	
    	if (out == null)
    		throw new ResourceNotFoundException("queryUser error");
    	return out;
    }

    @Override 
    public Role createRole(Role role) {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlUpdate = "insert into role (title) values (?)";
    	
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlUpdate);
    		stat.setString(1, role.getTitle());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		//throw new CreateException("createRole error");
    	}
    	
    	Role out = null;
    	String sqlQuery = "select r.id from role r where title=?";
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, role.getTitle());
    		ResultSet rs = stat.executeQuery();
    		while (rs.next()) {
    			out = new Role(rs.getInt("id"), role.getTitle());
    		}
    	} catch(Exception e) {
    		//throw new CreateException("createRole error: fail to insert");
    	}
    	
    	// defalut return type
    	return new Role(-1, "");
    }

    @Override 
    public Role updateRole(Role role) {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "UPDATE role SET title=? where id=?";
    	
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, role.getTitle());
    		stat.setInt(2, role.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		//throw new UpdateException("updateRole error");
    	}
    	return new Role(role.getId(), role.getTitle());
    }

    @Override 
    public Role deleteRole(Role role) {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "delete from role where id=?";
    	
    	// Delete
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, role.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		//throw new DeleteException("deleteRole error");
    	}
    	return new Role(role.getId(), role.getTitle());
    }

    @Override 
    public Role queryRole(Integer id) throws ResourceNotFoundException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "select * from role where id=?";
    	
    	Role out = null;
    	int cnt = 0;
    	// Fetch Role
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, id);
    		ResultSet rs = stat.executeQuery();
   
    		while (rs.next()) {
	    		String title = rs.getString("title");
	    		out = new Role(id, title);
	    		cnt += 1;
    		}
    	}
    	catch(Exception e) {
    		//throw new QueryException("queryRole error: id");
    	}
    	
    	if (cnt == 1)
    		return out;
    	else if (cnt < 1)
    		throw new ResourceNotFoundException("queryRole error: id can't find");
    	else
    		return out;//throw new QueryException("queryRole error: multi-reply");
    }
}
