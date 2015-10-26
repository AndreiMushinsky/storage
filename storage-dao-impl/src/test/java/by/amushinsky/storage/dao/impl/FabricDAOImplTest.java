package by.amushinsky.storage.dao.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.core.FabricStock;
import by.amushinsky.storage.dao.api.FabricsDAO;
import by.amushinsky.storage.dao.config.DataConfig;
import by.amushinsky.storage.dao.fixtures.FabricDAOFixtures;
import by.amushinsky.storage.dao.sql.TableConstants.Fabrics;

@ActiveProfiles("dev")
@ContextConfiguration(classes = { DataConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FabricDAOImplTest {

	@Autowired
	private FabricDAOFixtures fixtures;	
	
	@Autowired
	private FabricsDAO fabricsDAO;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	@Sql("classpath:/sql/insert_fabric.sql")
	public void testIsNameReserved() {
		Fabric fabric = fixtures.fabric();
		String reservedName = fabric.getName();
		assertTrue("name is reserved", fabricsDAO.isNameReserved(reservedName));
		String unReservedName = fixtures.unReservedName();
		assertFalse("name is not reserved", fabricsDAO.isNameReserved(unReservedName));
	}
	
	@Test
	public void testInsertFabric() {
		Fabric fabric = fixtures.fabric();
		
		// Assert that table is empty
		int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, Fabrics.TABLE_NAME);
		assertTrue("table is empty at start", rows == 0);

		// Assert that inserted object has not null id
		assertTrue("object has null id", fabric.getId() == 0);

		// Insert object in empty table
		fabricsDAO.insertFabric(fabric);

		// Assert that table has only one row after inserting
		rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, Fabrics.TABLE_NAME);
		assertTrue("table has only one row after inserting", rows == 1);

		// Assert that inserted object has not null id
		assertTrue("inserted object has not null id", fabric.getId() != 0);

		// Assert that object and entry in DB are the same entity
		String condition = MessageFormat.format("{0} = {1, number, integer} AND {2} = ''{3}''", 
				Fabrics.ID, fabric.getId(), Fabrics.NAME, fabric.getName());
		rows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, Fabrics.TABLE_NAME, condition);
		assertTrue("object and entry in DB are the same entity", rows == 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertNullReference() {
		fabricsDAO.insertFabric(null);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testInsertFabricWithNullFields() {
		fabricsDAO.insertFabric(new Fabric(null));
	}


	@Test
	@Sql("classpath:/sql/insert_fabrics.sql")
	public void testSelectAllFabrics() {
		List<Fabric> expected = fixtures.fabrics();
		
		// Assert that table is not empty
		int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, Fabrics.TABLE_NAME);
		assertTrue("table has " + expected.size(), rows == expected.size());
		
		// Assert that method return list of objects in right order
		List<Fabric> actual = fabricsDAO.selectAllFabrics();
		actual.forEach(obj -> obj.setId(0));
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testSelectAllFabricsOnEmptyDB() {
		assertTrue(fabricsDAO.selectAllFabrics().isEmpty());
	}

	@Test
	@Sql("classpath:/sql/insert_fabrics.sql")
	@Sql("classpath:/sql/insert_entries_for_these_fabrics.sql")
	public void testSelectAllFabricStocks() {
		List<FabricStock> expected = fixtures.stocks();
		List<FabricStock> actual = fabricsDAO.selectAllFabricStocks();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	@Sql("classpath:/sql/insert_fabrics.sql")
	public void testSelectAllFabricStocksWithoutEntries() {
		List<FabricStock> expected = fixtures.emptyStocks();
		List<FabricStock> actual = fabricsDAO.selectAllFabricStocks();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testSelectAllFabricStocksOnEmptyDB() {
		assertTrue(fabricsDAO.selectAllFabricStocks().isEmpty());
	}

	@Test
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entries_for_this_fabric.sql")
	public void testSelectFabricAmountById() {
		Fabric fabric = fixtures.fabricWithId();
		BigDecimal expected = fixtures.fabricAmount(fabric.getId());
		BigDecimal actual = fabricsDAO.selectFabricAmountById(fabric.getId());
		assertTrue("amounts are equal", expected.compareTo(actual) == 0);
	}
	
	@Test
	@Sql("classpath:/sql/insert_fabric.sql")
	public void testSelectFabricAmountByIdWithoutEntries() {
		Fabric fabric = fixtures.fabricWithId();
		BigDecimal expected = new BigDecimal(0);
		BigDecimal actual = fabricsDAO.selectFabricAmountById(fabric.getId());
		assertTrue("amounts are equal", expected.compareTo(actual) == 0);
	}
	
	@Test
	public void testSelectFabricAmountByIdWhenNoSuchId() {
		Fabric fabric = fixtures.fabricWithId();
		BigDecimal expected = new BigDecimal(0);
		BigDecimal actual = fabricsDAO.selectFabricAmountById(fabric.getId());
		assertTrue("amounts are equal", expected.compareTo(actual) == 0);
	}

	@Test
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entries_for_this_fabric.sql")
	public void testSelectTotalFabricAmount() {
		Fabric fabric = fixtures.fabricWithId();
		BigDecimal expected = fixtures.fabricAmount(fabric.getId());
		BigDecimal actual = fabricsDAO.selectTotalFabricAmount();
		assertTrue("amounts are equal", expected.compareTo(actual) == 0);
	}

}
