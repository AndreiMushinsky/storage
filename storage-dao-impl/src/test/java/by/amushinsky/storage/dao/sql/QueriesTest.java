package by.amushinsky.storage.dao.sql;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.amushinsky.storage.dao.config.DataConfig;

@ActiveProfiles("dev")
@ContextConfiguration(classes = DataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class QueriesTest {
	
	@Autowired
	Queries queries;
	
	public static final String EXPECTED_SQL_QUERY = "UPDATE Journal SET date=:date, fabric_id=:fabricId, is_dr=:isDr, amount=:amount WHERE id=:id";
	
	@Test
	public void testGetUpdateJournalEntry() {
		assertEquals(EXPECTED_SQL_QUERY, queries.getUpdateJournalEntry());
	}

}
