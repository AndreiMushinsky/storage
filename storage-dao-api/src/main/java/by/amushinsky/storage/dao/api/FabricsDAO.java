package by.amushinsky.storage.dao.api;

import java.math.BigDecimal;
import java.util.List;
import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.core.FabricStock;

public interface FabricsDAO {
	
	void insertFabric(Fabric fabric);
	
	boolean isNameReserved(String name);
	
	List<Fabric> selectAllFabrics();
	
	List<FabricStock> selectAllFabricStocks();
	
	BigDecimal selectFabricAmountById(int id);
	
	BigDecimal selectTotalFabricAmount();
}
