package ac.shenkar.workshoptwo;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * http://greenrobot.org/eventbus/documentation/how-to-get-started/
 * Created by amir on 3/4/17.
 */
public class MyFragmentEvent {
    public static final int SOME_COMMAND = 1;
    public static final int SOME_OTHER_COMMAND = 2;
    public static final int SHOW_TOAST = 3;


    private int c;
    private String message;

    public MyFragmentEvent(@FRAG_COMMANDS int c, String message) {
        this.c = c;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCommand() {
        return c;
    }

    /**
     * Session wrapper id
     */
    @Retention(SOURCE)
    @IntDef({
            SOME_COMMAND,
            SOME_OTHER_COMMAND,
            SHOW_TOAST,
    })
    public @interface FRAG_COMMANDS {
    }
}
