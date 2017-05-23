package ac.shenkar.workshoptwo.models;

import ac.shenkar.workshoptwo.MyModelInterface;

/**
 * Created by amir on 5/20/17.
 */

public class MyModel implements MyModelInterface {
    @Override
    public boolean isLoggedIn() {
        return false;
    }
}
