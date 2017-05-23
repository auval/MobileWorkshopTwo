package ac.shenkar.workshoptwo;

import org.junit.Before;
import org.junit.Test;

import ac.shenkar.workshoptwo.models.MyModel;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * check out http://site.mockito.org/
 * <p>
 * nice (though old) TDD tutorial to follow:  https://youtu.be/Asc4hU1iSTU
 * <p>
 * Created by amir on 5/23/17.
 */
public class FeaturePresenterTest {
    FeaturePresenter presenter;

    FeatureView view = mock(FeatureView.class);
    MyModel model = mock(MyModel.class);

    @Before
    public void setUp() throws Exception {
        // the production object to test
        presenter = new FeaturePresenter(view, model);

    }

    @Test
    public void checkNotNull() throws Exception {
        assertNotNull(view);
        assertNotNull(model);
        assertNotNull(presenter);
    }

    @Test
    public void checkThatShowAnonymousFragIsCalled() throws Exception {
        presenter.onButtonOneClicked();
        verify(view).showAnonymousFrag();
    }


}