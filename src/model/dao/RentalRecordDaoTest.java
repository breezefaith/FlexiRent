package model.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.entity.RentalProperty;
import model.entity.RentalRecord;
import model.exception.ReturnException;
import model.util.DateTime;

public class RentalRecordDaoTest {
	private RentalProperty rentalProperty;

	@Before
	public void setUp() throws Exception {
		rentalProperty = new RentalProperty();
		rentalProperty.setPropertyId("A_701BM");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSave() throws Exception {
		RentalRecord record = new RentalRecord(rentalProperty, "CUS700", new DateTime("01102018"),
				new DateTime("03102018"));
		RentalRecordDao.save(record);

	}

	@Test
	public void testFindLatestRecordByProperty() {
		System.out.println(RentalRecordDao.findLatestRecordByProperty(rentalProperty).getRentalProperty().getPropertyType());
	}

	@Test
	public void testUpdate() throws ReturnException {
		RentalRecord record=RentalRecordDao.findLatestRecordByProperty(rentalProperty);
		record.setActualReturnDateAndFees(new DateTime("03102018"));
		System.out.println(record.getDetail());
		RentalRecordDao.update(record);
	}
}
