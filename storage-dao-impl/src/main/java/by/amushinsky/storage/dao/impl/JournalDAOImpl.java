package by.amushinsky.storage.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.dao.api.JournalDAO;
import by.amushinsky.storage.dao.sql.Queries;
import by.amushinsky.storage.dao.sql.TableConstants.Fabrics;
import by.amushinsky.storage.dao.sql.TableConstants.Journal;

@Repository
public class JournalDAOImpl implements JournalDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert insertJournalEntry;

	@Autowired
	private Queries queries;

	private RowMapper<JournalEntry> journalEntryMapper = (resultSet, rowNum) -> {
		
		int id = resultSet.getInt(Journal.ID);
		Date date = resultSet.getDate(Journal.DATE);
		int fabricId = resultSet.getInt(Journal.FABRIC_ID);
		boolean isDr = resultSet.getBoolean(Journal.IS_DR);
		BigDecimal amount = resultSet.getBigDecimal(Journal.AMOUNT);
		
		return new JournalEntry(id, date, fabricId, isDr, amount);
	};


	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.insertJournalEntry = new SimpleJdbcInsert(dataSource)
				.withTableName(Journal.TABLE_NAME)
				.usingGeneratedKeyColumns(Journal.ID);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteJournalEntryById(int id) {
		jdbcTemplate.getJdbcOperations().update(queries.getDeleteJournalEntryById(), id);
	}

	@Override
	@Transactional(readOnly = false)
	public void insertJournalEntry(JournalEntry journalEntry) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(journalEntry);
		int id = insertJournalEntry.executeAndReturnKey(params).intValue();
		journalEntry.setId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isIdReserved(int id) {
		SqlParameterSource params = new MapSqlParameterSource(Journal.ID, id);
		int count = jdbcTemplate.queryForObject(queries.getCountEntriesWithId(), params, Integer.class);
		return count != 0;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<JournalEntry> selectJournalEntries(TimePeriod timePeriod) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(timePeriod);
		return jdbcTemplate.query(queries.getSelectJournalEntries(), params, journalEntryMapper);
	}

	@Override
	@Transactional(readOnly = true)
	public JournalEntry selectJournalEntryById(int id) {
		return jdbcTemplate.getJdbcOperations().queryForObject(queries.getSelectJournalEntryById(), journalEntryMapper,
				id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FabricMovement> selectFabricMovements(TimePeriod timePeriod) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(timePeriod);
		return jdbcTemplate.query(queries.getSelectFabricMovements(), params,
				(resultSet, rowNum) -> {
					String name = resultSet.getString(Fabrics.NAME);
					BigDecimal start = resultSet.getBigDecimal(Journal.START_BALANCE);
					BigDecimal debit = resultSet.getBigDecimal(Journal.DEBIT_MOVEMENT);
					BigDecimal credit = resultSet.getBigDecimal(Journal.CREDIT_MOVEMENT);
					BigDecimal end = resultSet.getBigDecimal(Journal.END_BALANCE);
					return new FabricMovement(name, start, debit, credit, end);
				});
	}

	@Override
	@Transactional(readOnly = true)
	public FabricMovement selectTotalFabricMovement(TimePeriod timePeriod) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(timePeriod);
		return jdbcTemplate.queryForObject(queries.getSelectTotalFabricMovement(), params,
				(resultSet, rowNum) -> {
					String name = Journal.TOTAL_NAME;
					BigDecimal start = resultSet.getBigDecimal(Journal.START_BALANCE);
					BigDecimal debit = resultSet.getBigDecimal(Journal.DEBIT_MOVEMENT);
					BigDecimal credit = resultSet.getBigDecimal(Journal.CREDIT_MOVEMENT);
					BigDecimal end = resultSet.getBigDecimal(Journal.END_BALANCE);
					return new FabricMovement(name, start, debit, credit, end);
				});
	}

	@Override
	@Transactional(readOnly = false)
	public void updateJournalEntry(JournalEntry journalEntry) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(journalEntry);
		jdbcTemplate.update(queries.getUpdateJournalEntry(), params);
	}

	
}
