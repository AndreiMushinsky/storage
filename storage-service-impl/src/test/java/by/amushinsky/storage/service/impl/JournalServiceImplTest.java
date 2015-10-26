package by.amushinsky.storage.service.impl;

import static by.amushinsky.storage.service.fixtures.JournalServiceFixtures.WRONG_ID;
import static by.amushinsky.storage.service.fixtures.JournalServiceFixtures.entry;
import static by.amushinsky.storage.service.fixtures.JournalServiceFixtures.entryViolations;
import static by.amushinsky.storage.service.fixtures.JournalServiceFixtures.invalidEntry;
import static by.amushinsky.storage.service.fixtures.JournalServiceFixtures.invalidPeriod;
import static by.amushinsky.storage.service.fixtures.JournalServiceFixtures.period;
import static by.amushinsky.storage.service.fixtures.JournalServiceFixtures.timePeriodViolations;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.dao.api.JournalDAO;
import by.amushinsky.storage.service.api.JournalService;
import by.amushinsky.storage.service.config.TestConfig;
import by.amushinsky.storage.service.exception.NoEntryWithSuchIdException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class JournalServiceImplTest {

	@Autowired
	private JournalService journalService;
	
	@Autowired
	private JournalDAO journalDAO;
	
	@Autowired
	private Validator validator;

	@Before
	public void setUp() {
		Mockito.reset(journalDAO, validator);
		
		Mockito.when(validator.validate(invalidEntry())).thenReturn(entryViolations());
		Mockito.when(validator.validate(invalidPeriod())).thenReturn(timePeriodViolations());
		Mockito.when(journalDAO.isIdReserved(entry().getId())).thenReturn(true);
		Mockito.when(journalDAO.isIdReserved(WRONG_ID)).thenReturn(false);
	}

	@Test
	public void testAddJournalEntry() {
		JournalEntry entry = entry();
		journalService.addJournalEntry(entry);
		Mockito.verify(validator, Mockito.times(1)).validate(entry);
		Mockito.verify(journalDAO, Mockito.times(1)).insertJournalEntry(entry);
	}

	@Test
	public void testChangeJournalEntry() {
		JournalEntry entry = entry();
		journalService.changeJournalEntry(entry);
		Mockito.verify(validator, Mockito.times(1)).validate(entry);
		Mockito.verify(journalDAO, Mockito.times(1)).updateJournalEntry(entry);
	}

	@Test
	public void testGetJournalEntries() {
		TimePeriod timePeriod = period();
		journalService.getJournalEntries(timePeriod);
		Mockito.verify(validator, Mockito.times(1)).validate(timePeriod);
		Mockito.verify(journalDAO, Mockito.times(1)).selectJournalEntries(timePeriod);
	}

	@Test
	public void testGetJournalEntryByIdPass() {
		JournalEntry entry = entry();
		journalService.getJournalEntryById(entry.getId());
		Mockito.verify(journalDAO, Mockito.times(1)).selectJournalEntryById(entry.getId());
	}

	@Test(expected = NoEntryWithSuchIdException.class)
	public void testGetJournalEntryByIdFail() {
		try {
			journalService.getJournalEntryById(WRONG_ID);
		} catch (EmptyResultDataAccessException exception) {
			Mockito.verify(journalDAO, Mockito.times(1)).selectJournalEntryById(WRONG_ID);
			throw exception;
		}
	}

	@Test
	public void testGetFabricMovements() {
		TimePeriod timePeriod = period();
		journalService.getFabricMovements(timePeriod);
		Mockito.verify(validator, Mockito.times(1)).validate(timePeriod);
		Mockito.verify(journalDAO, Mockito.times(1)).selectFabricMovements(timePeriod);
	}

	@Test
	public void testGetTotalFabricMovement() {
		TimePeriod timePeriod = period();
		journalService.getTotalFabricMovement(timePeriod);
		Mockito.verify(validator, Mockito.times(1)).validate(timePeriod);
		Mockito.verify(journalDAO, Mockito.times(1)).selectTotalFabricMovement(timePeriod);
	}

	@Test
	public void testRemoveJournalEntryById() {
		JournalEntry entry = entry();
		journalDAO.deleteJournalEntryById(entry.getId());
		Mockito.verify(journalDAO, Mockito.times(1)).deleteJournalEntryById(entry.getId());
	}

}
