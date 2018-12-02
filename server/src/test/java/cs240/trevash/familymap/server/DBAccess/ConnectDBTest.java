package cs240.trevash.familymap.server.DBAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertTrue;

/**
 * Created by trevash on 3/8/18.
 */

public class ConnectDBTest {
    ConnectDB db;

    @Before
    public void setUp() {
        db = new ConnectDB();
    }

    @After
    public void tearDown() {
        db = null;
    }
    @Test
    public void openAndCloseConnectionTest() throws DatabaseException {
        db.openConnection();
        Connection conn = db.getConn();
        assertTrue(conn != null);
        db.closeConnection(true);
        conn = db.getConn();
        assertTrue(conn == null);

    }
}
