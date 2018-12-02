package cs240.trevash.familymap.server.DBAccess;

import java.sql.SQLException;

/**
 * Created by trevash on 2/15/18.
 */

public class DatabaseException extends Exception {
    private String error;

    DatabaseException(String error, SQLException e) {
        System.out.println(error);
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
