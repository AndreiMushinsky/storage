package by.amushinsky.storage.dao.sql;

import java.io.IOException;
import java.lang.reflect.Field;
import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class Queries {

	private static final String SQL_PATH_KEY = "sql.path";
	private static final String SQL_FILE_EXTENSION = ".sql";

	@Autowired
	private Environment env;

	// FabricDAO
	private String insertFabric;
	private String countFabricsWithName;
	private String selectAllFabrics;
	private String selectAllFabricStocks;
	private String selectFabricAmountById;
	private String selectTotalFabricAmount;
	

	// JournalDAO
	private String deleteJournalEntryById;
	private String countEntriesWithId;
	private String insertJournalEntry;
	private String selectJournalEntries;
	private String selectJournalEntryById;
	private String selectFabricMovements;
	private String selectTotalFabricMovement;
	private String updateJournalEntry;

	public String getInsertFabric() {
		return insertFabric;
	}

	public String getSelectAllFabrics() {
		return selectAllFabrics;
	}

	public String getSelectAllFabricStocks() {
		return selectAllFabricStocks;
	}

	public String getSelectFabricAmountById() {
		return selectFabricAmountById;
	}

	public String getSelectTotalFabricAmount() {
		return selectTotalFabricAmount;
	}

	public void setInsertFabric(String insertFabric) {
		this.insertFabric = insertFabric;
	}

	public void setSelectAllFabrics(String selectAllFabrics) {
		this.selectAllFabrics = selectAllFabrics;
	}

	public void setSelectAllFabricStocks(String selectAllFabricStocks) {
		this.selectAllFabricStocks = selectAllFabricStocks;
	}

	public void setSelectFabricAmountById(String selectFabricAmountById) {
		this.selectFabricAmountById = selectFabricAmountById;
	}

	public void setSelectTotalFabricAmount(String selectTotalFabricAmount) {
		this.selectTotalFabricAmount = selectTotalFabricAmount;
	}

	public String getDeleteJournalEntryById() {
		return deleteJournalEntryById;
	}

	public String getInsertJournalEntry() {
		return insertJournalEntry;
	}

	public String getSelectJournalEntries() {
		return selectJournalEntries;
	}

	public String getSelectJournalEntryById() {
		return selectJournalEntryById;
	}

	public String getSelectFabricMovements() {
		return selectFabricMovements;
	}

	public String getSelectTotalFabricMovement() {
		return selectTotalFabricMovement;
	}

	public String getUpdateJournalEntry() {
		return updateJournalEntry;
	}

	public void setDeleteJournalEntryById(String deleteJournalEntryById) {
		this.deleteJournalEntryById = deleteJournalEntryById;
	}

	public void setInsertJournalEntry(String insertJournalEntry) {
		this.insertJournalEntry = insertJournalEntry;
	}

	public void setSelectJournalEntries(String selectJournalEntries) {
		this.selectJournalEntries = selectJournalEntries;
	}

	public void setSelectJournalEntryById(String selectJournalEntryById) {
		this.selectJournalEntryById = selectJournalEntryById;
	}

	public void setSelectFabricMovements(String selectFabricMovements) {
		this.selectFabricMovements = selectFabricMovements;
	}

	public void setSelectTotalFabricMovement(String selectTotalFabricMovement) {
		this.selectTotalFabricMovement = selectTotalFabricMovement;
	}

	public void setUpdateJournalEntry(String updateJournalEntry) {
		this.updateJournalEntry = updateJournalEntry;
	}

	public String getCountFabricsWithName() {
		return countFabricsWithName;
	}
	
	public void setCountFabricsWithName(String countFabricsWithName) {
		this.countFabricsWithName = countFabricsWithName;
	}
	
	public String getCountEntriesWithId() {
		return countEntriesWithId;
	}
	
	public void setCountEntriesWithId(String countEntriesWithId) {
		this.countEntriesWithId = countEntriesWithId;
	}
	
	@PostConstruct
	private void init() throws IOException, IllegalAccessException {
		for (Field field : this.getClass().getDeclaredFields()) {
			// only for not cnstant strings
			if (field.getType().equals(String.class) && Character.isLowerCase(field.getName().charAt(0))) {
				String fieldName = field.getName();
				// Constructing script file name
				StringBuffer buffer = new StringBuffer();
				for (char ch : fieldName.toCharArray())
					if (Character.isUpperCase(ch)) {
						buffer.append('_').append(Character.toLowerCase(ch));
					} else {
						buffer.append(ch);
					}
				String filePath = env.getProperty(SQL_PATH_KEY) + buffer.toString() + SQL_FILE_EXTENSION;
				String sql = IOUtils.toString(new ClassPathResource(filePath).getInputStream());
				field.set(this, sql);
			}
		}
	}

}
