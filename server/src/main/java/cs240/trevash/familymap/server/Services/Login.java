package cs240.trevash.familymap.server.Services;

/**
 * Created by trevash on 2/14/18.
 */

import java.sql.Connection;
import java.util.UUID;

import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.shared.Objects.LoginRequest;
import cs240.trevash.familymap.shared.Objects.RegisterLoginResult;

/**
 * Attempts to log user into the database to retrieve an authorization token, userName, and password
 */
public class Login {

    /**
     * Returns a login result to a successfully logged in user with a new uuid
     * Makes request to UserDB to verify user is in DB,
     * Collects data if user in DB,
     * Generates authToken,
     * Makes request to authToken in AuthTokenDB
     * @param l - login request object to verify
     * @return returns a successfully logged in login result object
     */
    public RegisterLoginResult LoginUser(LoginRequest l) {
        ConnectDB db = new ConnectDB();

        try {
            db.openConnection();
            Connection conn = db.getConn();
            UUID newAuth = UUID.randomUUID();

            String validUserStatement = "SELECT personID FROM UserTable WHERE userName = '" + l.getUserName() + "' AND password = '"+ l.getPassword() +"';";
            String personID = db.getUserDB().userInTable(conn, validUserStatement);
            if (personID != null) {

                String insertAuthStatement = "INSERT INTO AuthTokenTable (authToken, userName) VALUES ('" + newAuth + "', '" + l.getUserName() + "');";
                db.getAuthTokenDB().insertAuth(conn, insertAuthStatement);

                RegisterLoginResult loginResult = new RegisterLoginResult(newAuth, l.getUserName(), personID);
                db.closeConnection(true);
                return loginResult;
            }
            else {
                RegisterLoginResult loginResult = new RegisterLoginResult("Not a valid user in the database");
                db.closeConnection(false);
                return loginResult;
            }
        }
        catch(DatabaseException e) {
            try {
                db.closeConnection(false);
            } catch (DatabaseException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return new RegisterLoginResult(e.getMessage());
        }
    }
}
