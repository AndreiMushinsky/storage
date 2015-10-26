package by.amushinsky.storage.service.impl;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.dao.api.JournalDAO;
import by.amushinsky.storage.service.api.JournalService;
import by.amushinsky.storage.service.exception.JournalEntryValidationException;
import by.amushinsky.storage.service.exception.NoEntryWithSuchIdException;
import by.amushinsky.storage.service.exception.TimePeriodValidationException;
import by.amushinsky.storage.utils.Loggable;

@Service
public class JournalServiceImpl implements JournalService {

	@Autowired
	private Validator validator;

	@Autowired
	private JournalDAO journalDAO;

	@Override
	@Loggable
	@Transactional(readOnly = false)
	public void addJournalEntry(JournalEntry journalEntry) throws JournalEntryValidationException {
		validateEntry(journalEntry);
		journalDAO.insertJournalEntry(journalEntry);
	}

	@Override
	@Loggable
	@Transactional(readOnly = false)
	public void changeJournalEntry(JournalEntry journalEntry) throws JournalEntryValidationException {
		validateEntry(journalEntry);
		journalDAO.updateJournalEntry(journalEntry);
	}

	@Override
	@Loggable
	@Transactional(readOnly = true)
	public List<JournalEntry> getJournalEntries(TimePeriod timePeriod) {
		validatePeriod(timePeriod);
		return journalDAO.selectJournalEntries(timePeriod);
	}

	@Override
	@Loggable
	@Transactional(readOnly = true)
	public JournalEntry getJournalEntryById(int id) throws NoEntryWithSuchIdException {
		checkId(id);
		return journalDAO.selectJournalEntryById(id);
	}


	@Override
	@Loggable
	@Transactional(readOnly = true)
	public List<FabricMovement> getFabricMovements(TimePeriod timePeriod) {
		validatePeriod(timePeriod);
		return journalDAO.selectFabricMovements(timePeriod);
	}

	@Override
	@Loggable
	@Transactional(readOnly = true)
	public FabricMovement getTotalFabricMovement(TimePeriod timePeriod) {
		validatePeriod(timePeriod);
		return journalDAO.selectTotalFabricMovement(timePeriod);
	}

	@Override
	@Loggable
	@Transactional(readOnly = false)
	public void removeJournalEntryById(int id) {
		journalDAO.deleteJournalEntryById(id);
	}

	private void validateEntry(JournalEntry journalEntry) {
		Set<ConstraintViolation<JournalEntry>> violations = validator.validate(journalEntry);
		if (!violations.isEmpty())
			throw new JournalEntryValidationException(violations);
	}

	private void validatePeriod(TimePeriod timePeriod) {
		Set<ConstraintViolation<TimePeriod>> violations = validator.validate(timePeriod);
		if (!violations.isEmpty())
			throw new TimePeriodValidationException(violations);
	}
	
	private void checkId(int id) {
		if(!journalDAO.isIdReserved(id))
			throw new NoEntryWithSuchIdException(id);
	}

}
