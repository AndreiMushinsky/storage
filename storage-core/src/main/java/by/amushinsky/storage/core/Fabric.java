package by.amushinsky.storage.core;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Fabric {

    public static final int NAME_MIN_LENGTH = 3;
    
    public static final int NAME_MAX_LENGTH = 30;

    private int id;

    @NotNull
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH)
    private String name;

    public Fabric() {}

    public Fabric(String name) {
        this.name = name;
    }

    public Fabric(int id, String name) {
        this(name);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Fabric other = (Fabric) obj;
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fabric [id=" + id + ", name=" + name + "]";
    }

}
