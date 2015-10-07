package by.amushinsky.storage.core;

import java.math.BigDecimal;
import java.util.Date;

public class JournalEntry {
	private int id;
	private Date date;
	private int fabricId;
	private int isDr;
	private BigDecimal amount;

	public JournalEntry() {
	}

	public JournalEntry(Date date, int fabricId, int isDr, BigDecimal amount) {
		super();
		this.date = date;
		this.fabricId = fabricId;
		this.isDr = isDr;
		this.amount = amount;
	}

	public JournalEntry(int id, Date date, int fabricId, int isDr, BigDecimal amount) {
		super();
		this.id = id;
		this.date = date;
		this.fabricId = fabricId;
		this.isDr = isDr;
		this.amount = amount;
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

	public int getIsDr() {
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

	public void setIsDr(int isDr) {
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
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (fabricId != other.fabricId)
			return false;
		if (id != other.id)
			return false;
		if (isDr != other.isDr)
			return false;
		return true;
	}
	
	

}
