package ac.shenkar.workshoptwo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Example on DataBinding
 * <p>
 * BaseObservable is optional - if you'd like the values to update at runtime
 * <p>
 * https://developer.android.com/topic/libraries/data-binding/index.html
 * Created by amir on 3/4/17.
 */
public class MyData extends BaseObservable {

    private String text = "Text not set yet";

    public MyData(String s) {
        text = s;
    }

    @Bindable
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);

    }
}
