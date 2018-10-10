package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.Apartment;
import model.util.JDBCUtil;

public class ApartmentDao {
	private static Connection connection = JDBCUtil.getConnection();

	public static double findPerDayFeeByPropertyId(String propertyId) {
		Statement statement = null;
		double perDayFee=0;
		try {
			statement=connection.createStatement();
			String sql="SELECT per_day_fee FROM apartment WHERE property_id='"+propertyId+"'";
			ResultSet resultSet=statement.executeQuery(sql);
			while (resultSet.next()) {
				perDayFee=resultSet.getDouble(1);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return perDayFee;
		}finally {
			if(statement!=null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return perDayFee;
	}
	
	public static List<Apartment> getAllApartment(){
		ArrayList<Apartment> apartments=new ArrayList<>();
		Statement statement = null;
		try {
			statement=connection.createStatement();
			String sql="SELECT * FROM apartment";
			ResultSet resultSet=statement.executeQuery(sql);
			while (resultSet.next()) {
				Apartment apartment=new Apartment();
				apartment.setPropertyId(resultSet.getString(1));
				apartment.setPerDayFee(resultSet.getDouble(2));
				apartments.add(apartment);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return apartments;
		}finally {
			if(statement!=null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return apartments;
	}
	
	public static void saveAll(List<Apartment> apartments) throws Exception {
		Statement statement=null;
		try {
			statement=connection.createStatement();
			String sql="INSERT INTO apartment VALUES ";
			StringBuffer temp=new StringBuffer();
			for (Apartment apartment : apartments) {
				temp.append("('"+apartment.getPropertyId()+"','"+apartment.getPerDayFee()+"'),");
			}
			temp.delete(temp.length()-1, temp.length());
			sql=sql+temp.toString();
			statement.execute(sql);
		} catch (Exception e) {
			throw e;
		}finally {
			if(statement!=null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
