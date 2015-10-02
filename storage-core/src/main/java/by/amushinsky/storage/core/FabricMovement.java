package by.amushinsky.storage.core;

import java.math.BigDecimal;

public class FabricMovement {
	private String name;
	private BigDecimal startBalance;
	private BigDecimal drMovement;
	private BigDecimal crMovement;
	private BigDecimal endBalance;

	public FabricMovement(String name, BigDecimal startBalance, BigDecimal drMovement, BigDecimal crMovement,
			BigDecimal endBalance) {
		this.name = name;
		this.startBalance = startBalance;
		this.drMovement = drMovement;
		this.crMovement = crMovement;
		this.endBalance = endBalance;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getStartBalance() {
		return startBalance;
	}

	public BigDecimal getDrMovement() {
		return drMovement;
	}

	public BigDecimal getCrMovement() {
		return crMovement;
	}

	public BigDecimal getEndBalance() {
		return endBalance;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	public void setDrMovement(BigDecimal drMovement) {
		this.drMovement = drMovement;
	}

	public void setCrMovement(BigDecimal crMovement) {
		this.crMovement = crMovement;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FabricMovement other = (FabricMovement) obj;
		if (crMovement == null) {
			if (other.crMovement != null)
				return false;
		} else if (!crMovement.equals(other.crMovement))
			return false;
		if (drMovement == null) {
			if (other.drMovement != null)
				return false;
		} else if (!drMovement.equals(other.drMovement))
			return false;
		if (endBalance == null) {
			if (other.endBalance != null)
				return false;
		} else if (!endBalance.equals(other.endBalance))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (startBalance == null) {
			if (other.startBalance != null)
				return false;
		} else if (!startBalance.equals(other.startBalance))
			return false;
		return true;
	}

}
