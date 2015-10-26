package by.amushinsky.storage.service.exception;

import java.text.MessageFormat;

import by.amushinsky.storage.core.JournalEntry;

public class NoEntryWithSuchIdException extends RuntimeException {

	private static final String ERROR_MESSAGE_TEMPLATE = "no entry with id ''{0}'' exists";
	private static final long serialVersionUID = 1L;

	public NoEntryWithSuchIdException(int id) {
		super(MessageFormat.format(ERROR_MESSAGE_TEMPLATE, id));
	}

	public NoEntryWithSuchIdException(JournalEntry journalEntry) {
		super(MessageFormat.format(ERROR_MESSAGE_TEMPLATE, journalEntry.getId()));
	}
	
}
