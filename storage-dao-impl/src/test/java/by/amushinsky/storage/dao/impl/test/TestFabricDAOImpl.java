package by.amushinsky.storage.dao.impl.test;


import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.dao.api.FabricDAO;
import by.amushinsky.storage.dao.config.DataConfig;
import static by.amushinsky.storage.dao.impl.test.Etalons.*;

import java.util.List;

@ActiveProfiles("dev")
@ContextConfiguration(classes = DataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestFabricDAOImpl {
	@Autowired
	private FabricDAO fabricDAO;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void testContext() {
		Assert.assertNotNull(fabricDAO);
	}
	
	@Test
	public void testCreateFabric() {
		fabricDAO.create(ETALON_FABRIC);
		Fabric actualFabric = jdbcTemplate.queryForObject("SELECT * FROM Fabrics WHERE name='test'", 
				(rs, rowNum) -> new Fabric(rs.getInt(1), rs.getString(2)));
		Assert.assertEquals(ETALON_FABRIC.getName(), actualFabric.getName());
		jdbcTemplate.update("DELETE FROM Fabrics WHERE name='test'");
	}
	
	@Test
	public void testGetFabric(){
		List<Fabric> actualFabrics = fabricDAO.getFabrics();
		Assert.assertTrue(ETALON_FABRICS.containsAll(actualFabrics) && actualFabrics.containsAll(ETALON_FABRICS));
	}
	
	@After
	public void rollbackChanges(){
		jdbcTemplate.update("DELETE FROM Fabrics WHERE name='test'");
	}
	
}
