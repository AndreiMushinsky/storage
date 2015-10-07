package by.amushinsky.storage.dao.impl.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.FabricStock;
import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.core.TotalMovement;

public class Etalons {
	public final static List<FabricStock> ETALON_STOCKS;
	public final static BigDecimal ETALON_TOTAL_AMOUNT;
	public final static int ETALON_FABRIC_ID;
	public final static BigDecimal ETALON_FABRIC_AMOUNT;
	public final static Date ETALON_FROM_DATE;
	public final static Date ETALON_TO_DATE;
	public final static TimePeriod ETALON_TIMEPERIOD;
	public final static TimePeriod ETALON_EMPTY_TIMEPERIOD;
	public final static TotalMovement ETALON_TOTAL_MOVEMENT;
	public final static TotalMovement ETALON_EMPTY_TOTAL_MOVEMENT;
	public final static List<FabricMovement> ETALON_MOVEMENTS;
	public final static Fabric ETALON_FABRIC;
	public final static List<JournalEntry> ETALON_ENTRIES;
	public final static JournalEntry ETALON_CREATE_ENTRY;
	public final static JournalEntry ETALON_UPDATE_ENTRY;
	public final static JournalEntry ETALON_REMOVE_ENTRY;
	public final static List<Fabric> ETALON_FABRICS;

	static {
		ETALON_STOCKS = new ArrayList<>();
		ETALON_STOCKS.add(new FabricStock("linen 100%", new BigDecimal("500.00")));
		ETALON_STOCKS.add(new FabricStock("cotton 100%", new BigDecimal("460.10")));
		ETALON_STOCKS.add(new FabricStock("cot/lin 50/50", new BigDecimal("1200.50")));
		ETALON_STOCKS.add(new FabricStock("PE 100%", new BigDecimal("799.50")));

		ETALON_TOTAL_AMOUNT = new BigDecimal("2960.10");

		ETALON_FABRIC_ID = 1;

		ETALON_FABRIC_AMOUNT = new BigDecimal("500.00");

		ETALON_FROM_DATE = new GregorianCalendar(2015, Calendar.SEPTEMBER, 7).getTime();

		ETALON_TO_DATE = new GregorianCalendar(2015, Calendar.SEPTEMBER, 14).getTime();

		ETALON_TIMEPERIOD = new TimePeriod(ETALON_FROM_DATE, ETALON_TO_DATE);

		ETALON_TOTAL_MOVEMENT = new TotalMovement(new BigDecimal("1560.20"), new BigDecimal("4000.60"),
				new BigDecimal("1100.20"), new BigDecimal("4460.60"));

		ETALON_MOVEMENTS = new ArrayList<>();
		ETALON_MOVEMENTS.add(new FabricMovement("cotton 100%", new BigDecimal("560.20"), new BigDecimal("0.00"),
				new BigDecimal("100.10"), new BigDecimal("460.10")));
		ETALON_MOVEMENTS.add(new FabricMovement("cot/lin 50/50", new BigDecimal("0.00"), new BigDecimal("1700.60"),
				new BigDecimal("500.10"), new BigDecimal("1200.50")));
		ETALON_MOVEMENTS.add(new FabricMovement("linen 100%", new BigDecimal("1000.00"), new BigDecimal("0.00"),
				new BigDecimal("500.00"), new BigDecimal("500.00")));
		ETALON_MOVEMENTS.add(new FabricMovement("PE 100%", new BigDecimal("0.00"), new BigDecimal("2300.00"),
				new BigDecimal("0.00"), new BigDecimal("2300.00")));

		ETALON_EMPTY_TIMEPERIOD = new TimePeriod(new GregorianCalendar(1991, Calendar.SEPTEMBER, 7),
				new GregorianCalendar(1991, Calendar.SEPTEMBER, 14));

		ETALON_EMPTY_TOTAL_MOVEMENT = new TotalMovement(new BigDecimal("0.00"), new BigDecimal("0.00"),
				new BigDecimal("0.00"), new BigDecimal("0.00"));

		ETALON_FABRIC = new Fabric("test");

		ETALON_ENTRIES = new ArrayList<>();
		ETALON_ENTRIES.add(new JournalEntry(3, ETALON_FROM_DATE, 3, 1, new BigDecimal("1700.60")));
		ETALON_ENTRIES.add(new JournalEntry(4, new GregorianCalendar(2015, Calendar.SEPTEMBER, 10).getTime(), 4, 1,
				new BigDecimal("2300.00")));
		ETALON_ENTRIES.add(new JournalEntry(5, new GregorianCalendar(2015, Calendar.SEPTEMBER, 11).getTime(), 1, 0,
				new BigDecimal("500.00")));
		ETALON_ENTRIES.add(new JournalEntry(6, new GregorianCalendar(2015, Calendar.SEPTEMBER, 12).getTime(), 2, 0,
				new BigDecimal("100.10")));
		ETALON_ENTRIES.add(new JournalEntry(7, ETALON_TO_DATE, 3, 0, new BigDecimal("500.10")));
		ETALON_ENTRIES.sort( (x, y) -> x.getDate().compareTo(y.getDate()) );
		
		ETALON_CREATE_ENTRY = new JournalEntry(ETALON_EMPTY_TIMEPERIOD.getFromDate(), 1, 1, new BigDecimal("1.00"));
		
		ETALON_UPDATE_ENTRY = new JournalEntry(ETALON_EMPTY_TIMEPERIOD.getToDate(), 2, 0, new BigDecimal("2.00"));
		
		ETALON_REMOVE_ENTRY = new JournalEntry(100, ETALON_EMPTY_TIMEPERIOD.getFromDate(), 1, 1, new BigDecimal("1.00"));
		
		ETALON_FABRICS = new ArrayList<>();
		ETALON_FABRICS.add(new Fabric(1, "linen 100%"));
		ETALON_FABRICS.add(new Fabric(2, "cotton 100%"));
		ETALON_FABRICS.add(new Fabric(3, "cot/lin 50/50"));
		ETALON_FABRICS.add(new Fabric(4, "PE 100%"));
	}
}
