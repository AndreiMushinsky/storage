package by.amushinsky.storage.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.core.FabricStock;
import by.amushinsky.storage.dao.api.FabricsDAO;
import by.amushinsky.storage.service.api.FabricService;
import by.amushinsky.storage.service.exception.FabricNameExistsException;
import by.amushinsky.storage.service.exception.FabricValidationException;
import by.amushinsky.storage.utils.Loggable;

@Service
public class FabricsServiceImpl implements FabricService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private FabricsDAO fabricsDAO;
	
	@Override
	@Loggable
	@Transactional(readOnly = false)
	public void addFabric(Fabric fabric) throws FabricValidationException, IllegalArgumentException {
		validate(fabric);
		checkUniqueName(fabric);
		fabricsDAO.insertFabric(fabric);
	}

	@Override
	@Loggable
	@Transactional(readOnly = true)
	public List<Fabric> getAllFabrics() {
		return fabricsDAO.selectAllFabrics();
	}

	@Override
	@Loggable
	@Transactional(readOnly = true)
	public List<FabricStock> getAllFabricStocks() {
		return fabricsDAO.selectAllFabricStocks();
	}

	@Override
	@Loggable
	@Transactional(readOnly = true)
	public BigDecimal getFabricAmountById(int id) {
		return fabricsDAO.selectFabricAmountById(id);
	}

	@Override
	@Loggable
	@Transactional(readOnly = true)
	public BigDecimal getTotalFabricAmount() {
		return fabricsDAO.selectTotalFabricAmount();
	}
	
	private void validate(Fabric fabric){
		Set<ConstraintViolation<Fabric>> violations = validator.validate(fabric);
		if(!violations.isEmpty()) 
			throw new FabricValidationException(violations);
	}
	
	private void checkUniqueName(Fabric fabric){
		String name = fabric.getName();
		if(fabricsDAO.isNameReserved(name))
			throw new FabricNameExistsException(name);
	}
	
}
