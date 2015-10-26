package by.amushinsky.storage.dao.sql;

public class TableConstants {
	public static class Fabrics{
		public static final String TABLE_NAME = "Fabrics";
		// Column names
		public static final String ID = "id";	
		public static final String NAME = "name";
		// Miscellaneous constants related to fabrics
		public static final String BALANCE = "balance";
	}
	public static class Journal{
		public static final String TABLE_NAME = "Journal";
		// Column names
		public static final String ID = "id";	
		public static final String DATE = "date";
		public static final String FABRIC_ID = "fabric_id";
		public static final String IS_DR = "is_dr";
		public static final String AMOUNT = "amount";
		// Miscellaneous constants related to fabrics
		public static final String START_BALANCE = "start_balance";
		public static final String END_BALANCE = "end_balance";
		public static final String DEBIT_MOVEMENT = "dr_movement";
		public static final String CREDIT_MOVEMENT = "cr_movement";
		public static final String TOTAL_NAME = "_total_";
	}
}
