package by.amushinsky.storage.dao.fixtures;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.core.FabricStock;

@Component
public class FabricDAOFixtures {

	@Autowired
	private Environment env;
	
	public Fabric fabric(){
		return new Fabric(env.getProperty("fabric.name"));
	}
	
	public Fabric fabricWithId(){
		return new Fabric(env.getProperty("fabric.id", Integer.class), env.getProperty("fabric.name"));
	}
	
	public BigDecimal fabricAmount(int id){
		return env.getProperty("fabric.amount", BigDecimal.class);
	}
	
	public List<Fabric> fabrics(){
		return IntStream.rangeClosed(1, 4)
				.mapToObj(i -> env.getProperty("fabrics.name._"+i))
				.sorted()
				.map(name -> new Fabric(name))
				.collect(Collectors.toList());
	}
	
	public List<FabricStock> stocks(){
		return IntStream.rangeClosed(1, 4)
				.mapToObj(i -> {
					String name = env.getProperty("fabrics.name._"+i);
					BigDecimal amount = env.getProperty("fabrics.amount._"+i, BigDecimal.class);
					return new FabricStock(name, amount);
				}).sorted( (fst, snd) -> fst.getName().compareTo(snd.getName()) )
				.collect(Collectors.toList());
	}
	
	public List<FabricStock> emptyStocks(){
		return IntStream.rangeClosed(1, 4)
				.mapToObj(i -> {
					String name = env.getProperty("fabrics.name._"+i);
					BigDecimal amount = new BigDecimal(0);
					return new FabricStock(name, amount);
				}).sorted( (fst, snd) -> fst.getName().compareTo(snd.getName()) )
				.collect(Collectors.toList());
	}
	
	public String unReservedName(){
		String name = fabric().getName();
		return name + name;
	}

}
