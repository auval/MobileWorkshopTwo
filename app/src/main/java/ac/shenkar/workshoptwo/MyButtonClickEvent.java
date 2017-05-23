package ac.shenkar.workshoptwo;

/**
 * http://greenrobot.org/eventbus/documentation/how-to-get-started/
 * Created by amir on 3/4/17.
 */
public class MyButtonClickEvent {

    private String message;
    private String fragTag;

    public MyButtonClickEvent(String message, String fragTag) {
        this.message = message;
        this.fragTag = fragTag;
    }

    public String getMessage() {
        return message;
    }

    public String getFragTag() {
        return fragTag;
    }
}
