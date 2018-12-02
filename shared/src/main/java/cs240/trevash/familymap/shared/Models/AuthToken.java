package cs240.trevash.familymap.shared.Models;

/**
 * Created by trevash on 2/13/18.
 */

import java.util.UUID;

/**
 * Generates an Authentication Token for a logged in user.
 * Can Generate multiple tokens for the same user logged in on separate Clients
 * Should generate unique tokens for each login
 * Should be able to save logged in authentication tokens so users aren't required to pass userName and password frequently
 */
public class AuthToken {
    private UUID authToken;

    /**
     *
     */
    public AuthToken() {
        this.authToken = UUID.randomUUID();
    }

    public UUID getAuthToken() {
        return authToken;
    }

}
