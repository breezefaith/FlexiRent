package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.constant.PropertyType;
import model.entity.Apartment;
import model.entity.PremiumSuite;
import model.entity.RentalProperty;
import model.exception.PerformMaintenanceException;
import model.util.DateTime;
import model.util.JDBCUtil;

public class RentalPropertyDao {
	private static Connection connection = JDBCUtil.getConnection();

	public static List<RentalProperty> getAllRentalProperty() {
		ArrayList<RentalProperty> rentalProperties = new ArrayList<>();
		Statement statement = null;

		try {
			statement = connection.createStatement();
			String sql = "SELECT * FROM rental_property order by create_time desc";

			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				RentalProperty rentalProperty = null;
				if (resultSet.getString(6).equals(PropertyType.APARTMENT)) {
					rentalProperty = new Apartment();
					rentalProperty.setPropertyId(resultSet.getString(1));
					rentalProperty.setStreetNum(resultSet.getString(2));
					rentalProperty.setStreetName(resultSet.getString(3));
					rentalProperty.setSuburb(resultSet.getString(4));
					rentalProperty.setBedroomsNum(resultSet.getInt(5));
					rentalProperty.setPropertyType(PropertyType.APARTMENT);
					rentalProperty.setStatus(resultSet.getString(7));
					rentalProperty.setImageName(resultSet.getString(8));
					rentalProperty.setDescription(resultSet.getString(9));

					((Apartment) rentalProperty)
							.setPerDayFee(ApartmentDao.findPerDayFeeByPropertyId(rentalProperty.getPropertyId()));
				} else if (resultSet.getString(6).equals(PropertyType.PREMIUM_SUITE)) {
					rentalProperty = new PremiumSuite();
					rentalProperty.setPropertyId(resultSet.getString(1));
					rentalProperty.setStreetNum(resultSet.getString(2));
					rentalProperty.setStreetName(resultSet.getString(3));
					rentalProperty.setSuburb(resultSet.getString(4));
					rentalProperty.setBedroomsNum(resultSet.getInt(5));
					rentalProperty.setPropertyType(PropertyType.PREMIUM_SUITE);
					rentalProperty.setStatus(resultSet.getString(7));
					rentalProperty.setImageName(resultSet.getString(8));
					rentalProperty.setDescription(resultSet.getString(9));

					((PremiumSuite) rentalProperty)
							.setPerDayFee(PremiumSuiteDao.findPerDayFeeByPropertyId(rentalProperty.getPropertyId()));
					((PremiumSuite) rentalProperty).setLastMaintenance(
							PremiumSuiteDao.findLastMaintenanceByPropertyId(rentalProperty.getPropertyId()));
				}
				rentalProperties.add(rentalProperty);
			}

			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rentalProperties;
	}

	public static List<Apartment> getAllApartment() {
		ArrayList<Apartment> apartments = new ArrayList<>();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			String sql = "SELECT " + "rental_property.property_id,street_number,street_name,suburb,property_status,"
					+ "bedrooms_number,property_type,image_name,description,per_day_fee "
					+ "FROM rental_property,apartment " + "where rental_property.property_id=apartment.property_id";
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				Apartment apartment = new Apartment();
				apartment.setPropertyId(resultSet.getString("property_id"));
				apartment.setStreetNum(resultSet.getString("street_number"));
				apartment.setStreetName(resultSet.getString("street_name"));
				apartment.setSuburb(resultSet.getString("suburb"));
				apartment.setPropertyType(resultSet.getString("property_type"));
				apartment.setStatus(resultSet.getString("property_status"));
				apartment.setBedroomsNum(resultSet.getInt("bedrooms_number"));
				apartment.setImageName(resultSet.getString("image_name"));
				apartment.setDescription(resultSet.getString("description"));
				apartment.setPerDayFee(resultSet.getDouble("per_day_fee"));

				apartments.add(apartment);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return apartments;
	}

	public static List<PremiumSuite> getAllPremiumSuite() {
		ArrayList<PremiumSuite> premiumSuites = new ArrayList<>();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			String sql = "SELECT " + "rental_property.property_id,street_number,street_name,suburb,property_status,"
					+ "bedrooms_number,property_type,image_name,description,per_day_fee,last_maintenance_date "
					+ "FROM rental_property,premium_suite "
					+ "where rental_property.property_id=premium_suite.property_id";
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				PremiumSuite premiumSuite = new PremiumSuite();

				premiumSuite.setPropertyId(resultSet.getString("property_id"));
				premiumSuite.setStreetNum(resultSet.getString("street_number"));
				premiumSuite.setStreetName(resultSet.getString("street_name"));
				premiumSuite.setSuburb(resultSet.getString("suburb"));
				premiumSuite.setPropertyType(resultSet.getString("property_type"));
				premiumSuite.setStatus(resultSet.getString("property_status"));
				premiumSuite.setBedroomsNum(resultSet.getInt("bedrooms_number"));
				premiumSuite.setImageName(resultSet.getString("image_name"));
				premiumSuite.setDescription(resultSet.getString("description"));
				premiumSuite.setPerDayFee(resultSet.getDouble("per_day_fee"));
				premiumSuite.setLastMaintenance(new DateTime(resultSet.getString("last_maintenance_date")));

				premiumSuites.add(premiumSuite);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return premiumSuites;
	}

	public static RentalProperty findById(String propertyId) {
		Statement statement = null;
		RentalProperty rentalProperty = null;
		try {
			statement = connection.createStatement();
			String sql = "SELECT * FROM rental_property where property_id='" + propertyId + "'";
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				if (resultSet.getString(6).equals(PropertyType.APARTMENT)) {
					rentalProperty = new Apartment();
					rentalProperty.setPropertyId(resultSet.getString(1));
					rentalProperty.setStreetNum(resultSet.getString(2));
					rentalProperty.setStreetName(resultSet.getString(3));
					rentalProperty.setSuburb(resultSet.getString(4));
					rentalProperty.setBedroomsNum(resultSet.getInt(5));
					rentalProperty.setPropertyType(PropertyType.APARTMENT);
					rentalProperty.setStatus(resultSet.getString(7));
					rentalProperty.setImageName(resultSet.getString(8));
					rentalProperty.setDescription(resultSet.getString(9));

					((Apartment) rentalProperty)
							.setPerDayFee(ApartmentDao.findPerDayFeeByPropertyId(rentalProperty.getPropertyId()));
				} else if (resultSet.getString(6).equals(PropertyType.PREMIUM_SUITE)) {
					rentalProperty = new PremiumSuite();
					rentalProperty.setPropertyId(resultSet.getString(1));
					rentalProperty.setStreetNum(resultSet.getString(2));
					rentalProperty.setStreetName(resultSet.getString(3));
					rentalProperty.setSuburb(resultSet.getString(4));
					rentalProperty.setBedroomsNum(resultSet.getInt(5));
					rentalProperty.setPropertyType(PropertyType.PREMIUM_SUITE);
					rentalProperty.setStatus(resultSet.getString(7));
					rentalProperty.setImageName(resultSet.getString(8));
					rentalProperty.setDescription(resultSet.getString(9));

					((PremiumSuite) rentalProperty)
							.setPerDayFee(PremiumSuiteDao.findPerDayFeeByPropertyId(rentalProperty.getPropertyId()));
				}
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return rentalProperty;
	}

	public static void updateStatus(RentalProperty rentalProperty) throws PerformMaintenanceException {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			String sql = "UPDATE rental_property SET " + "property_status='" + rentalProperty.getStatus() + "' "
					+ "WHERE property_id='" + rentalProperty.getPropertyId() + "'";
			if (statement.executeUpdate(sql) != 1) {
				throw new SQLException("updating rent record into table rental_record failed;");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PerformMaintenanceException("sql error:" + e.getMessage());
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

	public static List<RentalProperty> getAllRentalPropertyForExport() {
		ArrayList<RentalProperty> rentalProperties = new ArrayList<>();
		Statement statement = null;

		try {
			statement = connection.createStatement();
			String sql = "SELECT * FROM rental_property";

			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				RentalProperty rentalProperty = null;
				if (resultSet.getString(6).equals(PropertyType.APARTMENT)) {
					rentalProperty = new Apartment();
					rentalProperty.setPropertyId(resultSet.getString(1));
					rentalProperty.setStreetNum(resultSet.getString(2));
					rentalProperty.setStreetName(resultSet.getString(3));
					rentalProperty.setSuburb(resultSet.getString(4));
					rentalProperty.setBedroomsNum(resultSet.getInt(5));
					rentalProperty.setPropertyType(PropertyType.APARTMENT);
					rentalProperty.setStatus(resultSet.getString(7));
					rentalProperty.setImageName(resultSet.getString(8));
					rentalProperty.setDescription(resultSet.getString(9));
					rentalProperty.setCreateTime(resultSet.getString(10));
				} else if (resultSet.getString(6).equals(PropertyType.PREMIUM_SUITE)) {
					rentalProperty = new PremiumSuite();
					rentalProperty.setPropertyId(resultSet.getString(1));
					rentalProperty.setStreetNum(resultSet.getString(2));
					rentalProperty.setStreetName(resultSet.getString(3));
					rentalProperty.setSuburb(resultSet.getString(4));
					rentalProperty.setBedroomsNum(resultSet.getInt(5));
					rentalProperty.setPropertyType(PropertyType.PREMIUM_SUITE);
					rentalProperty.setStatus(resultSet.getString(7));
					rentalProperty.setImageName(resultSet.getString(8));
					rentalProperty.setDescription(resultSet.getString(9));
					rentalProperty.setCreateTime(resultSet.getString(10));
				}
				rentalProperties.add(rentalProperty);
			}

			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rentalProperties;
	}

	public static void saveAll(List<RentalProperty> rentalProperties) throws Exception {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			String sql = "INSERT INTO rental_property VALUES ";
			StringBuffer temp = new StringBuffer();
			for (RentalProperty rentalProperty : rentalProperties) {
				temp.append("(");
				temp.append(String.format("'%s',", rentalProperty.getPropertyId()));
				temp.append(String.format("'%s',", rentalProperty.getStreetNum()));
				temp.append(String.format("'%s',", rentalProperty.getStreetName()));
				temp.append(String.format("'%s',", rentalProperty.getSuburb()));
				temp.append(String.format("'%d',", rentalProperty.getBedroomsNum()));
				temp.append(String.format("'%s',", rentalProperty.getPropertyType()));
				temp.append(String.format("'%s',", rentalProperty.getStatus()));
				temp.append(String.format("'%s',", rentalProperty.getImageName()));
				temp.append(String.format("'%s',", rentalProperty.getDescription()));
				temp.append(String.format("'%s'", rentalProperty.getCreateTime()));
				temp.append("),");
			}
			temp.delete(temp.length() - 1, temp.length());
			sql = sql + temp.toString();
			statement.execute(sql);
		} catch (Exception e) {
			throw e;
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

	public static List<RentalProperty> getAllRentalPropertyWithConditon(String condition) {
		ArrayList<RentalProperty> rentalProperties = new ArrayList<>();
		Statement statement = null;

		try {
			statement = connection.createStatement();
			String sql = "SELECT * FROM rental_property WHERE " + condition + " ORDER BY create_time DESC";

			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				RentalProperty rentalProperty = null;
				if (resultSet.getString(6).equals(PropertyType.APARTMENT)) {
					rentalProperty = new Apartment();
					rentalProperty.setPropertyId(resultSet.getString(1));
					rentalProperty.setStreetNum(resultSet.getString(2));
					rentalProperty.setStreetName(resultSet.getString(3));
					rentalProperty.setSuburb(resultSet.getString(4));
					rentalProperty.setBedroomsNum(resultSet.getInt(5));
					rentalProperty.setPropertyType(PropertyType.APARTMENT);
					rentalProperty.setStatus(resultSet.getString(7));
					rentalProperty.setImageName(resultSet.getString(8));
					rentalProperty.setDescription(resultSet.getString(9));

					((Apartment) rentalProperty)
							.setPerDayFee(ApartmentDao.findPerDayFeeByPropertyId(rentalProperty.getPropertyId()));
				} else if (resultSet.getString(6).equals(PropertyType.PREMIUM_SUITE)) {
					rentalProperty = new PremiumSuite();
					rentalProperty.setPropertyId(resultSet.getString(1));
					rentalProperty.setStreetNum(resultSet.getString(2));
					rentalProperty.setStreetName(resultSet.getString(3));
					rentalProperty.setSuburb(resultSet.getString(4));
					rentalProperty.setBedroomsNum(resultSet.getInt(5));
					rentalProperty.setPropertyType(PropertyType.PREMIUM_SUITE);
					rentalProperty.setStatus(resultSet.getString(7));
					rentalProperty.setImageName(resultSet.getString(8));
					rentalProperty.setDescription(resultSet.getString(9));

					((PremiumSuite) rentalProperty)
							.setPerDayFee(PremiumSuiteDao.findPerDayFeeByPropertyId(rentalProperty.getPropertyId()));
					((PremiumSuite) rentalProperty).setLastMaintenance(
							PremiumSuiteDao.findLastMaintenanceByPropertyId(rentalProperty.getPropertyId()));
				}
				rentalProperties.add(rentalProperty);
			}

			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rentalProperties;
	}
}
