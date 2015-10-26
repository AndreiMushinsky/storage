package by.amushinsky.storage.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Service;

import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.service.api.TimePeriodService;

@Service
public class TimePeriodServiceImpl implements TimePeriodService {

	@Override
	public TimePeriod defaultTimePeriod() {
		Calendar now = new GregorianCalendar();
		Date fromDate = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1).getTime();
		Date toDate = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).getTime();
		return new TimePeriod(fromDate, toDate);
	}

}
