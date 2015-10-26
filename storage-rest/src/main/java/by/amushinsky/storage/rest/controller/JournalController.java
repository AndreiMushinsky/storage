package by.amushinsky.storage.rest.controller;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.service.api.JournalService;
import by.amushinsky.storage.service.exception.JournalEntryValidationException;
import by.amushinsky.storage.service.exception.NoEntryWithSuchIdException;

@RestController
public class JournalController extends RestJSONController {

	@Autowired
	private JournalService journalService;
	
	@RequestMapping(method = POST, value = "/entries", consumes = "application/json")
	public JournalEntry addJournalEntry(@RequestParam JournalEntry journalEntry) {
		journalService.addJournalEntry(journalEntry);
		return journalEntry;
	}
	
	@RequestMapping(method = PUT, value = "/entries/{id}", consumes = "application/json")
	public void changeJournalEntry(@PathVariable int id, @RequestParam JournalEntry journalEntry) {
		journalEntry.setId(id);
		journalService.changeJournalEntry(journalEntry);
	}
	
	@RequestMapping(method = GET, value = "/entries")
	public List<JournalEntry> getJournalEntries(@RequestParam long fromDate, @RequestParam long toDate) {
		TimePeriod timePeriod = new TimePeriod(fromDate, toDate);
		return journalService.getJournalEntries(timePeriod);
	}
	
	@RequestMapping(method = GET, value = "/entries/{id}")
	public JournalEntry getJournalEntryById(@PathVariable int id) {
		return journalService.getJournalEntryById(id);
	}
	
	@RequestMapping(method = GET, value = "/movements")
	public List<FabricMovement> getFabricMovements(@RequestParam long fromDate, @RequestParam long toDate) {
		TimePeriod timePeriod = new TimePeriod(fromDate, toDate);
		return journalService.getFabricMovements(timePeriod);
	}
	
	@RequestMapping(method = GET, value = "/movements/amount")
	public FabricMovement getTotalFabricMovement(@RequestParam long fromDate, @RequestParam long toDate) {
		TimePeriod timePeriod = new TimePeriod(fromDate, toDate);
		return journalService.getTotalFabricMovement(timePeriod);
	}
	
	@RequestMapping(method = DELETE, value = "/entries/{id}")
	public void removeJournalEntryById(@PathVariable int id) {
		journalService.removeJournalEntryById(id);
	}

	@ResponseStatus(NOT_ACCEPTABLE)
	@ExceptionHandler(JournalEntryValidationException.class)
	public List<String> validationFailure(JournalEntryValidationException exception) {
		Set<ConstraintViolation<JournalEntry>> violations = exception.getViolations();
		return violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
	}
	
	@ResponseStatus(NOT_FOUND)
	@ExceptionHandler(NoEntryWithSuchIdException.class)
	public List<String> notFound(NoEntryWithSuchIdException exception) {
		return Collections.singletonList(exception.getMessage());
	}
	
}
