package by.amushinsky.storage.service.fixtures;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import by.amushinsky.storage.core.Fabric;


public class FabricServiceFixtures {

	public static final int FABRIC_ID = 1;
	public static final String FABRIC_NAME = "FABRIC_NAME";
	public static final String RESERVED_NAME = "RESERVED_NAME";
	public static final String RESERVED_NAME_EXCEPTION_MSG = "Name '" + RESERVED_NAME + "' is reserved.";
	
	public static Fabric fabric(){
		return new Fabric(FABRIC_ID, FABRIC_NAME);
	}
	
	public static Fabric invalidFabric(){
		return new Fabric(FABRIC_ID, null);
	}
	
	public static final Set<ConstraintViolation<Fabric>> violations(){
		Set<ConstraintViolation<Fabric>> violations = new HashSet<>();
		violations.add(null);
		return violations;
	}
}
