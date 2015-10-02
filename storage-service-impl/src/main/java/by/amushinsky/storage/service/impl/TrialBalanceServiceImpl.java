package by.amushinsky.storage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.core.TrialBalance;
import by.amushinsky.storage.dao.api.TrialBalanceDAO;
import by.amushinsky.storage.service.api.TrialBalanceService;

@Service
public class TrialBalanceServiceImpl implements TrialBalanceService {
	private TrialBalanceDAO trialBalanceDAO;

	@Autowired
	public void setTrialBalanceDAO(TrialBalanceDAO trialBalanceDAO) {
		this.trialBalanceDAO = trialBalanceDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public TrialBalance getTrialBalance(TimePeriod timePeriod) {
		return new TrialBalance(trialBalanceDAO.getMovements(timePeriod), trialBalanceDAO.getTotalMovement(timePeriod),
				timePeriod);
	}

}
