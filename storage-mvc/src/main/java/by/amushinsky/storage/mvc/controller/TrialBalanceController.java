package by.amushinsky.storage.mvc.controller;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.service.api.TrialBalanceService;

@Controller
public class TrialBalanceController {
	private TrialBalanceService trialBalanceService;

	private DateFormat dateFormat;

	@Autowired
	public void setTrialBalanceService(TrialBalanceService trialBalanceService) {
		this.trialBalanceService = trialBalanceService;
	}

	@Autowired
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	@InitBinder
	protected void InitBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping(value = "/trial", method = RequestMethod.GET)
	public void defaultTrialBalance(Model model) {
		TimePeriod timePeriod = defaultTimePeriod();
		model.addAttribute(timePeriod);
		model.addAttribute(trialBalanceService.getTrialBalance(timePeriod));
	}

	@RequestMapping(value = "/trial", method = RequestMethod.POST)
	public void trialBalance(Model model, @Valid TimePeriod timePeriod) {
		model.addAttribute(timePeriod);
		model.addAttribute(trialBalanceService.getTrialBalance(timePeriod));
	}

	private TimePeriod defaultTimePeriod() {
		Calendar now = new GregorianCalendar();
		return new TimePeriod(new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1),
				new GregorianCalendar());
	}

}
