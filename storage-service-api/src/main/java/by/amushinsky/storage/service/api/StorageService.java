package by.amushinsky.storage.service.api;

import java.math.BigDecimal;
import java.util.List;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.core.Storage;

public interface StorageService {
	Storage getStorage();
	
	List<Fabric> getFabrics();
	
	BigDecimal getAmountById(int id);
}
