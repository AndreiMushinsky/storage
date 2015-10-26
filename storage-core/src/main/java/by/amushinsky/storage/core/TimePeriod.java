package by.amushinsky.storage.core;

import java.util.Date;

import javax.validation.constraints.NotNull;

import by.amushinsky.storage.validation.ValidTimePeriod;

@ValidTimePeriod
public class TimePeriod {

    @NotNull
    private Date fromDate;

    @NotNull
    private Date toDate;

    public TimePeriod() {}

    public TimePeriod(Date fromDate, Date toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public TimePeriod(long fromDate, long toDate) {
        this.fromDate = new Date(fromDate);
        this.toDate = new Date(toDate);
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

    @Override
    public String toString() {
        return "TimePeriod [fromDate=" + fromDate + ", toDate=" + toDate + "]";
    }

}
