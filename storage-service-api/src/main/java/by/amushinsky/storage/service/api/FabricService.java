package by.amushinsky.storage.service.api;

import java.math.BigDecimal;
import java.util.List;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.core.FabricStock;

public interface FabricService {
	
	public void addFabric(Fabric fabric);
	public List<Fabric> getAllFabrics();
	public List<FabricStock> getAllFabricStocks();
	public BigDecimal getFabricAmountById(int id);
	public BigDecimal getTotalFabricAmount();
	
}
