package by.amushinsky.storage.dao.api;

import java.util.List;

import by.amushinsky.storage.core.Fabric;

public interface FabricDAO {
	List<Fabric> getFabrics();
	void create(Fabric fabric);
}
