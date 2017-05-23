package ac.shenkar.workshoptwo;

/**
 * In an application with a good layered architecture,
 * this model would only be the gateway to the domain layer or business logic.
 * See it as the provider of the data we want to display in the view.
 *
 * Created by amir on 5/18/17.
 */
public interface MyModelInterface {
    // abtracts implementation details: i.e. firebase should implement this model and expose only
    // the data and some listener/callback mechanism when that data changes.
    // so in testing we could mockup the firebase part (provide mock data to the presenter)

    boolean isLoggedIn();

}
