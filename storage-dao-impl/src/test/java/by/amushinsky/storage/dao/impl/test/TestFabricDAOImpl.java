package by.amushinsky.storage.dao.impl.test;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.dao.api.FabricDAO;
import by.amushinsky.storage.dao.api.FabricStockDAO;
import by.amushinsky.storage.dao.config.DataConfig;

@ActiveProfiles("dev")
@ContextConfiguration(classes = DataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestFabricDAOImpl {
	@Autowired
	private FabricDAO fabricDAO;
	
	@Autowired
	private FabricStockDAO fabricStockDAO;
	
	@Test
	public void testContext() {
		Assert.assertNotNull(fabricDAO);
	}
	
	@Test
	public void testCreateFabric() {
		fabricDAO.create(new Fabric("TEST"));
		
	}
}
