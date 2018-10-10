package model.entity;

import model.constant.PropertyStatus;
import model.dao.PremiumSuiteDao;
import model.dao.RentalPropertyDao;
import model.dao.RentalRecordDao;
import model.exception.CompleteMaintenanceException;
import model.exception.PerformMaintenanceException;
import model.exception.RentException;
import model.exception.ReturnException;
import model.util.DateTime;

public class PremiumSuite extends RentalProperty {
	private DateTime lastMaintenance;
	private double perDayFee;

	public PremiumSuite() {
		
	}

	public PremiumSuite(String propertyId, String streetNum, String streetName, String Suburb, String propertyType,
			DateTime lastMaintenance) {
		this.propertyId = propertyId;
		this.streetNum = streetNum;
		this.streetName = streetName;
		this.suburb = Suburb;
		this.propertyType = propertyType;
		this.lastMaintenance = lastMaintenance;
		this.propertyId = "S_" + streetNum + streetName + Suburb;
		status = PropertyStatus.AVAILABLE;
		perDayFee = 554;
	}	
	
	public DateTime getLastMaintenance() {
		return lastMaintenance;
	}

	public void setLastMaintenance(DateTime lastMaintenance) {
		this.lastMaintenance = lastMaintenance;
	}

	public double getPerDayFee() {
		return perDayFee;
	}

	public void setPerDayFee(double perDayFee) {
		this.perDayFee = perDayFee;
	}

	public void rent(String customerId, DateTime rentDate, DateTime estimatedReturnDate) throws RentException, PerformMaintenanceException {
		if (status.equals(PropertyStatus.PremiumSuiteStatus.BEING_RENTED) || status.equals(PropertyStatus.PremiumSuiteStatus.UNDER_MAINTENANCE)) {
			throw new RentException(3);
		}
		if (rentDate.getTime()>=new DateTime().getTime()&&status.equals(PropertyStatus.AVAILABLE)
				&& DateTime.diffDays(estimatedReturnDate, rentDate) < DateTime.diffDays((new DateTime(lastMaintenance, 10)), lastMaintenance)) {
			status = PropertyStatus.PremiumSuiteStatus.BEING_RENTED;
			RentalRecord record=new RentalRecord(this, customerId, rentDate,estimatedReturnDate);
			
			RentalRecordDao.save(record);
			RentalPropertyDao.updateStatus(this);
		}else {
			throw new RentException(0);
		}

	}

	public void returnProperty(DateTime returnDate) throws ReturnException, PerformMaintenanceException {
		RentalRecord record=RentalRecordDao.findLatestRecordByProperty(this);
		if (returnDate.getTime() < record.getRentDate().getTime()) {
			throw new ReturnException();
		} else {
			status = PropertyStatus.AVAILABLE;
			record.setActualReturnDateAndFees(returnDate);
			
			RentalRecordDao.update(record);
			RentalPropertyDao.updateStatus(this);
		}
	}

	public void performMaintenance() throws PerformMaintenanceException {
		if (status.equals(PropertyStatus.PremiumSuiteStatus.BEING_RENTED)) {
			throw new PerformMaintenanceException();
		} else {
			status = PropertyStatus.PremiumSuiteStatus.UNDER_MAINTENANCE;
			RentalPropertyDao.updateStatus(this);
		}
	}

	public void completeMaintenance(DateTime completeDate) throws CompleteMaintenanceException, PerformMaintenanceException {
		if (status.equals(PropertyStatus.PremiumSuiteStatus.UNDER_MAINTENANCE)) {
			status = PropertyStatus.AVAILABLE;
			lastMaintenance = completeDate;
			PremiumSuiteDao.updateLastMaintenance(this);
			RentalPropertyDao.updateStatus(this);
		} else {
			throw new CompleteMaintenanceException("this property isn't under maintenance.");
		}
	}

	public String getDetails() {
		return this.getPropertyId()+":"+this.getStreetNum()+":"+this.getStreetName()+":"+this.getSuburb()+":"+this.getBedroomsNum()+":"
				+this.getPropertyType()+":"+this.getStatus()+":"+this.getImageName()+":"+this.getDescription();
	}
}
