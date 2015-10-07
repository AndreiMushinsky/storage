package by.amushinsky.storage.service.api;

import java.util.List;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;

public interface JournalService {
	List<JournalEntry> getEntriesForPeriod(TimePeriod timePeriod);
	
	void createEntry(JournalEntry journalEntry);
	
	void createEntryWithFabric(JournalEntry journalEntry, Fabric fabric);

	void updateEntry(JournalEntry journalEntry);

	void removeEntryById(int id);
}
