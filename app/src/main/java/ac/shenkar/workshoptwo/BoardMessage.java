package ac.shenkar.workshoptwo;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

/**
 * This is the object loaded to Firebase and also used inside the app
 * <p>
 * http://greenrobot.org/eventbus/documentation/how-to-get-started/
 * Created by amir on 3/4/17.
 */
public class BoardMessage {

    private String message;
    private long timestamp;
    private Long creationDate;

    public java.util.Map<String, String> getCreationDate() {
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public Long getCreationDateLong() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }


    public BoardMessage() {
        timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
