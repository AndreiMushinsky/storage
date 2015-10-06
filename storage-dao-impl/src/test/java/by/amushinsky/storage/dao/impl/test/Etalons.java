package by.amushinsky.storage.dao.impl.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.FabricStock;
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
	
	static{
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
		
		ETALON_EMPTY_TOTAL_MOVEMENT = new TotalMovement(new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"),
				new BigDecimal("0.00"));
	}
}
