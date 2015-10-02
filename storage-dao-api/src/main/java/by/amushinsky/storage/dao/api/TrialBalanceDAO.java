package by.amushinsky.storage.dao.api;


import java.util.List;

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.core.TotalMovement;

public interface TrialBalanceDAO {
	List<FabricMovement> getMovements(TimePeriod timePeriod);

	TotalMovement getTotalMovement(TimePeriod timePeriod);
}
