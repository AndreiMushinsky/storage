package by.amushinsky.storage.dao.impl.test;

import java.util.List;

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

import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.dao.api.JournalEntryDAO;
import by.amushinsky.storage.dao.config.DataConfig;
import static by.amushinsky.storage.dao.impl.test.Etalons.*;

@ActiveProfiles("dev")
@ContextConfiguration(classes = DataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestJournalEntryDAOImpl {
	@Autowired
	private JournalEntryDAO journalEntryDAO;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Test
	public void testContext() {
		Assert.assertNotNull(journalEntryDAO);
	}

	@Test
	public void testGetEntries() {
		List<JournalEntry> actualEntries = journalEntryDAO.getEntriesForPeriod(ETALON_TIMEPERIOD);
		Assert.assertEquals(ETALON_ENTRIES, actualEntries);
	}

	@Test
	public void testCreate() {
		journalEntryDAO.create(ETALON_CREATE_ENTRY);
		boolean isCreated = jdbcTemplate
				.query("SELECT * FROM Journal WHERE date=? AND fabric_id=? AND is_dr=? AND amount=?",
						(rs, rowNum) -> new JournalEntry(rs.getDate("date"), rs.getInt("fabric_id"), rs.getInt("is_dr"),
								rs.getBigDecimal("amount")),
						ETALON_CREATE_ENTRY.getDate(), ETALON_CREATE_ENTRY.getFabricId(), ETALON_CREATE_ENTRY.getIsDr(),
						ETALON_CREATE_ENTRY.getAmount())
				.size() == 1;

		Assert.assertTrue(isCreated);
		jdbcTemplate.update("DELETE FROM Journal WHERE date BETWEEN ? AND ?", ETALON_EMPTY_TIMEPERIOD.getFromDate(),
				ETALON_EMPTY_TIMEPERIOD.getToDate());
	}

	@Test
	public void testUpdate() {
		
		journalEntryDAO.create(ETALON_CREATE_ENTRY);
		
		JournalEntry journalEntry = jdbcTemplate.queryForObject(
				"SELECT * FROM Journal WHERE date=? AND fabric_id=? AND is_dr=? AND amount=?",
				(rs, rowNum) -> new JournalEntry(rs.getInt("id"), rs.getDate("date"), rs.getInt("fabric_id"), rs.getInt("is_dr"),
						rs.getBigDecimal("amount")),
				ETALON_CREATE_ENTRY.getDate(), ETALON_CREATE_ENTRY.getFabricId(), ETALON_CREATE_ENTRY.getIsDr(),
				ETALON_CREATE_ENTRY.getAmount());
		
		journalEntryDAO.update(new JournalEntry(journalEntry.getId(), ETALON_UPDATE_ENTRY.getDate(),
				ETALON_UPDATE_ENTRY.getFabricId(), ETALON_UPDATE_ENTRY.getIsDr(), ETALON_UPDATE_ENTRY.getAmount()));
		JournalEntry updatedJournalEntry = jdbcTemplate.queryForObject(
				"SELECT * FROM Journal WHERE id=?", (rs, rowNum) -> new JournalEntry(rs.getInt("id"),
						rs.getDate("date"), rs.getInt("fabric_id"), rs.getInt("is_dr"), rs.getBigDecimal("amount")),
				journalEntry.getId());
		Assert.assertEquals(ETALON_UPDATE_ENTRY.getDate(), updatedJournalEntry.getDate());
		Assert.assertEquals(ETALON_UPDATE_ENTRY.getFabricId(), updatedJournalEntry.getFabricId());
		Assert.assertEquals(ETALON_UPDATE_ENTRY.getIsDr(), updatedJournalEntry.getIsDr());
		Assert.assertEquals(ETALON_UPDATE_ENTRY.getAmount(), updatedJournalEntry.getAmount());
		jdbcTemplate.update("DELETE FROM Journal WHERE date BETWEEN ? AND ?", ETALON_EMPTY_TIMEPERIOD.getFromDate(),
				ETALON_EMPTY_TIMEPERIOD.getToDate());
	}

	@Test
	public void testRemove() {
		jdbcTemplate.update("INSERT INTO Journal VALUES(?, ?, ?, ?, ?)", ETALON_REMOVE_ENTRY.getId(),
				ETALON_REMOVE_ENTRY.getDate(), ETALON_REMOVE_ENTRY.getFabricId(), ETALON_REMOVE_ENTRY.getIsDr(),
				ETALON_REMOVE_ENTRY.getAmount());
		journalEntryDAO.remove(ETALON_REMOVE_ENTRY.getId());
		boolean isRemoved = jdbcTemplate
				.query("SELECT * FROM Journal WHERE date=? AND fabric_id=? AND is_dr=? AND amount=?",
						(rs, rowNum) -> new JournalEntry(rs.getDate("date"), rs.getInt("fabric_id"), rs.getInt("is_dr"),
								rs.getBigDecimal("amount")),
						ETALON_CREATE_ENTRY.getDate(), ETALON_CREATE_ENTRY.getFabricId(), ETALON_CREATE_ENTRY.getIsDr(),
						ETALON_CREATE_ENTRY.getAmount()).isEmpty();

		Assert.assertTrue(isRemoved);
		jdbcTemplate.update("DELETE FROM Journal WHERE date BETWEEN ? AND ?", ETALON_EMPTY_TIMEPERIOD.getFromDate(),
				ETALON_EMPTY_TIMEPERIOD.getToDate());
	}
	
	@After
	public void cleanChanges(){
		jdbcTemplate.update("DELETE FROM Journal WHERE date BETWEEN ? AND ?", ETALON_EMPTY_TIMEPERIOD.getFromDate(),
				ETALON_EMPTY_TIMEPERIOD.getToDate());
	}

}
