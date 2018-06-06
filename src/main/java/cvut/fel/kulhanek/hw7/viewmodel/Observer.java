package cvut.fel.kulhanek.hw7.viewmodel;

public interface Observer {
    void onChanged(String property, Object value);
}
