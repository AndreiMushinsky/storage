package by.amushinsky.storage.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.core.Storage;
import by.amushinsky.storage.dao.api.FabricDAO;
import by.amushinsky.storage.dao.api.FabricStockDAO;
import by.amushinsky.storage.service.api.StorageService;

@Service
public class StorageServiceImpl implements StorageService {

	private FabricStockDAO fabricStockDAO;
	
	private FabricDAO fabricDAO;

	@Autowired
	public void setFabricStockDAO(FabricStockDAO fabricStockDAO) {
		this.fabricStockDAO = fabricStockDAO;
	}
	
	@Autowired
	public void setFabricDAO(FabricDAO fabricDAO) {
		this.fabricDAO = fabricDAO;
	}

	@Override
	@Transactional(readOnly=true)
	public Storage getStorage() {
		return new Storage(fabricStockDAO.getStocks(), fabricStockDAO.getTotalAmount());
	}

	@Override
	@Transactional(readOnly=true)
	public List<Fabric> getFabrics() {
		return fabricDAO.getFabrics();
	}

	@Override
	@Transactional(readOnly=true)
	public BigDecimal getAmountById(int id) {
		return fabricStockDAO.getAmountById(id);
	}

}
