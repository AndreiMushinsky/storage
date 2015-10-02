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

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.core.TotalMovement;
import by.amushinsky.storage.dao.api.TrialBalanceDAO;

@Repository
public class TrialBalanceDAOImpl implements TrialBalanceDAO {
	private String totalMovementQuery;
	private String fabricMovementsQuery;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	Environment env;

	@PostConstruct
	public void init() throws IOException {
		totalMovementQuery = IOUtils
				.toString(new ClassPathResource(env.getProperty("totalMovementQuery")).getInputStream());
		fabricMovementsQuery = IOUtils
				.toString(new ClassPathResource(env.getProperty("fabricMovementsQuery")).getInputStream());
	}

	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FabricMovement> getMovements(TimePeriod timePeriod) {
		return jdbcTemplate.query(fabricMovementsQuery,
				(rs, rowNum) -> new FabricMovement(rs.getString("name"), rs.getBigDecimal("start_balance"),
						rs.getBigDecimal("dr_movement"), rs.getBigDecimal("cr_movement"),
						rs.getBigDecimal("end_balance")),
				timePeriod.getFromDate(), timePeriod.getFromDate(), timePeriod.getFromDate(), 
				timePeriod.getToDate(), timePeriod.getFromDate(), timePeriod.getToDate());
	}

	@Override
	@Transactional(readOnly = true)
	public TotalMovement getTotalMovement(TimePeriod timePeriod) {
		return jdbcTemplate.queryForObject(totalMovementQuery,
				(rs, rowNum) -> new TotalMovement(rs.getBigDecimal("start_balance"), rs.getBigDecimal("dr_movement"),
						rs.getBigDecimal("cr_movement"), rs.getBigDecimal("end_balance")),
				timePeriod.getFromDate(), timePeriod.getFromDate(), timePeriod.getFromDate(), 
				timePeriod.getToDate(), timePeriod.getFromDate(), timePeriod.getToDate(), timePeriod.getToDate(), 
				timePeriod.getToDate());

	}

}
