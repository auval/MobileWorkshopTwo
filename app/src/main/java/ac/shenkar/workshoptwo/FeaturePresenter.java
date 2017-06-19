package ac.shenkar.workshoptwo;

import javax.inject.Inject;

/**
 * The Presenter is responsible to act as the middle man between View and Model.
 * It retrieves data from the Model and returns it formatted to the View.
 * But unlike the typical MVC, it also decides what happens when you interact with the View.
 * Created by amir on 5/18/17.
 */
public class FeaturePresenter {

    // it's an interface implemented by the Activity, so I can replace it with a mockup for testing
    FeatureView myView;

    // This could be implemented with firebase or anything else
    MyModelInterface myModel;

    @Inject
    public FeaturePresenter(FeatureView view, MyModelInterface model) {
        myView = view;
        myModel = model;
    }

    public void onButtonAuthClicked() {
        // can call login here, show some
        myView.showLoggedInFrag();
    }

    public void onButtonOneClicked() {
        myView.showAnonymousFrag();
    }

    /**
     * called when the view is loaded
     */
    public void onViewStarted() {

    }

    /**
     * called when the view is paused
     */
    public void onViewPaused() {
    }


}
