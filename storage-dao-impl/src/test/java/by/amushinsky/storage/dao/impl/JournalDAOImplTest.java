package by.amushinsky.storage.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.dao.api.JournalDAO;
import by.amushinsky.storage.dao.config.DataConfig;
import by.amushinsky.storage.dao.fixtures.JournalDAOFixtures;
import by.amushinsky.storage.dao.sql.TableConstants.Fabrics;
import by.amushinsky.storage.dao.sql.TableConstants.Journal;

@ActiveProfiles("dev")
@ContextConfiguration(classes = { DataConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class JournalDAOImplTest {
	
	@Autowired
	private JournalDAOFixtures fixtures;	
	
	@Autowired
	private JournalDAO journalDAO;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Test
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entry_for_this_fabric.sql")
	public void testDeleteJournalEntryById() throws ParseException {
		JournalEntry entry = fixtures.entryWithId(1);
		
		// Assert that table is not empty
		int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, Journal.TABLE_NAME);
		assertTrue("table is empty at start", rows == 1);
		
		journalDAO.deleteJournalEntryById(entry.getId());
		
		// Assert that table is empty
		rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, Journal.TABLE_NAME);
		assertTrue("table is empty at start", rows == 0);
		
	}
	
	@Test
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entry_for_this_fabric.sql")
	public void testDeleteJournalEntryByIdNotExisting() throws ParseException {
		JournalEntry entry = fixtures.entryWithId(2);
		
		// Assert that table is not empty
		int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, Journal.TABLE_NAME);
		assertTrue("table is empty at start", rows == 1);
		
		journalDAO.deleteJournalEntryById(entry.getId());
		
		// Assert that table is still not empty
		rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, Journal.TABLE_NAME);
		assertTrue("table is empty at start", rows == 1);
		
	}
	
	@Test
	@Sql("classpath:/sql/insert_fabric.sql")
	public void testInsertJournalEntry() throws ParseException {
		JournalEntry entry = fixtures.entry();
		
		// Assert that table is empty
		int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, Journal.TABLE_NAME);
		assertTrue("table is empty at start", rows == 0);

		// Assert that inserted object has not null id
		assertTrue("object has null id", entry.getId() == 0);

		// Insert object in empty table
		journalDAO.insertJournalEntry(entry);

		// Assert that table has only one row after inserting
		rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, Journal.TABLE_NAME);
		assertTrue("table has only one row after inserting", rows == 1);

		// Assert that inserted object has not null id
		assertTrue("inserted object has not null id", entry.getId() != 0);

		// Assert that object and entry in DB are the same entity
		String condition = 
				MessageFormat.format("{0} = {1, number, integer} AND {2} = ''{3}'' " + 
				"AND {4} = {5, number, integer} AND {6} = {7} AND {8} = {9}", 
				Journal.ID, entry.getId(), Journal.DATE, formatter.format(entry.getDate()),
				Journal.FABRIC_ID, entry.getFabricId(), Journal.IS_DR, entry.getIsDr(),
				Journal.AMOUNT, entry.getAmount().toPlainString()); 
		rows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, Journal.TABLE_NAME, condition);
		assertTrue("object and entry in DB are the same entity", rows == 1);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testInsertJournalEntryWithoutFabric() throws ParseException {
		JournalEntry entry = fixtures.entry();
		
		// Assert that tables are empty
		int journalRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, Journal.TABLE_NAME);
		int fabricsRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, Fabrics.TABLE_NAME);
		assertTrue("tables are empty at start", journalRows == 0 && fabricsRows == 0);
		
		journalDAO.insertJournalEntry(entry);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertJoutnalEntryWithNullRef() {
		journalDAO.insertJournalEntry(null);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testInsertJournalEntryWithNullFields() {
		JournalEntry nullContainingEntry = new JournalEntry();
		journalDAO.insertJournalEntry(nullContainingEntry);
	}
	
	@Test
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entry_for_this_fabric.sql")
	public void testIsIdReserved() throws ParseException {
		JournalEntry entry = fixtures.entryWithId(1);
		int reservedId = entry.getId();
		assertTrue("id is reserved", journalDAO.isIdReserved(reservedId));
		int notReservedId = fixtures.notReservedId();
		assertFalse("name is not reserved", journalDAO.isIdReserved(notReservedId));
	}
	
	@Test
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entries_for_this_fabric.sql")
	public void testSelectJournalEntries() throws ParseException {
		TimePeriod empty = fixtures.emptyPeriod();
		List<JournalEntry> actual = journalDAO.selectJournalEntries(empty);
		assertTrue("no entries in empty period", actual.isEmpty());
		
		TimePeriod middle = fixtures.middlePeriod();
		List<JournalEntry> expected = fixtures.entries(middle);
		actual = journalDAO.selectJournalEntries(middle);
		actual.forEach(entry -> entry.setId(0));
		assertEquals("middle selection", expected, actual);
		
		TimePeriod whole = fixtures.wholePeriod();
		expected = fixtures.entries(whole);
		actual = journalDAO.selectJournalEntries(whole);
		actual.forEach(entry -> entry.setId(0));
		assertEquals("whole selection", expected, actual);
	}
	
	@Test(expected=IllegalArgumentException.class)
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entries_for_this_fabric.sql")
	public void testSelectJournalEntriesWithNullRef() {
		journalDAO.selectJournalEntries(null);
	}
	
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entries_for_this_fabric.sql")
	public void testSelectJournalEntriesWithNullFields() throws ParseException {
		TimePeriod period = new TimePeriod(null, null);
		List<JournalEntry> entries = journalDAO.selectJournalEntries(period);
		assertTrue(entries.isEmpty());
		
		period = new TimePeriod(fixtures.wholePeriod().getFromDate(), null);
		entries = journalDAO.selectJournalEntries(period);
		assertTrue(entries.isEmpty());
		
		period = new TimePeriod(null, fixtures.wholePeriod().getToDate());
		entries = journalDAO.selectJournalEntries(period);
		assertTrue(entries.isEmpty());
	}

	@Test
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entry_for_this_fabric.sql")
	public void testSelectJournalEntryById() throws ParseException {
		JournalEntry expected = fixtures.entryWithId(1);
		JournalEntry actual = journalDAO.selectJournalEntryById(expected.getId());
		assertEquals("selection by id", expected, actual);
	}
	
	@Sql("classpath:/sql/insert_fabric.sql")
	@Test(expected=EmptyResultDataAccessException.class)
	public void testSelectJournalEntryByIdNotExisting() throws ParseException {
		journalDAO.selectJournalEntryById(1);
	}

	@Test
	@Sql("classpath:/sql/insert_fabrics.sql")
	@Sql("classpath:/sql/insert_entries_for_these_fabrics.sql")
	public void testSelectFabricMovements() throws ParseException {
		TimePeriod period = fixtures.movementPeriod();
		List<FabricMovement> expected = fixtures.movements();
		List<FabricMovement> actual = journalDAO.selectFabricMovements(period);
		assertEquals("movements selection", expected, actual);
	}
	
	@Test
	@Sql("classpath:/sql/insert_fabrics.sql")
	@Sql("classpath:/sql/insert_entries_for_these_fabrics.sql")
	public void testSelectFabricMovementsEmptyPeriod() throws ParseException {
		TimePeriod period = fixtures.emptyPeriod();
		List<FabricMovement> expected = fixtures.emptyMovements();
		List<FabricMovement> actual = journalDAO.selectFabricMovements(period);
		assertEquals("empty movements selection", expected, actual);
	}

	@Test
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entries_for_this_fabric.sql")
	public void testSelectTotalFabricMovement() throws ParseException {
		TimePeriod whole = fixtures.wholePeriod();
		FabricMovement expected = fixtures.totalMovement(whole);
		FabricMovement actual = journalDAO.selectTotalFabricMovement(whole);
		assertEquals("selection whole movement", expected, actual);
		
		TimePeriod middle = fixtures.middlePeriod();
		expected = fixtures.totalMovement(middle);
		actual = journalDAO.selectTotalFabricMovement(middle);
		assertEquals("selection middle movement", expected, actual);
		
		TimePeriod empty = fixtures.emptyPeriod();
		expected = fixtures.totalMovement(empty);
		actual = journalDAO.selectTotalFabricMovement(empty);
		assertEquals("selection empty movement", expected, actual);
	}
	
	@Test(expected=IllegalArgumentException.class)
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entries_for_this_fabric.sql")
	public void testSelectTotalFabricMovementWithNullRef() throws ParseException {
		journalDAO.selectTotalFabricMovement(null);
	}
	
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entries_for_this_fabric.sql")
	public void testSelectTotalFabricMovementWithNullField() throws ParseException {
		TimePeriod timePeriod = new TimePeriod(null, null);
		FabricMovement actual = journalDAO.selectTotalFabricMovement(timePeriod);
		FabricMovement expected = fixtures.totalMovement(timePeriod);
		assertEquals("expecting zero fields in total", expected, actual);
		
		timePeriod = new TimePeriod(null, fixtures.wholePeriod().getToDate());
		actual = journalDAO.selectTotalFabricMovement(timePeriod);
		expected = fixtures.totalMovement(timePeriod);
		assertEquals("expecting zero fields in total", expected, actual);
		
		timePeriod = new TimePeriod(fixtures.wholePeriod().getFromDate(), null);
		actual = journalDAO.selectTotalFabricMovement(timePeriod);
		expected = fixtures.totalMovement(timePeriod);
		assertEquals("expecting zero fields in total", expected, actual);
	}

	@Test
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entry_for_this_fabric.sql")
	public void testUpdateJournalEntry() throws ParseException {
		JournalEntry entry = fixtures.entryWithId(1);
		
		String condition = 
				MessageFormat.format("{0} = {1, number, integer} AND {2} = ''{3}'' " + 
				"AND {4} = {5, number, integer} AND {6} = {7} AND {8} = {9}", 
				Journal.ID, entry.getId(), Journal.DATE, formatter.format(entry.getDate()),
				Journal.FABRIC_ID, entry.getFabricId(), Journal.IS_DR, entry.getIsDr(),
				Journal.AMOUNT, entry.getAmount().toPlainString()); 
		int rows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, Journal.TABLE_NAME, condition);
		assertTrue("object and entry in DB are the same entity", rows == 1);
		
		entry.setDate(new Date());
		entry.setAmount(new BigDecimal(0));
		entry.setIsDr(false);
		
		journalDAO.updateJournalEntry(entry);
		
		condition = 
				MessageFormat.format("{0} = {1, number, integer} AND {2} = ''{3}'' " + 
				"AND {4} = {5, number, integer} AND {6} = {7} AND {8} = {9}", 
				Journal.ID, entry.getId(), Journal.DATE, formatter.format(entry.getDate()),
				Journal.FABRIC_ID, entry.getFabricId(), Journal.IS_DR, entry.getIsDr(),
				Journal.AMOUNT, entry.getAmount().toPlainString()); 
		rows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, Journal.TABLE_NAME, condition);
		assertTrue("object and entry in DB are the same entity", rows == 1);
	}
	
	
	@Test
	@Sql("classpath:/sql/insert_fabric.sql")
	public void testUpdateJournalEntryNotExisting() throws ParseException {
		JournalEntry entry = fixtures.entryWithId(2);
		
		entry.setDate(new Date());
		entry.setAmount(new BigDecimal(0));
		entry.setIsDr(false);
		
		journalDAO.updateJournalEntry(entry);
	}
	
	@Sql("classpath:/sql/insert_fabric.sql")
	@Sql("classpath:/sql/insert_entry_for_this_fabric.sql")
	@Test(expected = DataIntegrityViolationException.class)
	public void testUpdateJournalEntryNotExistingFabricId() throws ParseException {
		JournalEntry entry = fixtures.entryWithId(1);
		
		entry.setFabricId(2);
		
		journalDAO.updateJournalEntry(entry);
	}
	
	@Test
	@Sql("classpath:/sql/insert_fabrics.sql")
	@Sql("classpath:/sql/insert_entry_for_this_fabric.sql")
	public void testUpdateJournalEntryExistingFabricId() throws ParseException {
		JournalEntry entry = fixtures.entryWithId(1);
		
		entry.setFabricId(2);
		
		journalDAO.updateJournalEntry(entry);
	}

}
