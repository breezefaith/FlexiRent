package model.constant;

public interface PropertyStatus {
	public static final String AVAILABLE = "currently available for rent";
	public static final String BEING_RENTED = "being rented";
	public static final String UNDER_MAINTENANCE = "under maintenance";

	public interface ApartmentStatus extends PropertyStatus {
	}

	public interface PremiumSuiteStatus extends PropertyStatus {
	}

}
