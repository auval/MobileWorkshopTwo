package ac.shenkar.workshoptwo;

/**
 * http://greenrobot.org/eventbus/documentation/how-to-get-started/
 * Created by amir on 3/4/17.
 */
public class MyEvent {

    private String message;
    private int frag;

    public MyEvent(String message, int frag) {
        this.message = message;
        this.frag = frag;
    }

    public String getMessage() {
        return message;
    }

    public int getFrag() {
        return frag;
    }
}
