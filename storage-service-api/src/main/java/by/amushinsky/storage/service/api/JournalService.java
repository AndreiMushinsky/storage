package by.amushinsky.storage.service.api;

import java.util.List;

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;

public interface JournalService {
	
	public void addJournalEntry(JournalEntry journalEntry);
	public void changeJournalEntry(JournalEntry journalEntry);
	public List<JournalEntry> getJournalEntries(TimePeriod timePeriod);
	public JournalEntry getJournalEntryById(int id);
	public List<FabricMovement> getFabricMovements(TimePeriod timePeriod);
	public FabricMovement getTotalFabricMovement(TimePeriod timePeriod);
	public void removeJournalEntryById(int id);
	
}
