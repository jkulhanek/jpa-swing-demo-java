package cvut.fel.kulhanek.hw7.viewmodel;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewModel {
    private final List<Observer> observers = new ArrayList<>();


    private void loadAsync(){

    }

    protected void notifyChange(String name, Object newValue){
        for(Observer o : observers) {
            o.onChanged(name, newValue);
        }
    }

    public void addObserver(Observer observer){
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer){
        this.observers.remove(observer);
    }
}