package by.amushinsky.storage.core;

import java.util.List;

public class TrialBalance {
	private List<FabricMovement> movements;
	private TotalMovement totalMovement;
	private TimePeriod timePeriod;

	public TrialBalance(List<FabricMovement> movements, TotalMovement totalMovement, TimePeriod timePeriod) {
		this.movements = movements;
		this.totalMovement = totalMovement;
		this.timePeriod = timePeriod;
	}

	public List<FabricMovement> getMovements() {
		return movements;
	}

	public TotalMovement getTotalMovement() {
		return totalMovement;
	}

	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	public void setMovements(List<FabricMovement> movements) {
		this.movements = movements;
	}

	public void setTotalMovement(TotalMovement totalMovement) {
		this.totalMovement = totalMovement;
	}

	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrialBalance other = (TrialBalance) obj;
		if (movements == null) {
			if (other.movements != null)
				return false;
		} else if (!movements.equals(other.movements))
			return false;
		if (timePeriod == null) {
			if (other.timePeriod != null)
				return false;
		} else if (!timePeriod.equals(other.timePeriod))
			return false;
		if (totalMovement == null) {
			if (other.totalMovement != null)
				return false;
		} else if (!totalMovement.equals(other.totalMovement))
			return false;
		return true;
	}
	
	
	
}
