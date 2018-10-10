package model.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.constant.PropertyStatus;
import model.entity.RentalProperty;
import model.exception.PerformMaintenanceException;

public class RentalPropertyDaoTest {
	RentalProperty rentalProperty;
	@Before
	public void setUp() throws Exception {
		rentalProperty=new RentalProperty();
		rentalProperty.setPropertyId("A_700BM");
		rentalProperty.setStatus(PropertyStatus.BEING_RENTED);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAllRentalProperty() {
		System.out.println(RentalPropertyDao.getAllRentalProperty());
	}

	@Test
	public void testUpdateStatus() throws PerformMaintenanceException{
		RentalPropertyDao.updateStatus(rentalProperty);
	}
}
