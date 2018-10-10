package model.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.entity.RentalProperty;

public class ApartmentDaoTest {
	private RentalProperty rentalProperty;
	@Before
	public void setUp() throws Exception {
		rentalProperty=new RentalProperty();
		rentalProperty.setPropertyId("A_700BM");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindPerDayFeeByPropertyId() {
		System.out.println(ApartmentDao.findPerDayFeeByPropertyId(rentalProperty.getPropertyId()));
	}

}
