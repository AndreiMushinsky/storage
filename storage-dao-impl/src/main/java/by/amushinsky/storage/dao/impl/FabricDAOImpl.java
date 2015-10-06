package by.amushinsky.storage.dao.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.dao.api.FabricDAO;

@Repository
public class FabricDAOImpl implements FabricDAO {
	private String createFabricQuery;
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	Environment env;

	@PostConstruct
	public void init() throws IOException {
		createFabricQuery = IOUtils
				.toString(new ClassPathResource(env.getProperty("createFabricQuery")).getInputStream());
	}
	
	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	@Transactional(readOnly = false)
	public void create(Fabric fabric) {
		jdbcTemplate.update(createFabricQuery, fabric.getName());
	}

}