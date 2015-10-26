package by.amushinsky.storage.dao.api;

import java.util.List;

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;

public interface JournalDAO {
	
	void deleteJournalEntryById(int id);
	
	void insertJournalEntry(JournalEntry journalEntry);
	
	boolean isIdReserved(int id);
	
	List<JournalEntry> selectJournalEntries(TimePeriod timePeriod);
	
	JournalEntry selectJournalEntryById(int id);
	
	List<FabricMovement> selectFabricMovements(TimePeriod timePeriod);
	
	FabricMovement selectTotalFabricMovement(TimePeriod timePeriod);
	
	void updateJournalEntry(JournalEntry journalEntry);

}
