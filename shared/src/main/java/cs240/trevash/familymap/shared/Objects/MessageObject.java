package cs240.trevash.familymap.shared.Objects;

/**
 * Created by trevash on 3/5/18.
 */

/**
 * Message object used for various responses that only require one string message in response
 */
public class MessageObject {
    private String message;

    public MessageObject(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
