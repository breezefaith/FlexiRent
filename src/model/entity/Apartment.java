package model.entity;

import model.constant.PropertyStatus;
import model.constant.PropertyType;
import model.dao.RentalPropertyDao;
import model.dao.RentalRecordDao;
import model.exception.PerformMaintenanceException;
import model.exception.RentException;
import model.exception.ReturnException;
import model.util.DateTime;

public class Apartment extends RentalProperty {

	private double perDayFee;
	private double latePerDayFee;
	
	public Apartment() {
	}

	public Apartment(String propertyId, String streetNum, String streetName, String suburb, String propertyType,
			int bedroomNum) {
		this.propertyId = "Apartment".charAt(0) + "_" + streetNum + streetName + suburb;
		this.streetNum = streetNum;
		this.streetName = streetName;
		this.suburb = suburb;
		this.propertyType = PropertyType.APARTMENT;
		this.bedroomsNum = bedroomNum;
		status = PropertyStatus.ApartmentStatus.AVAILABLE;
	}

	public double getPerDayFee() {
		return perDayFee;
	}

	public void setPerDayFee(double perDayFee) {
		this.perDayFee = perDayFee;
		this.latePerDayFee=1.15*perDayFee;
	}

	public double getLatePerDayFee() {
		return latePerDayFee;
	}

	public void setLatePerDayFee(double latePerDayFee) {
		this.latePerDayFee = latePerDayFee;
	}

	public void rent(String customerId, DateTime rentDate, DateTime estimatedReturnDate) throws RentException, PerformMaintenanceException {
		if (rentDate.getTime() >= new DateTime().getTime() && status.equals(PropertyStatus.ApartmentStatus.AVAILABLE)) {
			int diffDays=DateTime.diffDays(estimatedReturnDate, rentDate);
			if (rentDate.DayOfWeek().equals("Friday") || rentDate.DayOfWeek().equals("Saturday")) {
				if (diffDays >= 3 && diffDays <= 28) {
					status = PropertyStatus.ApartmentStatus.BEING_RENTED;
					RentalRecord latestRecord=new RentalRecord(this,customerId,rentDate,estimatedReturnDate);
					
					RentalRecordDao.save(latestRecord);
					RentalPropertyDao.updateStatus(this);
				} else {
					throw new RentException(1);
				}
			} else {
				if (diffDays >= 2 && diffDays <= 28) {
					status = PropertyStatus.ApartmentStatus.BEING_RENTED;
					RentalRecord latestRecord=new RentalRecord(this, customerId, rentDate,estimatedReturnDate);
					
					RentalRecordDao.save(latestRecord);
					RentalPropertyDao.updateStatus(this);
				} else {
					throw new RentException(2);
				}
			}
		} else {
			throw new RentException(3);
		}
	}

	public void returnProperty(DateTime returnDate) throws ReturnException, PerformMaintenanceException {
		RentalRecord latestRecord=RentalRecordDao.findLatestRecordByProperty(this);
		if (returnDate.getTime() > latestRecord.getRentDate().getTime() && status.equals(PropertyStatus.ApartmentStatus.BEING_RENTED)) {
			latestRecord.setActualReturnDateAndFees(returnDate);
			status = PropertyStatus.ApartmentStatus.AVAILABLE;
			
			RentalRecordDao.update(latestRecord);
			RentalPropertyDao.updateStatus(this);
		} else {
			throw new ReturnException();
		}
	}


	public String getDetails() {
		return this.getPropertyId()+":"+this.getStreetNum()+":"+this.getStreetName()+":"+this.getSuburb()+":"+this.getBedroomsNum()+":"
				+this.getPropertyType()+":"+this.getStatus()+":"+this.getImageName()+":"+this.getDescription();
	}

}
