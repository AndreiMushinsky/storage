package by.amushinsky.storage.dao.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import by.amushinsky.storage.dao.api.FabricStockDAO;
import by.amushinsky.storage.core.FabricStock;

@Repository
public class FabricStockDAOImpl implements FabricStockDAO
{
	private String TOTAL_AMOUNT_QUERY;
	private String FABRIC_STOCKS_QUERY;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment env;
	
	@PostConstruct
	public void init() throws IOException
	{
		TOTAL_AMOUNT_QUERY = 
				FileUtils.readFileToString(
						new ClassPathResource(env.getProperty("TOTAL_AMOUNT_QUERY")).getFile());
		FABRIC_STOCKS_QUERY = 
				FileUtils.readFileToString(
						new ClassPathResource(env.getProperty("FABRIC_STOCKS_QUERY")).getFile());
	}
	
	@Autowired
	public void setJdbcTemplate(DataSource dataSource)
	{
		this.jdbcTemplate =  new JdbcTemplate(dataSource);
	} 
	
	@Override
	@Transactional(readOnly = true)
	public List<FabricStock> getStocks() 
	{
		return jdbcTemplate.query(
				FABRIC_STOCKS_QUERY,
				(rs, rowNum) -> new FabricStock(rs.getString("name"), rs.getBigDecimal("balance"))
				);
		
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getTotalAmount() 
	{
		return jdbcTemplate.queryForObject(TOTAL_AMOUNT_QUERY, BigDecimal.class);
	}
	
}
