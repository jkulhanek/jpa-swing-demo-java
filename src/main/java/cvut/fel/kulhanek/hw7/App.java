package cvut.fel.kulhanek.hw7;

import cvut.fel.kulhanek.hw7.viewmodel.HomeViewModel;
import cvut.fel.kulhanek.hw7.viewmodel.HomeViewModelFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        ServiceContainer container = ServiceContainer.getCurrent();
        Presenter presenter = container.resolve(Presenter.class);
        HomeViewModel vm = container.resolve(HomeViewModelFactory.class).create();
        presenter.show(vm);
    }

    private void createServices(){

    }


}
