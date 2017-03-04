package ac.shenkar.workshoptwo;

/**
 * http://greenrobot.org/eventbus/documentation/how-to-get-started/
 * Created by amir on 3/4/17.
 */
public class MyEvent {

    private String message;

    public MyEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
