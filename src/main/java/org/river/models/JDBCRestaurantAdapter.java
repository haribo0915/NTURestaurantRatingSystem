package org.river.models;

import org.river.entities.*;
import org.river.utils.*;
import org.river.exceptions.*;
import java.util.*;
import java.io.ByteArrayInputStream;
import java.sql.*;


public class JDBCRestaurantAdapter implements RestaurantAdapter {
    @Override
    public Restaurant createRestaurant(Restaurant restaurant) throws CreateException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	
    	// Insert the new restaurant to the database
    	try {
    		String sqlUpdata = "INSERT INTO restaurant (area_id, food_category_id, "
        			+ "name, description, "
        			+ "image, "
        			+ "address) VALUES "
        			+ "(?, ?, ?, ?, ?, ?)";
    		PreparedStatement stat = con.prepareStatement(sqlUpdata);
    		stat.setInt(1, restaurant.getAreaId());
    		stat.setInt(2, restaurant.getFoodCategoryId());
    		stat.setString(3, restaurant.getName());
    		if (restaurant.getDescription() == null)
    			stat.setString(4, "");
    		else
    			stat.setString(4, restaurant.getDescription());
    		if (restaurant.getImage() == null)
    			stat.setNull(5, Types.BLOB);
    		else
    			stat.setBlob(5, ImageUtils.ImageToInputStream(restaurant.getImage()));
    		stat.setString(6, restaurant.getAddress());
    		stat.executeUpdate();
    	} catch(Exception e) {
    		throw new CreateException("createRestaurant error");
    	}
    	
    	// Fetch restaurant_id
    	int id = -1;
    	try {
    		String sqlQuery = "select r.id from restaurant r where name=?";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, restaurant.getName());
    		ResultSet rs = stat.executeQuery();
    		rs.next();
    		id = rs.getInt("id");
    		restaurant.setId(id);
    	} 
    	catch(Exception e) {
    		throw new CreateException("createRestaurant error: retrieve error");
    	}
    	
    	return new Restaurant(id, restaurant.getAreaId(), 
    			restaurant.getFoodCategoryId(),	restaurant.getName(), 
    			restaurant.getDescription(), restaurant.getImage(), 
    			restaurant.getAddress());
    }

    @Override 
    public Restaurant updateRestaurant(Restaurant restaurant) throws UpdateException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	
    	try {
    		String sqlQuery = "UPDATE restaurant SET name=?, area_id=?, "
        			+ "food_category_id=?, description=?, address=?, image=? where id=?";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, restaurant.getName());
    		stat.setInt(2, restaurant.getAreaId());
    		stat.setInt(3, restaurant.getFoodCategoryId());
    		if (restaurant.getDescription() == null)
    			stat.setString(4, "");
    		else
    			stat.setString(4, restaurant.getDescription());
    		stat.setString(5, restaurant.getAddress());
    		if (restaurant.getImage() == null)
    			stat.setNull(5, Types.BLOB);
    		else
    			stat.setBlob(5, ImageUtils.ImageToInputStream(restaurant.getImage()));
    		stat.setInt(7, restaurant.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		throw new UpdateException("updateRestaurant error");
    	}
    	return restaurant;
    }

    @Override
    public Restaurant deleteRestaurant(Restaurant restaurant) throws DeleteException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();

    	// Delete
    	try {
        	String sqlQuery = "delete from restaurant where id=?";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, restaurant.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		throw new DeleteException("deleteRestaurant error");
    	}
    	return restaurant;
    }

    @Override
    public Restaurant queryRestaurant(String name) throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	Restaurant out = null;
    	
    	try {
    		String sqlQuery = "select * from restaurant";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		ResultSet rs = stat.executeQuery();
    		
    		while (rs.next())
	    		if (name.equals(rs.getString("name")))
    				return new Restaurant(rs.getInt("id"), rs.getInt("area_id"), 
		    				rs.getInt("food_category_id"),	rs.getString("name"), 
		    				rs.getString("description"), ImageUtils.BlobToImage(rs.getBlob("image")), 
		        			rs.getString("address"));
    	} catch (Exception r) {
    		throw new QueryException("queryRestaurants error");
    	}
    	if (out == null)
    		throw new QueryException("queryRestaurants error");
    	else
    		return out;
    }

    /**
     *
     * @return all restaurants
     * @throws QueryException
     */
    @Override
    public List<Restaurant> queryRestaurants() throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	List<Restaurant> out = new ArrayList<Restaurant>();
    	
    	try {
    		String sqlQuery = "select * from restaurant";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		ResultSet rs = stat.executeQuery(sqlQuery);
    		
    		while (rs.next())
	    		out.add(new Restaurant(rs.getInt("id"), rs.getInt("area_id"), 
		    				rs.getInt("food_category_id"),	rs.getString("name"), 
		    				rs.getString("description"), ImageUtils.BlobToImage(rs.getBlob("image")), 
		        			rs.getString("address")));
    	} catch (Exception r) {
    		throw new QueryException("queryRestaurants error");
    	}
    	
    	return out;
    }

    @Override
    public List<Restaurant> queryRestaurants(Area area) throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	List<Restaurant> out = new ArrayList<>();
    	
    	try {
    		String sqlQuery = "select * from restaurant where area_id=?";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, area.getId());
    		ResultSet rs = stat.executeQuery();
    		
    		while (rs.next())
	    		out.add(new Restaurant(rs.getInt("id"), rs.getInt("area_id"), 
		    				rs.getInt("food_category_id"),	rs.getString("name"), 
		    				rs.getString("description"), ImageUtils.BlobToImage(rs.getBlob("image")), 
		        			rs.getString("address")));
    	} catch (Exception r) {
    		throw new QueryException("queryRestaurants error");
    	}
    	
    	return out;
    }

    @Override
    public List<Restaurant> queryRestaurants(FoodCategory foodCategory) throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	List<Restaurant> out = new ArrayList<>();
    	
    	try {
    		String sqlQuery = "select * from restaurant where food_category_id=?";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, foodCategory.getId());
    		ResultSet rs = stat.executeQuery();
    		
    		while (rs.next())
	    		out.add(new Restaurant(rs.getInt("id"), rs.getInt("area_id"), 
		    				rs.getInt("food_category_id"),	rs.getString("name"), 
		    				rs.getString("description"), ImageUtils.BlobToImage(rs.getBlob("image")), 
		        			rs.getString("address")));
    	} catch (Exception r) {
    		throw new QueryException("queryRestaurants error");
    	}
    	
    	return out;
    }

    @Override //to test
    public List<Restaurant> queryWeeklyHottestRestaurants() throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "select * from comment";

    	List<Restaurant> out = new ArrayList<Restaurant>();
    	int restaurantCount = SQLUtils.countSQL(con, "restaurant");
    	int[] totalRate = new int [restaurantCount];
    	//int[] rateCount = new int [restaurantCount];
    	
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		ResultSet rs = stat.executeQuery();

    		while (rs.next())
    			if (rs.getTimestamp("date").getTime() < 1000*3600*24*7) {
    				int restaurantId = rs.getInt("restaurant_id");
    				totalRate[restaurantId] += rs.getInt("rate");
    				//rateCount[restaurantId] += 1;
    			}

    	}
    	catch(Exception e) {
    		throw new QueryException("queryWeeklyHottestRestaurants error");
    	}
    	
    	// find hottest
    	int max = 0;
    	for (int i = 0; i < restaurantCount; i++) {
    		if (max < totalRate[i]) {
    			max = totalRate[i];
    			out = new ArrayList<Restaurant>();
    			out.add(SQLUtils.queryRestaurant(i));
    		}
    		else if (max == totalRate[i])
    			out.add(SQLUtils.queryRestaurant(i));
    	}
    	return out;
    }

    @Override
    public Area createArea(Area area) throws CreateException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlUpdate = "insert into area (name) values (?)";
    	
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlUpdate);
    		stat.setString(1, area.getName());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		throw new CreateException("createArea error");
    	}
    	
    	Area out = null;
    	String sqlQuery = "select a.id from area a where name=?";
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, area.getName());
    		ResultSet rs = stat.executeQuery();
    		rs.next();
    		out = new Area(rs.getInt("id"), area.getName());
    	} catch(Exception e) {
    		throw new CreateException("createArea error: fail to insert");
    	}
    	return out;
    }

    @Override
    public Area updateArea(Area area) throws UpdateException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "UPDATE area SET name=? where id=?";
    	
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, area.getName());
    		stat.setInt(2, area.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		throw new UpdateException("updateArea error");
    	}
    	return new Area(area.getId(), area.getName());
    }

    @Override
    public Area deleteArea(Area area) throws DeleteException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "delete from area where id=?";
    	
    	// Delete
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, area.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		throw new DeleteException("deleteArea error");
    	}
    	return new Area(area.getId(), area.getName());
    }

    @Override
    public Area queryArea(Integer id) throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "select * from area where id=?";
    	
    	Area out = null;
    	int cnt = 0;
    	// Fetch area
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, id);
    		ResultSet rs = stat.executeQuery();
   
    		while (rs.next()) {
	    		String name = rs.getString("name");
	    		out = new Area(id, name);
	    		cnt += 1;
    		}
    	}
    	catch(Exception e) {
    		throw new QueryException("queryArea error: id");
    	}
    	
    	if (cnt == 1)
    		return out;
    	else if (cnt < 1)
    		throw new QueryException("queryArea error: name can't be found with given id");
    	else
    		throw new QueryException("queryArea error: multi-reply");
    }

    @Override
    public Area queryArea(String name) throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "select * from area where name=?";
    	
    	Area out = null;
    	int cnt = 0;
    	// Fetch area
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, name);
    		ResultSet rs = stat.executeQuery();
   
    		while (rs.next()) {
	    		int id = rs.getInt("id");
	    		out = new Area(id, name);
	    		cnt += 1;
    		}
    	}
    	catch(Exception e) {
    		throw new QueryException("queryArea error: name");
    	}
    	
    	if (cnt == 1)
    		return out;
    	else if (cnt < 1)
    		throw new QueryException("queryArea error: id can't be found with given name");
    	else
    		throw new QueryException("queryArea error: multi-reply");
    }

    @Override
    public List<Area> queryAreas() throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "select * from area";
    	
    	List<Area> out = new ArrayList<Area>();
    	// Fetch area
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		ResultSet rs = stat.executeQuery();
   
    		while (rs.next()) {
	    		int id = rs.getInt("id");
	    		String name = rs.getString("name");
	    		out.add(new Area(id, name));
    		}
    	}
    	catch(Exception e) {
    		throw new QueryException("queryArea error: List");
    	}
    	return out;
    }

    
    @Override
    public FoodCategory createFoodCategory(FoodCategory foodCategory) throws CreateException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlUpdate = "insert into food_category (name) values (?)";
    	
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlUpdate);
    		stat.setString(1, foodCategory.getName());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		throw new CreateException("createFoodCategory error");
    	}
    	
    	FoodCategory out = null;
    	String sqlQuery = "select f.id from food_category f where name=?";
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, foodCategory.getName());
    		ResultSet rs = stat.executeQuery();
    		while (rs.next()) {
    			out = new FoodCategory(rs.getInt("id"), foodCategory.getName());
    		}
    	} catch(Exception e) {
    		throw new CreateException("createFoodCategory error: fail to insert");
    	}
    	return out;
    }

    @Override
    public FoodCategory updateFoodCategory(FoodCategory foodCategory) throws UpdateException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "UPDATE food_category SET name=? where id=?";
    	
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, foodCategory.getName());
    		stat.setInt(2, foodCategory.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		throw new UpdateException("updateFoodCategory error");
    	}
    	return new FoodCategory(foodCategory.getId(), foodCategory.getName());
    }

    @Override
    public FoodCategory deleteFoodCategory(FoodCategory foodCategory) throws DeleteException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "delete from food_category where id=?";
    	
    	// Delete
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, foodCategory.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		throw new DeleteException("deleteFoodCategory error");
    	}
    	return new FoodCategory(foodCategory.getId(), foodCategory.getName());
    }

    @Override
    public FoodCategory queryFoodCategory(Integer id) throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "select * from food_category where id=?";
    	
    	FoodCategory out = null;
    	int cnt = 0;
    	// Fetch food_category
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, id);
    		ResultSet rs = stat.executeQuery();
   
    		while (rs.next()) {
	    		String name = rs.getString("name");
	    		out = new FoodCategory(id, name);
	    		cnt += 1;
    		}
    	}
    	catch(Exception e) {
    		throw new QueryException("queryFoodCategory error: id");
    	}
    	
    	if (cnt == 1)
    		return out;
    	else if (cnt < 1)
    		throw new QueryException("queryFoodCategory error: id can't find");
    	else
    		throw new QueryException("queryFoodCategory error: multi-reply");
    }

    @Override
    public FoodCategory queryFoodCategory(String name) throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "select * from food_category where name=?";
    	
    	FoodCategory out = null;
    	int cnt = 0;
    	// Fetch food_category
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, name);
    		ResultSet rs = stat.executeQuery();
   
    		while (rs.next()) {
	    		int id = rs.getInt("id");
	    		out = new FoodCategory(id, name);
	    		cnt += 1;
    		}
    	}
    	catch(Exception e) {
    		throw new QueryException("queryFoodCategory error: name");
    	}
    	
    	if (cnt == 1)
    		return out;
    	else if (cnt < 1)
    		throw new QueryException("queryFoodCategory error: name can't find id");
    	else
    		throw new QueryException("queryFoodCategory error: multi-reply");
    }

    @Override
    public List<FoodCategory> queryFoodCategories() throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "select * from food_category";
    	
    	List<FoodCategory> out = new ArrayList<FoodCategory>();
    	// Fetch food_category
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		ResultSet rs = stat.executeQuery();
   
    		while (rs.next()) {
    			int id = rs.getInt("id");
	    		String name = rs.getString("name");
	    		out.add(new FoodCategory(id, name));
    		}
    	}
    	catch(Exception e) {
    		throw new QueryException("queryFoodCategory error: List");
    	}

    	return out;
    }

    @Override
    public Comment createComment(Comment comment) throws CreateException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	
    	try {
    		String sqlUpdata = "INSERT INTO comment (user_id, restaurant_id, "
        			+ "rate, description, "
        			+ "image, "
        			+ "date) VALUES "
        			+ "(?, ?, ?, ?, ?, ?)";
    		PreparedStatement stat = con.prepareStatement(sqlUpdata);
    		stat.setInt(1, comment.getUserId());
    		stat.setInt(2, comment.getRestaurantId());
    		stat.setInt(3, comment.getRate());
    		if (comment.getDescription() == null)
    			stat.setString(4, "");
    		else
    			stat.setString(4, comment.getDescription());
    		if (comment.getImage() == null)
    			stat.setNull(5, Types.BLOB);
    		else
    			stat.setBlob(5, ImageUtils.ImageToInputStream(comment.getImage()));
    		if (comment.getTimestamp() == null)
    			stat.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
    		else
    			stat.setTimestamp(6, comment.getTimestamp());
    		stat.executeUpdate();
    	} catch(Exception e) {
    		;
    	}
    	
    	try {
    		int sqlCount = SQLUtils.countSQL(con, "comment");
    		return new Comment(sqlCount, comment.getUserId(), comment.getRestaurantId(), 
    				comment.getRate(), comment.getDescription(), 
    				comment.getImage(), comment.getTimestamp());
    	} 
    	catch(Exception e) {
    		throw new CreateException("createComment error: retrieve error");
    	}
    }
    
    @Override
    public Comment updateComment(Comment comment) throws UpdateException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	
    	try {
    		String sqlQuery = "UPDATE comment SET user_id=?, restaurant_id=?, "
        			+ "rate=?, description=?, image=?, date=? where id=?";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, comment.getUserId());
    		stat.setInt(2, comment.getRestaurantId());
    		stat.setInt(3, comment.getRate());
    		if (comment.getDescription() == null)
    			stat.setString(4, "");
    		else
    			stat.setString(4, comment.getDescription());
    		if (comment.getImage() == null)
    			stat.setNull(5, Types.BLOB);
    		else
    			stat.setBlob(5, ImageUtils.ImageToInputStream(comment.getImage()));
    		if (comment.getTimestamp() == null)
    			stat.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
    		else
    			stat.setTimestamp(6, comment.getTimestamp());
    		stat.setInt(7, comment.getId());
    		int reply = stat.executeUpdate();
    		return comment;
    	}
    	catch(Exception e) {
    		throw new UpdateException("updateComment error");
    	}
    }

    @Override
    public Comment deleteComment(Comment comment) throws DeleteException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	
    	try {
    		String sqlQuery = "delete from comment where id=?";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, comment.getId());
    		int reply = stat.executeUpdate();
    		return comment;
    	}
    	catch(Exception e) {
    		throw new DeleteException("deleteComment error");
    	}
    }

    @Override
    public List<Comment> queryComments(User user) throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "select * from comment where user_id=?";
    	
    	List<Comment> out = new ArrayList<Comment>();
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, user.getId());
    		ResultSet rs = stat.executeQuery();
   
    		while (rs.next()) 
	    		out.add(new Comment(rs.getInt("id"), rs.getInt("user_id"), 
	    				rs.getInt("restaurant_id"), rs.getInt("rate"),
	    				rs.getString("description"), ImageUtils.BlobToImage(rs.getBlob("image")),
	    				rs.getTimestamp("date")));
    		
    	}
    	catch(Exception e) {
    		throw new QueryException("queryComments: error: List");
    	}

    	return out;
    }

    @Override
    public List<Comment> queryComments(Restaurant restaurant) throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "select * from comment where restaurant_id=?";
    	
    	List<Comment> out = new ArrayList<Comment>();
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, restaurant.getId());
    		ResultSet rs = stat.executeQuery();
   
    		while (rs.next()) 
	    		out.add(new Comment(rs.getInt("id"), rs.getInt("user_id"), 
	    				rs.getInt("restaurant_id"), rs.getInt("rate"),
	    				rs.getString("description"), ImageUtils.BlobToImage(rs.getBlob("image")),
	    				rs.getTimestamp("date")));
    		
    	}
    	catch(Exception e) {
    		throw new QueryException("queryComments: error: List");
    	}

    	return out;
    }

    @Override // to test
    public List<UserComment> queryUserComments(Restaurant restaurant) throws QueryException {
    	DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "select * from comment inner join user on restaurant_id=?";

    	List<UserComment> out = new ArrayList<UserComment>();
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, restaurant.getId());
    		ResultSet rs = stat.executeQuery();

    		while (rs.next())
	    		out.add(new UserComment(rs.getInt("role_id"), rs.getString("name"),
	    				rs.getString("account"), rs.getString("password"),
	    				rs.getString("email"), rs.getString("department"),
	    				rs.getInt("user_id"), restaurant.getId(),
	    				rs.getInt("rate"), rs.getString("description"),
	    				ImageUtils.BlobToImage(rs.getBlob("image")),
	    				rs.getTimestamp("date")));

    	}
    	catch(Exception e) {
    		throw new QueryException("queryUserComments: error: List");
    	}
    	return out;
    }

	@Override
	public Comment queryComment(Integer userId, Integer restaurantId) throws QueryException {
		DBConnect DBC = new DBConnect();
    	Connection con = DBC.getConnect();
    	String sqlQuery = "select * from comment where user_id=? and restaurant_id=?";

    	Comment out = null;
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, userId);
    		stat.setInt(2, restaurantId);
    		ResultSet rs = stat.executeQuery();

    		while (rs.next())
    			out = (new Comment(rs.getInt("id"), rs.getInt("user_id"), 
	    				rs.getInt("restaurant_id"), rs.getInt("rate"),
	    				rs.getString("description"), ImageUtils.BlobToImage(rs.getBlob("image")),
	    				rs.getTimestamp("date")));

    	}
    	catch(Exception e) {
    		throw new QueryException("queryComment error");
    	}
    	
    	if (out == null)
    		throw new QueryException("queryComment error");
    	else
    		return out;
	}
}
