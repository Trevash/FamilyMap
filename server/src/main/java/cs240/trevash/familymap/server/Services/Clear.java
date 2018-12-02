package cs240.trevash.familymap.server.Services;

import java.sql.Connection;

import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.shared.Objects.ClearFillResult;

/**
 * Created by trevash on 2/14/18.
 */

/**
 * Clears the database and returns a successful clear message
 */
public class Clear {

    /**
     * Calls clearDB and returns a clear fill result if it is successful
     * @return clear fill result object to be sent to user
     */
    public ClearFillResult clear() {
        ConnectDB db = new ConnectDB();

        try {
            db.openConnection();
            Connection conn = db.getConn();
            db.getClearDB().clearDB(conn);
            db.closeConnection(true);
            return new ClearFillResult("Successful removal!");
        }
        catch(DatabaseException e) {
            try {
                db.closeConnection(false);
            }
            catch (DatabaseException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return new ClearFillResult("There was an error in the database");
        }
    }
}
