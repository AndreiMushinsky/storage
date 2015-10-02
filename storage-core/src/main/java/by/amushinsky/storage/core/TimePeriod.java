package by.amushinsky.storage.core;

import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class TimePeriod {
	
	@DateTimeFormat(pattern="dd.MM.yyyy")
	private Date fromDate;
	
	@DateTimeFormat(pattern="dd.MM.yyyy")
	private Date toDate;

	public TimePeriod() {
	}

	public TimePeriod(Date fromDate, Date toDate) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public TimePeriod(Calendar fromDate, Calendar toDate) {
		super();
		this.fromDate = fromDate.getTime();
		this.toDate = toDate.getTime();
	}

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimePeriod other = (TimePeriod) obj;
		if (fromDate == null) {
			if (other.fromDate != null)
				return false;
		} else if (!fromDate.equals(other.fromDate))
			return false;
		if (toDate == null) {
			if (other.toDate != null)
				return false;
		} else if (!toDate.equals(other.toDate))
			return false;
		return true;
	}

}
