package by.amushinsky.storage.rest.controller;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.core.FabricStock;
import by.amushinsky.storage.service.api.FabricService;
import by.amushinsky.storage.service.exception.FabricNameExistsException;
import by.amushinsky.storage.service.exception.FabricValidationException;

@RestController
public class FabricController extends RestJSONController {

	@Autowired
	private FabricService fabricService;

	@RequestMapping(method = POST, value = "/fabrics", consumes = "application/json")
	public Fabric addFabric(@RequestBody Fabric fabric) {
		fabricService.addFabric(fabric);
		return fabric;
	}

	@RequestMapping(method = GET, value = "/fabrics")
	public List<Fabric> getAllFabrics() {
		return fabricService.getAllFabrics();
	}

	@RequestMapping(method = GET, value = "/stocks")
	public List<FabricStock> getAllFabricStocks() {
		return fabricService.getAllFabricStocks();
	}

	@RequestMapping(method = GET, value = "/fabrics/{id}/amount")
	public BigDecimal getFabricAmountById(@PathVariable int id) {
		return fabricService.getFabricAmountById(id);
	}

	@RequestMapping(method = GET, value = "/fabrics/amount")
	public BigDecimal getTotalFabricAmount() {
		return fabricService.getTotalFabricAmount();
	}

	@ResponseStatus(NOT_ACCEPTABLE)
	@ExceptionHandler(FabricValidationException.class)
	public List<String> validationFailure(FabricValidationException exception) {
		Set<ConstraintViolation<Fabric>> violations = exception.getViolations();
		return violations.stream().map( ConstraintViolation::getMessage ).collect(Collectors.toList());
	}

	@ResponseStatus(NOT_ACCEPTABLE)
	@ExceptionHandler(FabricNameExistsException.class)
	public List<String> notUniqueName(FabricNameExistsException exception) {
		return Collections.singletonList(exception.getMessage());
	}

	 
}
