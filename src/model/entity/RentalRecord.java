package model.entity;

import model.constant.PropertyType;
import model.util.DateTime;

public class RentalRecord {
	private String customerId;
	private RentalProperty rentalProperty;
	private DateTime rentDate;
	private String recordId;
	private DateTime actualReturnDate;
	private DateTime estimatedReturnDate;
	private double rentalFee;
	private double lateFee;


	public RentalRecord() {

	}

	public RentalRecord(RentalProperty rentalProperty, String customerId,DateTime rentDate, DateTime estimatedReturnDate) {
		this.customerId = customerId;
		this.rentalProperty = rentalProperty;
		this.rentDate=rentDate;
		this.estimatedReturnDate = estimatedReturnDate;
		this.recordId=rentalProperty.getPropertyId()+"_"+customerId+"_"+rentDate.getEightDigitDate();
	}

	public RentalRecord(String customerId, RentalProperty rentalProperty, DateTime rentDate, String recordId,
			DateTime actualReturnDate, DateTime estimatedReturnDate, double rentalFee, double lateFee) {
		this.customerId = customerId;
		this.rentalProperty = rentalProperty;
		this.rentDate = rentDate;
		this.recordId = recordId;
		this.actualReturnDate = actualReturnDate;
		this.estimatedReturnDate = estimatedReturnDate;
		this.rentalFee = rentalFee;
		this.lateFee = lateFee;
	}



	public RentalRecord(RentalProperty rentalProperty, String customerId, DateTime rentDate) {
		this.customerId=customerId;
		this.rentDate=rentDate;
		this.rentalProperty=rentalProperty;
		this.recordId=rentalProperty.getPropertyId()+"_"+customerId+"_"+rentDate.getEightDigitDate();
	}

	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public RentalProperty getRentalProperty() {
		return rentalProperty;
	}
	public void setRentalProperty(RentalProperty rentalProperty) {
		this.rentalProperty = rentalProperty;
	}
	public DateTime getRentDate() {
		return rentDate;
	}
	public void setRentDate(DateTime rentDate) {
		this.rentDate = rentDate;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public DateTime getActualReturnDate() {
		return actualReturnDate;
	}
	public void setActualReturnDateAndFees(DateTime actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
		int diffDays=DateTime.diffDays(this.actualReturnDate, this.estimatedReturnDate);
		if(this.rentalProperty.propertyType.equals(PropertyType.APARTMENT)) {
			if(diffDays>0) {
				this.rentalFee=DateTime.diffDays(this.estimatedReturnDate, this.rentDate)*((Apartment)this.rentalProperty).getPerDayFee();
				this.lateFee=DateTime.diffDays(this.actualReturnDate, this.estimatedReturnDate)*((Apartment)this.rentalProperty).getPerDayFee()*1.15;
			}else {
				this.lateFee=0;
				this.rentalFee=DateTime.diffDays(this.actualReturnDate, this.rentDate)*((Apartment)this.rentalProperty).getPerDayFee();
			}
		}else if(this.rentalProperty.propertyType.equals(PropertyType.PREMIUM_SUITE)) {
			if(diffDays>0) {
				this.rentalFee=DateTime.diffDays(this.estimatedReturnDate, this.rentDate)*((PremiumSuite)this.rentalProperty).getPerDayFee();
				this.lateFee=DateTime.diffDays(this.actualReturnDate, this.estimatedReturnDate)*662;
			}else {
				this.lateFee=0;
				this.rentalFee=DateTime.diffDays(this.actualReturnDate, this.rentDate)*((PremiumSuite)this.rentalProperty).getPerDayFee();
			}
		}
	}
	public void setActualReturnDate(DateTime actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}
	
	public DateTime getEstimatedReturnDate() {
		return estimatedReturnDate;
	}
	public void setEstimatedReturnDate(DateTime estimatedReturnDate) {
		this.estimatedReturnDate = estimatedReturnDate;
	}
	public double getRentalFee() {
		return rentalFee;
	}
	public void setRentalFee(double rentalFee) {
		this.rentalFee = rentalFee;
	}
	public double getLateFee() {
		return lateFee;
	}
	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}

	public String getDetail() {
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append(this.getRecordId()+":");
		stringBuffer.append(this.getRentDate().getFormattedDate()+":");
		stringBuffer.append(this.getEstimatedReturnDate().getFormattedDate()+":");
		if(this.actualReturnDate!=null) {
			stringBuffer.append(this.getActualReturnDate().getFormattedDate()+":");
			stringBuffer.append(String.format("%.2f", this.getRentalFee())+":");
			stringBuffer.append(String.format("%.2f", this.getLateFee()));
		}else {
			stringBuffer.append("none:none:none");
		}
		return new String(stringBuffer);
	}

}
