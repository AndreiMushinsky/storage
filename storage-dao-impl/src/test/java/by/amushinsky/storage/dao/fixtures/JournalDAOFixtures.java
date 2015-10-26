package by.amushinsky.storage.dao.fixtures;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.dao.sql.TableConstants.Journal;

@Component
public class JournalDAOFixtures {
	
	@Autowired
	private Environment env;

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public JournalEntry entry() throws ParseException {

		Date date = formatter.parse(env.getProperty("entry.date"));
		int fabricId = env.getProperty("entry.fabricId", Integer.class);
		boolean isDr = env.getProperty("entry.isDr", Boolean.class);
		BigDecimal amount = env.getProperty("entry.amount", BigDecimal.class);
		return new JournalEntry(date, fabricId, isDr, amount);
	}

	public JournalEntry entryWithId(int id) throws ParseException {
		JournalEntry entry = entry();
		entry.setId(id);
		return entry;
	}
	
	public List<JournalEntry> entries(TimePeriod timePeriod){
		return IntStream.rangeClosed(1, 4)
				.mapToObj(i -> {
					Date date = null;
					try {
						date = formatter.parse(env.getProperty("entry.date._"+i));
					} catch (Exception e) {
						e.printStackTrace();
					}
					int fabricId = env.getProperty("entry.fabricId._"+i, Integer.class);
					boolean isDr = env.getProperty("entry.isDr._"+i, Boolean.class);
					BigDecimal amount = env.getProperty("entry.amount._"+i, BigDecimal.class);
					return new JournalEntry(date, fabricId, isDr, amount);
				})
				.filter(entry -> {
					boolean lowBound = entry.getDate().compareTo(timePeriod.getFromDate()) >= 0;
					boolean hiBound = entry.getDate().compareTo(timePeriod.getToDate()) <= 0;
					return lowBound && hiBound;
				})
				.sorted( (fst, snd) -> fst.getDate().compareTo(snd.getDate()) )
				.collect(Collectors.toList());
	}
	
	public TimePeriod wholePeriod() throws ParseException{
		Date fromDate = formatter.parse(env.getProperty("entry.date._"+1));
		Date toDate = formatter.parse(env.getProperty("entry.date._"+4));
		return new TimePeriod(fromDate, toDate);
	}
	
	public TimePeriod middlePeriod() throws ParseException{
		Date fromDate = formatter.parse(env.getProperty("entry.date._"+2));
		Date toDate = formatter.parse(env.getProperty("entry.date._"+3));
		return new TimePeriod(fromDate, toDate);
	}
	
	public TimePeriod emptyPeriod() {
		Date fromDate = new Date();
		Date toDate = new Date();
		return new TimePeriod(fromDate, toDate);
	}
	
	public FabricMovement totalMovement(TimePeriod timePeriod) throws ParseException{
		Date fromDate = timePeriod.getFromDate();
		Date toDate = timePeriod.getToDate();
		
		if(fromDate == null || toDate == null){
			BigDecimal zero = BigDecimal.ZERO;
			return new FabricMovement(Journal.TOTAL_NAME, zero, zero, zero, zero);
		}
			
		List<JournalEntry> entries = entries(wholePeriod());
		String name = Journal.TOTAL_NAME;
		BigDecimal start = entries.stream()
				.filter(entry -> entry.getDate().compareTo(fromDate) < 0)
				.map(entry -> entry.getIsDr()? entry.getAmount(): entry.getAmount().negate())
				.reduce(BigDecimal.ZERO, (fst, snd) -> fst.add(snd) );
		BigDecimal debit = entries.stream()
				.filter(entry -> entry.getDate().compareTo(fromDate) >= 0)
				.filter(entry -> entry.getDate().compareTo(toDate) <= 0)
				.filter(entry -> entry.getIsDr())
				.map(entry -> entry.getAmount())
				.reduce(BigDecimal.ZERO, (fst, snd) -> fst.add(snd) );
		BigDecimal credit = entries.stream()
				.filter(entry -> entry.getDate().compareTo(fromDate) >= 0)
				.filter(entry -> entry.getDate().compareTo(toDate) <= 0)
				.filter(entry -> !entry.getIsDr())
				.map(entry -> entry.getAmount())
				.reduce(BigDecimal.ZERO, (fst, snd) -> fst.add(snd) );
		BigDecimal end = start.add(debit).subtract(credit);
		return new FabricMovement(name, start, debit, credit, end);
	}
	
	public List<FabricMovement> movements(){
		return IntStream.rangeClosed(1, 4)
				.mapToObj(i -> {
					String name = env.getProperty("fabrics.name._"+i);
					BigDecimal start = env.getProperty("fabrics.start._"+i, BigDecimal.class);
					BigDecimal debit = env.getProperty("fabrics.debit._"+i, BigDecimal.class);
					BigDecimal credit = env.getProperty("fabrics.credit._"+i, BigDecimal.class);
					BigDecimal end = start.add(debit).subtract(credit);
					return new FabricMovement(name, start, debit, credit, end);
				})
				.sorted((fst, snd) -> fst.getName().compareTo(snd.getName()))
				.collect(Collectors.toList());
	}
	
	public List<FabricMovement> emptyMovements(){
		return IntStream.rangeClosed(1, 4)
				.mapToObj(i -> {
					String name = env.getProperty("fabrics.name._"+i);
					BigDecimal start = env.getProperty("fabrics.end._"+i, BigDecimal.class);
					BigDecimal zero = BigDecimal.ZERO;
					BigDecimal end = env.getProperty("fabrics.end._"+i, BigDecimal.class);
					return new FabricMovement(name, start, zero, zero, end);
				})
				.sorted((fst, snd) -> fst.getName().compareTo(snd.getName()))
				.collect(Collectors.toList());
	}
	
	public TimePeriod movementPeriod() throws ParseException{
		Date fromDate = formatter.parse(env.getProperty("movement.start"));
		Date toDate = formatter.parse(env.getProperty("movement.end"));
		return new TimePeriod(fromDate, toDate);
	}
	
	public int notReservedId(){
		return -1;
	}
}
