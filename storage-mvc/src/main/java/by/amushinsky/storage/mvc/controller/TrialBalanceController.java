package by.amushinsky.storage.mvc.controller;

import java.text.DateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.service.api.TimePeriodService;
import by.amushinsky.storage.service.api.TrialBalanceService;

@Controller
public class TrialBalanceController {
	private TrialBalanceService trialBalanceService;
	private TimePeriodService timePeriodService;
	private DateFormat dateFormat;

	@Autowired
	public void setTrialBalanceService(TrialBalanceService trialBalanceService) {
		this.trialBalanceService = trialBalanceService;
	}

	@Autowired
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	@Autowired
	public void setTimePeriodService(TimePeriodService timePeriodService) {
		this.timePeriodService = timePeriodService;
	}
	
	@InitBinder
	protected void InitBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping(value = "/trial", method = RequestMethod.GET)
	public void defaultTrialBalance(Model model) {
		TimePeriod timePeriod = timePeriodService.defaultTimePeriod();
		model.addAttribute(timePeriod);
		model.addAttribute(trialBalanceService.getTrialBalance(timePeriod));
	}

	@RequestMapping(value = "/trial", method = RequestMethod.POST)
	public void trialBalance(Model model, HttpServletRequest request, @Valid TimePeriod timePeriod) {
		model.addAttribute(timePeriod);
		model.addAttribute(trialBalanceService.getTrialBalance(timePeriod));
	}

}
