package by.amushinsky.storage.dao.api;

import java.util.List;

import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;

public interface JournalEntryDAO {
	List<JournalEntry> getEntriesForPeriod(TimePeriod timePeriod);

	void create(JournalEntry journalEntry);

	void update(JournalEntry journalEntry);

	void remove(int id);
}
