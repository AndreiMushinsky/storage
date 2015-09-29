package by.amushinsky.storage.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.amushinsky.storage.core.Storage;
import by.amushinsky.storage.service.api.StorageService;

@Controller
public class StorageController {
	private StorageService storageService;
	
	@Autowired
	public void setStorageService(StorageService storageService){
		this.storageService = storageService;
	}
	
	@RequestMapping(value="/storage", method = RequestMethod.GET)
	public Storage getStorage(){
		return storageService.getStorage();
	}
}
