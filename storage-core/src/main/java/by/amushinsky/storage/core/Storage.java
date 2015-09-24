package by.amushinsky.storage.core;

import java.math.BigDecimal;
import java.util.List;

public class Storage 
{
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
	
}
