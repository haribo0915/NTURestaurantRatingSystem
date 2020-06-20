package org.river.models;

import org.river.entities.*;
import org.river.exceptions.ResourceNotFoundException;
import org.river.utils.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class JDBCRestaurantAdapter implements RestaurantAdapter {
	private JDBCConnectionPool jdbcConnectionPool;

	public JDBCRestaurantAdapter() {
		jdbcConnectionPool = JDBCConnectionPool.getInstance();
	}
	
	// Insert a new restaurant to the database and return a Restaurant object
    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();

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
    			stat.setNull(4, Types.VARCHAR);
    		else
    			stat.setString(4, restaurant.getDescription());
    		if (restaurant.getImage() == null)
    			stat.setNull(5, Types.VARCHAR);
    		else
    			stat.setString(5, restaurant.getImage());
    		stat.setString(6, restaurant.getAddress());
    		stat.executeUpdate();
    	} catch(Exception e) {
    		e.printStackTrace();
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
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	} 
    	finally {
            jdbcConnectionPool.takeIn(con);
        }

    	return new Restaurant(id, restaurant.getAreaId(),
    			restaurant.getFoodCategoryId(),	restaurant.getName(),
    			restaurant.getDescription(), restaurant.getImage(),
    			restaurant.getAddress());
    }

    // update the new info about the given restaurant to the database
    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();

    	// update the info
    	try {
    		String sqlQuery = "UPDATE restaurant SET name=?, area_id=?, "
        			+ "food_category_id=?, description=?, address=?, image=? where id=?";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, restaurant.getName());
    		stat.setInt(2, restaurant.getAreaId());
    		stat.setInt(3, restaurant.getFoodCategoryId());
    		if (restaurant.getDescription() == null)
    			stat.setNull(4, Types.VARCHAR);
    		else
    			stat.setString(4, restaurant.getDescription());
    		stat.setString(5, restaurant.getAddress());
    		if (restaurant.getImage() == null)
    			stat.setNull(6, Types.VARCHAR);
    		else
    			stat.setString(6, restaurant.getImage());
    		stat.setInt(7, restaurant.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

    	return restaurant;
    }

    // Delete the given restaurant from the database
    @Override
    public Restaurant deleteRestaurant(Restaurant restaurant) {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();

    	// Delete
    	try {
        	String sqlQuery = "delete from restaurant where id=?";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, restaurant.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

    	return restaurant;
    }

    // return the Restaurant object with the given id
    public Restaurant queryRestaurant(int id) throws ResourceNotFoundException {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
		Restaurant out = null;

		// Query
		try {
			String sqlQuery = "select * from restaurant where id=?";
			PreparedStatement stat = con.prepareStatement(sqlQuery);
			stat.setInt(1, id);
			ResultSet rs = stat.executeQuery();

			while (rs.next())
				out = new Restaurant(id, rs.getInt("area_id"),
						rs.getInt("food_category_id"),	rs.getString("name"),
						rs.getString("description"), rs.getString("image"),
						rs.getString("address"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            jdbcConnectionPool.takeIn(con);
        }

		if (out == null)
			throw new ResourceNotFoundException("queryRestaurant error: utils");
		return out;
    }

    // return a list of the Restaurant objects with the given info
	@Override
	public List<Restaurant> queryRestaurants(String restaurantName, Area area, FoodCategory foodCategory) throws ResourceNotFoundException {
		// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
    	List<Restaurant> out = new ArrayList<Restaurant>();

    	// Query
    	try {
    		String sqlQuery = "select * from restaurant where (? or area_id=?)"
    				+ " and (? or food_category_id=?) and (? or name=?)";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setBoolean(1, area == null);
    		if (area != null)
    			stat.setInt(2, area.getId());
    		else
    			stat.setInt(2, 0);
    		stat.setBoolean(3, foodCategory == null);
    		if (foodCategory != null)
    			stat.setInt(4, foodCategory.getId());
    		else
    			stat.setInt(4, 0);
    		stat.setBoolean(5, restaurantName == null);
    		if (restaurantName != null)
    			stat.setString(6, restaurantName);
    		else
    			stat.setString(6, "");
    		ResultSet rs = stat.executeQuery();

    		while (rs.next())
	    		out.add(new Restaurant(rs.getInt("id"), rs.getInt("area_id"),
		    				rs.getInt("food_category_id"),	rs.getString("name"),
		    				rs.getString("description"), rs.getString("image"),
		        			rs.getString("address")));
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

    	if (out.size() == 0)
    		throw new ResourceNotFoundException("query restaurants error");
    	return out;
	}


	// return a list of restaurants that get the highest average rate in a past week
    @Override
    public List<Restaurant> queryWeeklyHottestRestaurants() throws ResourceNotFoundException {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
    	String sqlQuery = "select * from comment";
    	List<Restaurant> out = new ArrayList<Restaurant>();

    	List<Integer> restaurantIds = new ArrayList<Integer>();
    	List<Integer> rateArray = new ArrayList<Integer>();
    	int maxRestaurantId = 0;
    	
    	// Query
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		ResultSet rs = stat.executeQuery();
    		
    		while (rs.next()) {
    			if (new java.util.Date().getTime() - rs.getTimestamp("date").getTime() < 1000*3600*24*7) {
					System.out.println("comment");
    				int restaurantId = rs.getInt("restaurant_id");
    				int restaurantRate = rs.getInt("rate");
    				restaurantIds.add(restaurantId);
    				rateArray.add(restaurantRate);
    				if (maxRestaurantId < restaurantId)
    					maxRestaurantId = restaurantId;
    			}
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	} finally {
    		jdbcConnectionPool.takeIn(con);
		}
    	
    	
    	int[] totalRate = new int [maxRestaurantId + 20];
    	int[] commentCnt = new int [maxRestaurantId + 20];
    	
    	// calculate the total rate and how many times a restautant is commented
    	for (int i = 0; i < restaurantIds.size(); i++) {
    		int restaurantId = restaurantIds.get(i);
    		int restaurantRate = rateArray.get(i);
    		totalRate[restaurantId] += restaurantRate;
    		commentCnt[restaurantId] += 1;
    	}

    	// find hottest
    	double max = 0;

    	for (int i = 1; i < maxRestaurantId + 1; i++) {
    		if (commentCnt[i] == 0)
    			continue;
    		else if (max < (double)totalRate[i]/commentCnt[i]) {
    			max = (double)totalRate[i]/commentCnt[i];
    			out = new ArrayList<Restaurant>();
    			out.add(queryRestaurant(i));
    		}
    		else if (max == (double)totalRate[i]/commentCnt[i])
    			out.add(queryRestaurant(i));
    	}

		if (out.size() == 0)
			throw new ResourceNotFoundException("queryWeeklyHottestRestaurants error");
    	return out;
    }

    // insert the given area into the database and return the area object with its id
    @Override
    public Area createArea(Area area) {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
    	String sqlUpdate = "insert into area (name) values (?)";

    	try {
    		PreparedStatement stat = con.prepareStatement(sqlUpdate);
    		stat.setString(1, area.getName());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
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
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

    	return out;
    }

    // update the given area object to the database
    @Override
    public Area updateArea(Area area) {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
    	String sqlQuery = "UPDATE area SET name=? where id=?";

    	// update the table
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, area.getName());
    		stat.setInt(2, area.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

    	return new Area(area.getId(), area.getName());
    }

    // delete the given area from the database
    @Override
    public Area deleteArea(Area area) {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
    	String sqlQuery = "delete from area where id=?";

    	// Delete
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, area.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

    	return new Area(area.getId(), area.getName());
    }

    // return the area with the given id
    @Override
    public Area queryArea(Integer id) throws ResourceNotFoundException {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
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
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

    	if(cnt < 1)
    		throw new ResourceNotFoundException("queryArea error: given area id not found");
    	return out;
    }

    // return the area object with the given area name
    @Override
    public Area queryArea(String name) throws ResourceNotFoundException {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
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
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

		if(cnt < 1)
			throw new ResourceNotFoundException("queryArea error: given area name not found");
		return out;
    }

    // return a list of all areas in the database
    @Override
    public List<Area> queryAreas() throws ResourceNotFoundException {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
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
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

		if (out.size() == 0)
			throw new ResourceNotFoundException("query areas error");
    	return out;
    }

    // insert the given foodcategory into the database
    @Override
    public FoodCategory createFoodCategory(FoodCategory foodCategory) {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
    	String sqlUpdate = "insert into food_category (name) values (?)";

    	// insert the given foodcategory into the table
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlUpdate);
    		stat.setString(1, foodCategory.getName());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
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
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

    	return out;
    }

    // update the given foodCategory to the database and return the given foodCategory
    @Override
    public FoodCategory updateFoodCategory(FoodCategory foodCategory) {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
    	String sqlQuery = "UPDATE food_category SET name=? where id=?";

    	// update
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setString(1, foodCategory.getName());
    		stat.setInt(2, foodCategory.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

    	return new FoodCategory(foodCategory.getId(), foodCategory.getName());
    }

    // delete the given foodCategory from the database
    @Override
    public FoodCategory deleteFoodCategory(FoodCategory foodCategory) {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
    	String sqlQuery = "delete from food_category where id=?";

    	// Delete
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, foodCategory.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

    	return new FoodCategory(foodCategory.getId(), foodCategory.getName());
    }

    // return a FoodCategory object with the given id
    @Override
    public FoodCategory queryFoodCategory(Integer id) throws ResourceNotFoundException {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
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
    		throw new ResourceNotFoundException("queryFoodCategory error: id");
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

		if(cnt < 1)
			throw new ResourceNotFoundException("queryFoodCategory error: id can't find");
		return out;
    }

    // return a FoodCategory object with the given FoodCategoryName
    @Override
    public FoodCategory queryFoodCategory(String name) throws ResourceNotFoundException {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
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
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

		if(cnt < 1)
			throw new ResourceNotFoundException("queryFoodCategory error: name can't find id");
		return out;
    }

    // return a list of FoodCategory objects in the database
    @Override
    public List<FoodCategory> queryFoodCategories() throws ResourceNotFoundException {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
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
    		e.printStackTrace();
    	} finally {
            jdbcConnectionPool.takeIn(con);
        }

		if(out.size() == 0)
			throw new ResourceNotFoundException("query food categories not found");
    	return out;
    }

    // insert a given comment to the database and return the comment with its comment_id
    @Override
    public Comment createComment(Comment comment) {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();

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
    			stat.setNull(4, Types.VARCHAR);
    		else
    			stat.setString(4, comment.getDescription());
    		if (comment.getImage() == null)
    			stat.setNull(5, Types.VARCHAR);
    		else
    			stat.setString(5, comment.getImage());
    		if (comment.getTimestamp() == null)
    			stat.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
    		else
    			stat.setTimestamp(6, comment.getTimestamp());
    		stat.executeUpdate();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	Comment out = null;
    	
    	try {
    		int sqlCount = SQLUtils.countSQL(con, "comment");
    		out = new Comment(sqlCount, comment.getUserId(), comment.getRestaurantId(),
    				comment.getRate(), comment.getDescription(),
    				comment.getImage(), comment.getTimestamp());
    	}
    	catch(Exception e) {
    		// default return type
    		out = new Comment(-1, -1, -1,
    				0, null,
    				null, null);
    	} finally {
    		jdbcConnectionPool.takeIn(con);
		}
    	
    	return out;
    }

    // update the given comment to the database
    @Override
    public Comment updateComment(Comment comment) {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();

    	try {
    		String sqlQuery = "UPDATE comment SET user_id=?, restaurant_id=?, "
        			+ "rate=?, description=?, image=?, date=? where id=?";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, comment.getUserId());
    		stat.setInt(2, comment.getRestaurantId());
    		stat.setInt(3, comment.getRate());
    		if (comment.getDescription() == null)
    			stat.setNull(4, Types.VARCHAR);
    		else
    			stat.setString(4, comment.getDescription());
    		if (comment.getImage() == null)
    			stat.setNull(5, Types.VARCHAR);
    		else
    			stat.setString(5, comment.getImage());
    		if (comment.getTimestamp() == null)
    			stat.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
    		else
    			stat.setTimestamp(6, comment.getTimestamp());
    		stat.setInt(7, comment.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	} 
    	finally {
    		jdbcConnectionPool.takeIn(con);
		}
    	
    	return comment;
    }

    // delete the comment from the database
    @Override
    public Comment deleteComment(Comment comment) {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();

    	// Delete
    	try {
    		String sqlQuery = "delete from comment where id=?";
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, comment.getId());
    		int reply = stat.executeUpdate();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	finally {
    		jdbcConnectionPool.takeIn(con);
		}
    	
    	return comment;
    }

    // query a list of comments with the given user
    @Override
    public List<Comment> queryComments(User user) throws ResourceNotFoundException {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
    	String sqlQuery = "select * from comment where user_id=?";

    	// Query
    	List<Comment> out = new ArrayList<Comment>();
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, user.getId());
    		ResultSet rs = stat.executeQuery();

    		while (rs.next())
	    		out.add(new Comment(rs.getInt("id"), rs.getInt("user_id"),
	    				rs.getInt("restaurant_id"), rs.getInt("rate"),
	    				rs.getString("description"), rs.getString("image"),
	    				rs.getTimestamp("date")));

    	}
    	catch(Exception e) {}
    	finally {
    		jdbcConnectionPool.takeIn(con);
		}

    	if (out.size() == 0) {
    		throw new ResourceNotFoundException("queryComments: error: List");
		}
    	return out;
    }

    // query a list of comments with the given restaurant
    @Override
    public List<Comment> queryComments(Restaurant restaurant) throws ResourceNotFoundException {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
    	String sqlQuery = "select * from comment where restaurant_id=?";

    	// Query
    	List<Comment> out = new ArrayList<Comment>();
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, restaurant.getId());
    		ResultSet rs = stat.executeQuery();

    		while (rs.next())
	    		out.add(new Comment(rs.getInt("id"), rs.getInt("user_id"),
	    				rs.getInt("restaurant_id"), rs.getInt("rate"),
	    				rs.getString("description"), rs.getString("image"),
	    				rs.getTimestamp("date")));

    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	} 
    	finally {
    		jdbcConnectionPool.takeIn(con);
		}

		if (out.size() == 0) {
			throw new ResourceNotFoundException("queryComments: error: List");
		}
    	return out;
    }

    // query a list of UserComment objects with the given restaurant
    @Override
    public List<UserComment> queryUserComments(Restaurant restaurant) throws ResourceNotFoundException {
    	// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
    	String sqlQuery = "select * from comment c inner join user u on restaurant_id=? and c.user_id=u.id";

    	// Query
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
	    				rs.getString("image"),
	    				rs.getTimestamp("date")));

    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	finally {
    		jdbcConnectionPool.takeIn(con);
		}

		if (out.size() == 0) {
			throw new ResourceNotFoundException("queryComments: error: List");
		}
    	return out;
    }

    // query a comment object with the given restaurant_id or user_id
	@Override
	public Comment queryComment(Integer userId, Integer restaurantId) throws ResourceNotFoundException {
		// Connecting to database
    	Connection con = jdbcConnectionPool.takeOut();
    	String sqlQuery = "select * from comment where user_id=? and restaurant_id=?";

    	// Query
    	Comment out = null;
    	try {
    		PreparedStatement stat = con.prepareStatement(sqlQuery);
    		stat.setInt(1, userId);
    		stat.setInt(2, restaurantId);
    		ResultSet rs = stat.executeQuery();

    		while (rs.next())
    			out = (new Comment(rs.getInt("id"), rs.getInt("user_id"),
	    				rs.getInt("restaurant_id"), rs.getInt("rate"),
	    				rs.getString("description"), rs.getString("image"),
	    				rs.getTimestamp("date")));

    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	finally {
    		jdbcConnectionPool.takeIn(con);
		}

    	if (out == null)
    		throw new ResourceNotFoundException("New Comment");
    	else
    		return out;
	}
}
