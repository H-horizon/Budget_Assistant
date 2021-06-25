package android.h.horizon.budget_assistant.database;

/**
 * Represents the attributes' and the database table's names
 */
public class TransactionDbSchema {
    public static final String NAME = "transactions";

    public static final class Columns {
        public static final String UUID = "uuid";
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String DESCRIPTION = "description";
        public static final String AMOUNT = "amount";
        public static final String NEW = "new";
    }

}
