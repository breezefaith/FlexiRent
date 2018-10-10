package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.PremiumSuite;
import model.exception.CompleteMaintenanceException;
import model.util.DateTime;
import model.util.JDBCUtil;

public class PremiumSuiteDao {
	private static Connection connection = JDBCUtil.getConnection();
	
	public static double findPerDayFeeByPropertyId(String propertyId) {
		Statement statement = null;
		double perDayFee=0;
		try {
			statement=connection.createStatement();
			String sql="SELECT per_day_fee FROM premium_suite WHERE property_id='"+propertyId+"'";
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

	public static void updateLastMaintenance(PremiumSuite premiumSuite) throws CompleteMaintenanceException {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			String sql = "UPDATE premium_suite SET "
					+ "last_maintenance_date='"+premiumSuite.getLastMaintenance().getEightDigitDate()+"' "
					+ "WHERE property_id='"+premiumSuite.getPropertyId()+"'";
			if (statement.executeUpdate(sql) != 1) {
				throw new SQLException("updating rent record into table rental_record failed;");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CompleteMaintenanceException("sql error:"+e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}

	public static DateTime findLastMaintenanceByPropertyId(String propertyId) {
		Statement statement = null;
		DateTime dateTime=null;
		try {
			statement=connection.createStatement();
			String sql="SELECT last_maintenance_date FROM premium_suite WHERE property_id='"+propertyId+"'";
			ResultSet resultSet=statement.executeQuery(sql);
			while (resultSet.next()) {
				dateTime=new DateTime(resultSet.getString(1));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return dateTime;
		}finally {
			if(statement!=null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return dateTime;
	}
	
	public static List<PremiumSuite> getAllPremiumSuite(){
		ArrayList<PremiumSuite> premiumSuites=new ArrayList<>();
		Statement statement = null;
		try {
			statement=connection.createStatement();
			String sql="SELECT * FROM premium_suite";
			ResultSet resultSet=statement.executeQuery(sql);
			while (resultSet.next()) {
				PremiumSuite premiumSuite=new PremiumSuite();
				premiumSuite.setPropertyId(resultSet.getString(1));
				premiumSuite.setLastMaintenance(new DateTime(resultSet.getString(2)));
				premiumSuite.setPerDayFee(resultSet.getDouble(3));
				premiumSuites.add(premiumSuite);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return premiumSuites;
		}finally {
			if(statement!=null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return premiumSuites;
	}

	public static void saveAll(List<PremiumSuite> premiumSuites) throws Exception {
		Statement statement=null;
		try {
			statement=connection.createStatement();
			String sql="INSERT INTO premium_suite VALUES ";
			StringBuffer temp=new StringBuffer();
			for (PremiumSuite premiumSuite : premiumSuites) {
				temp.append("('"+premiumSuite.getPropertyId()+"','"+premiumSuite.getLastMaintenance().getEightDigitDate()+"','"+premiumSuite.getPerDayFee()+"'),");
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
