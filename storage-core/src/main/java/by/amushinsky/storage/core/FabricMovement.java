package by.amushinsky.storage.core;

import java.math.BigDecimal;

public class FabricMovement {

    private String name;

    private BigDecimal start;

    private BigDecimal debit;

    private BigDecimal credit;

    private BigDecimal end;

    public FabricMovement(String name, BigDecimal start, BigDecimal debit,
            BigDecimal credit, BigDecimal end) {
        this.name = name;
        this.start = start;
        this.debit = debit;
        this.credit = credit;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getStart() {
        return start;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public BigDecimal getEnd() {
        return end;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(BigDecimal start) {
        this.start = start;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public void setEnd(BigDecimal end) {
        this.end = end;
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
        if (credit == null) {
            if (other.credit != null)
                return false;
        } else if (credit.compareTo(other.credit) != 0)
            return false;
        if (debit == null) {
            if (other.debit != null)
                return false;
        } else if (debit.compareTo(other.debit) != 0)
            return false;
        if (end == null) {
            if (other.end != null)
                return false;
        } else if (end.compareTo(other.end) != 0)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (start == null) {
            if (other.start != null)
                return false;
        } else if (start.compareTo(other.start) != 0)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "FabricMovement [name=" + name + ", start" + start + ", debit="
                + debit + ", credit=" + credit + ", end=" + end + "]";
    }

}
