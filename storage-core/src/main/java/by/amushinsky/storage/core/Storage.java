package by.amushinsky.storage.core;

import java.math.BigDecimal;
import java.util.List;

public class Storage {
	private List<FabricStock> stocks;
	private BigDecimal totalAmount;

	public Storage(List<FabricStock> stocks, BigDecimal totalAmount) {
		super();
		this.stocks = stocks;
		this.totalAmount = totalAmount;
	}

	public List<FabricStock> getStocks() {
		return stocks;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setStocks(List<FabricStock> stocks) {
		this.stocks = stocks;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Storage other = (Storage) obj;
		if (stocks == null) {
			if (other.stocks != null)
				return false;
		} else if (!stocks.equals(other.stocks))
			return false;
		if (totalAmount == null) {
			if (other.totalAmount != null)
				return false;
		} else if (!totalAmount.equals(other.totalAmount))
			return false;
		return true;
	}

}
