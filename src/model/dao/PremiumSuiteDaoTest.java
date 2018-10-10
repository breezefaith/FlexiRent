package model.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.entity.PremiumSuite;
import model.exception.CompleteMaintenanceException;
import model.util.DateTime;

public class PremiumSuiteDaoTest {
	private PremiumSuite premiumSuite;
	@Before
	public void setUp() throws Exception {
		premiumSuite=new PremiumSuite();
		premiumSuite.setPropertyId("S_633WS");
		premiumSuite.setLastMaintenance(new DateTime("29092018"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindPerDayFeeByPropertyId() {
		System.out.println(PremiumSuiteDao.findPerDayFeeByPropertyId(premiumSuite.getPropertyId()));
	}

	@Test
	public void testUpdateLastCompleteMaintenance() throws CompleteMaintenanceException{
		PremiumSuiteDao.updateLastMaintenance(premiumSuite);
	}
}
