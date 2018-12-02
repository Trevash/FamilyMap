package cs240.trevash.familymap.shared.Objects;

/**
 * Created by trevash on 2/14/18.
 */

/**
 * Class to create a clear or fill result object - this will be a successful object
 */
public class ClearFillResult {
    private String message;

    /**
     * Constructs a clear or fill result object for a user request
     * @param M - success message
     */
    public ClearFillResult(String M) {
        this.message = M;
    }

    public String getMessage() {
        return message;
    }
}
