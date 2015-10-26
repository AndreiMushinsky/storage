package by.amushinsky.storage.core;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class JournalEntry {

    public static final String MIN_AMOUNT = "1.00";

    public static final int AMOUNT_FRACTION_PART_LENGTH = 2;

    public static final int AMOUNT_INTEGER_PART_LENGTH = 12;

    private int id;

    @NotNull
    private Date date;

    private int fabricId;

    private boolean isDr;

    @NotNull
    @DecimalMin(MIN_AMOUNT)
    @Digits(integer = AMOUNT_INTEGER_PART_LENGTH, fraction = AMOUNT_FRACTION_PART_LENGTH)
    private BigDecimal amount;

    public JournalEntry() {}

    public JournalEntry(Date date, int fabricId, boolean isDr,
            BigDecimal amount) {
        this.date = date;
        this.fabricId = fabricId;
        this.isDr = isDr;
        this.amount = amount;
    }

    public JournalEntry(int id, Date date, int fabricId, boolean isDr,
            BigDecimal amount) {
        this(date, fabricId, isDr, amount);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getFabricId() {
        return fabricId;
    }

    public boolean getIsDr() {
        return isDr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFabricId(int fabricId) {
        this.fabricId = fabricId;
    }

    public void setIsDr(boolean isDr) {
        this.isDr = isDr;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JournalEntry other = (JournalEntry) obj;
        if (id != other.id)
            return false;
        if (isDr != other.isDr)
            return false;
        if (amount == null) {
            if (other.amount != null)
                return false;
        } else if (amount.compareTo(other.amount) != 0)
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (fabricId != other.fabricId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "JournalEntry [id=" + id + ", date=" + date + ", fabricId="
                + fabricId + ", isDr=" + isDr + ", amount="
                + amount + "]";
    }

}
