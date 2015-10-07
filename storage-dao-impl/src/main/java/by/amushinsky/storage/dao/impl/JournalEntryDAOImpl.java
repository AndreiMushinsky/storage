package by.amushinsky.storage.dao.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.dao.api.JournalEntryDAO;

@Repository
public class JournalEntryDAOImpl implements JournalEntryDAO {
	private String createEntryQuery;
	private String updateEntryQuery;
	private String removeEntryQuery;
	private String getEntriesQuery;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	Environment env;

	@PostConstruct
	public void init() throws IOException {
		createEntryQuery = IOUtils
				.toString(new ClassPathResource(env.getProperty("createEntryQuery")).getInputStream());
		updateEntryQuery = IOUtils
				.toString(new ClassPathResource(env.getProperty("updateEntryQuery")).getInputStream());
		removeEntryQuery = IOUtils
				.toString(new ClassPathResource(env.getProperty("removeEntryQuery")).getInputStream());
		getEntriesQuery = IOUtils.toString(new ClassPathResource(env.getProperty("getEntriesQuery")).getInputStream());
	}

	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	@Transactional(readOnly = true)
	public List<JournalEntry> getEntriesForPeriod(TimePeriod timePeriod) {
		return jdbcTemplate.query(getEntriesQuery,
				(rs, rowNum) -> new JournalEntry(rs.getInt("id"), rs.getDate("date"), rs.getInt("fabric_id"),
						rs.getInt("is_dr"), rs.getBigDecimal("amount")),
				timePeriod.getFromDate(), timePeriod.getToDate());
	}

	@Override
	@Transactional(readOnly = false)
	public void create(JournalEntry journalEntry) {
		jdbcTemplate.update(createEntryQuery, journalEntry.getDate(), journalEntry.getFabricId(),
				journalEntry.getIsDr(), journalEntry.getAmount());
	}

	@Override
	@Transactional(readOnly = false)
	public void update(JournalEntry journalEntry) {
		jdbcTemplate.update(updateEntryQuery, journalEntry.getDate(), journalEntry.getFabricId(),
				journalEntry.getIsDr(), journalEntry.getAmount(), journalEntry.getId());
	}

	@Override
	@Transactional(readOnly = false)
	public void remove(int id) {
		jdbcTemplate.update(removeEntryQuery, id);
	}

}
