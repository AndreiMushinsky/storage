package by.amushinsky.storage.service.impl;

import static by.amushinsky.storage.service.fixtures.FabricServiceFixtures.FABRIC_NAME;
import static by.amushinsky.storage.service.fixtures.FabricServiceFixtures.RESERVED_NAME;
import static by.amushinsky.storage.service.fixtures.FabricServiceFixtures.RESERVED_NAME_EXCEPTION_MSG;
import static by.amushinsky.storage.service.fixtures.FabricServiceFixtures.fabric;
import static by.amushinsky.storage.service.fixtures.FabricServiceFixtures.invalidFabric;
import static by.amushinsky.storage.service.fixtures.FabricServiceFixtures.violations;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.dao.api.FabricsDAO;
import by.amushinsky.storage.service.api.FabricService;
import by.amushinsky.storage.service.config.TestConfig;
import by.amushinsky.storage.service.exception.FabricNameExistsException;
import by.amushinsky.storage.service.exception.FabricValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class FabricsServiceImplTest {
	
	@Autowired
	private FabricService fabricService;
	
	@Autowired
	private FabricsDAO fabricsDAO;
	
	@Autowired
	private Validator validator;
	
	@Before
	public void setUp(){
		Mockito.reset(fabricsDAO, validator);
		
		Mockito.when(validator.validate(invalidFabric())).thenReturn(violations());
		Mockito.when(fabricsDAO.isNameReserved(RESERVED_NAME)).thenReturn(true);
		Mockito.when(fabricsDAO.isNameReserved(FABRIC_NAME)).thenReturn(false);
	}
	
	@Test
	public void testAddFabric() {
		Fabric fabric = fabric();
		fabricService.addFabric(fabric);
		Mockito.verify(validator, Mockito.times(1)).validate(fabric);
		Mockito.verify(fabricsDAO, Mockito.times(1)).isNameReserved(FABRIC_NAME);
		Mockito.verify(fabricsDAO, Mockito.times(1)).insertFabric(fabric);
	}
	
	@Test(expected=FabricNameExistsException.class)
	public void testAddFabricWithReservedName() {
		Fabric fabric = fabric();
		fabric.setName(RESERVED_NAME);
		try{
			fabricService.addFabric(fabric);
		} catch(IllegalArgumentException exception){
			assertEquals(RESERVED_NAME_EXCEPTION_MSG, exception.getMessage());
			Mockito.verify(validator, Mockito.times(1)).validate(fabric);
			Mockito.verify(fabricsDAO, Mockito.times(1)).isNameReserved(RESERVED_NAME);
			Mockito.verify(fabricsDAO, Mockito.times(0)).insertFabric(fabric);
			throw exception;
		}
	}
	
	@Test(expected=FabricValidationException.class)
	public void testAddFabricWithValidationFailure() {
		Fabric fabric = invalidFabric();
		try{
			fabricService.addFabric(fabric);
		} catch(FabricValidationException exception){
			assertEquals(violations(), exception.getViolations());
			Mockito.verify(validator, Mockito.times(1)).validate(fabric);
			Mockito.verify(fabricsDAO, Mockito.times(0)).isNameReserved(FABRIC_NAME);
			Mockito.verify(fabricsDAO, Mockito.times(0)).insertFabric(fabric);
			throw exception;
		}
	}

	@Test
	public void testGetAllFabrics() {
		fabricService.getAllFabrics();
		Mockito.verify(fabricsDAO,Mockito.times(1)).selectAllFabrics();
	}

	@Test
	public void testGetAllFabricStocks() {
		fabricService.getAllFabricStocks();
		Mockito.verify(fabricsDAO,Mockito.times(1)).selectAllFabricStocks();
	}

	@Test
	public void testGetFabricAmountById() {
		Fabric fabric = fabric();
		fabricService.getFabricAmountById(fabric.getId());
		Mockito.verify(fabricsDAO, Mockito.times(1)).selectFabricAmountById(fabric.getId());
	}

	@Test
	public void testGetTotalFabricAmount() {
		Mockito.when(fabricsDAO.selectTotalFabricAmount()).thenReturn(BigDecimal.TEN);
		BigDecimal result = fabricService.getTotalFabricAmount();
		Mockito.verify(fabricsDAO,Mockito.times(1)).selectTotalFabricAmount();
		Assert.assertEquals(BigDecimal.TEN, result);
	}

}
