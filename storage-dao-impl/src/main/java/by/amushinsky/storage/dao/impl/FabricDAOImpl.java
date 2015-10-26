package by.amushinsky.storage.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.core.FabricStock;
import by.amushinsky.storage.dao.api.FabricsDAO;
import by.amushinsky.storage.dao.sql.Queries;
import by.amushinsky.storage.dao.sql.TableConstants.Fabrics;

@Repository
public class FabricDAOImpl implements FabricsDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert insertFabric;

	@Autowired
	private Queries queries;

	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.insertFabric = new SimpleJdbcInsert(dataSource).withTableName(Fabrics.TABLE_NAME)
				.usingGeneratedKeyColumns(Fabrics.ID);
	}

	@Override
	@Transactional(readOnly = false)
	public void insertFabric(Fabric fabric) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(fabric);
		int id = insertFabric.executeAndReturnKey(params).intValue();
		fabric.setId(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean isNameReserved(String name) {
		SqlParameterSource params = new MapSqlParameterSource(Fabrics.NAME, name);
		int count = jdbcTemplate.queryForObject(queries.getCountFabricsWithName(), params, Integer.class);
		return count != 0;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Fabric> selectAllFabrics() {
		return jdbcTemplate.query(queries.getSelectAllFabrics(),
				(resultSet, rowNum) -> new Fabric(resultSet.getInt(Fabrics.ID), resultSet.getString(Fabrics.NAME)));
	}

	@Override
	@Transactional(readOnly = true)
	public List<FabricStock> selectAllFabricStocks() {
		return jdbcTemplate.query(queries.getSelectAllFabricStocks(),
				(rs, rowNum) -> new FabricStock(rs.getString(Fabrics.NAME), rs.getBigDecimal(Fabrics.BALANCE)));
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal selectFabricAmountById(int id) {
		SqlParameterSource params = new MapSqlParameterSource(Fabrics.ID, id);
		return jdbcTemplate.queryForObject(queries.getSelectFabricAmountById(), params, BigDecimal.class);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal selectTotalFabricAmount() {
		return jdbcTemplate.getJdbcOperations().queryForObject(queries.getSelectTotalFabricAmount(), BigDecimal.class);
	}

}
