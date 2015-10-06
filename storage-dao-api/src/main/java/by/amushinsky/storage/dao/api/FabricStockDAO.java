package by.amushinsky.storage.dao.api;

import java.math.BigDecimal;
import java.util.List;

import by.amushinsky.storage.core.FabricStock;

public interface FabricStockDAO {
	List<FabricStock> getStocks();

	BigDecimal getTotalAmount();
	
	BigDecimal getAmountById(int id);
}
