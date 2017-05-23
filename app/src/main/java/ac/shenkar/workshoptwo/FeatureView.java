package ac.shenkar.workshoptwo;

/**
 * The View, usually implemented by an Activity, will contain a reference to the presenter.
 * The only thing that the view will do is to call a method from the Presenter every time there is an interface action.
 * Created by amir on 5/18/17.
 */
public interface FeatureView {
    void showLoggedInFrag();

//    void showLoginDialog();

    void showAnonymousFrag();
}
