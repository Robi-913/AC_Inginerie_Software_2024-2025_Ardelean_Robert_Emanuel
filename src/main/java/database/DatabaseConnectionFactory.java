package database;

public class DatabaseConnectionFactory {
    private static final String SCHEMA = "library";
    private static  final  String TEST_SCHEMA = "test_library";

    public static JDBConnectionWrapper getConnectionWrapper(boolean tets) {
        return tets ? new JDBConnectionWrapper(TEST_SCHEMA) : new JDBConnectionWrapper(SCHEMA);
    }
}
