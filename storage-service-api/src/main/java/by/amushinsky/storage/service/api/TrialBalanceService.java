package by.amushinsky.storage.service.api;

import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.core.TrialBalance;

public interface TrialBalanceService {
	TrialBalance getTrialBalance(TimePeriod timePeriod);

}
