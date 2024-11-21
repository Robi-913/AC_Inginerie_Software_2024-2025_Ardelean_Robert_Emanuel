package database;

import java.util.*;

public class Constants {

    // Returns a map of roles and their associated rights
    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> rolesRights = new HashMap<>();

        // Initialize roles with empty rights lists
        for (String role : Roles.ROLES) {
            rolesRights.put(role, new ArrayList<>());
        }

        // Assign rights to roles
        rolesRights.get(Roles.ADMINISTRATOR).addAll(Arrays.asList(Rights.RIGHTS));
        rolesRights.get(Roles.EMPLOYEE).addAll(Arrays.asList(
                Rights.CREATE_BOOK, Rights.DELETE_BOOK, Rights.UPDATE_BOOK, Rights.SELL_BOOK));
        rolesRights.get(Roles.CUSTOMER).addAll(Arrays.asList(
                Rights.SELL_BOOK, Rights.BUY_BOOK, Rights.RETURN_BOOK));

        return rolesRights;
    }

    // Nested class for Roles
    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";
        public static final String CUSTOMER = "customer";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE, CUSTOMER};
    }

    // Nested class for Schemas
    public static class Schemas {
        public static final String TEST = "test_library";
        public static final String PRODUCTION = "library";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    // Nested class for Tables
    public static class Tables {
        public static final String BOOK = "book";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";

        public static final String[] ORDERED_TABLES_FOR_CREATION =
                new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE, BOOK};
    }

    // Nested class for Rights
    public static class Rights {
        public static final String CREATE_USER = "create_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";

        public static final String CREATE_BOOK = "create_book";
        public static final String DELETE_BOOK = "delete_book";
        public static final String UPDATE_BOOK = "update_book";

        public static final String SELL_BOOK = "sell_book";
        public static final String BUY_BOOK = "buy_book";
        public static final String RETURN_BOOK = "return_book";

        public static final String[] RIGHTS = new String[]{
                CREATE_USER, DELETE_USER, UPDATE_USER,
                CREATE_BOOK, DELETE_BOOK, UPDATE_BOOK,
                SELL_BOOK, BUY_BOOK, RETURN_BOOK
        };
    }
}
