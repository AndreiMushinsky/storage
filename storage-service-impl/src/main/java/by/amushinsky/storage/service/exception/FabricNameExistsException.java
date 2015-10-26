package by.amushinsky.storage.service.exception;

import java.text.MessageFormat;

import by.amushinsky.storage.core.Fabric;

public class FabricNameExistsException extends RuntimeException {

	private static final String ERROR_MESSAGE_TEMPLATE = "fabric with name ''{0}'' already exists";
	private static final long serialVersionUID = 1L;

	public FabricNameExistsException(String name) {
		super(MessageFormat.format(ERROR_MESSAGE_TEMPLATE, name));
	}

	public FabricNameExistsException(Fabric fabric) {
		super(MessageFormat.format(ERROR_MESSAGE_TEMPLATE, fabric.getName()));
	}
	
}
