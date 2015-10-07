package by.amushinsky.storage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.dao.api.FabricDAO;
import by.amushinsky.storage.dao.api.JournalEntryDAO;
import by.amushinsky.storage.service.api.JournalService;

@Service
public class JournalServiceImpl implements JournalService {
	
	private JournalEntryDAO journalEntryDAO;
	
	private FabricDAO fabricDAO;
	
	@Autowired
	public void setJournalEntryDAO(JournalEntryDAO journalEntryDAO) {
		this.journalEntryDAO = journalEntryDAO;
	}
	
	@Autowired
	public void setFabricDAO(FabricDAO fabricDAO) {
		this.fabricDAO = fabricDAO;
	}

	@Override
	@Transactional(readOnly=true)
	public List<JournalEntry> getEntriesForPeriod(TimePeriod timePeriod) {
		return journalEntryDAO.getEntriesForPeriod(timePeriod);
	}

	@Override
	@Transactional(readOnly=false)
	public void createEntry(JournalEntry journalEntry) {
		journalEntryDAO.create(journalEntry);
	}

	@Override
	@Transactional(readOnly=false)
	public void createEntryWithFabric(JournalEntry journalEntry, Fabric fabric) {
		fabricDAO.create(fabric);
		journalEntryDAO.create(journalEntry);
	}

	@Override
	@Transactional(readOnly=false)
	public void updateEntry(JournalEntry journalEntry) {
		journalEntryDAO.update(journalEntry);
	}

	@Override
	@Transactional(readOnly=false)
	public void removeEntryById(int id) {
		journalEntryDAO.remove(id);
	}

}
