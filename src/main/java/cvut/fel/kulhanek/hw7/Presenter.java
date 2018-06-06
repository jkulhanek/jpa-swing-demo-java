package cvut.fel.kulhanek.hw7;

import cvut.fel.kulhanek.hw7.viewmodel.HomeViewModel;
import cvut.fel.kulhanek.hw7.viewmodel.SelectCategoryViewModel;
import cvut.fel.kulhanek.hw7.viewmodel.UpdateProductViewModel;
import cvut.fel.kulhanek.hw7.viewmodel.ViewModel;

import javax.swing.*;

public class Presenter {
    public void show(ViewModel viewModel){
        if(viewModel instanceof HomeViewModel){
            JFrame frame = new JFrame("Product editor");
            frame.setContentPane(new HomeWindow((HomeViewModel)viewModel).getContentPane());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }
        else if(viewModel instanceof SelectCategoryViewModel){
            SelectCategoryWindow window = new SelectCategoryWindow((SelectCategoryViewModel)viewModel);
            window.pack();
            window.setVisible(true);
        }
        else if(viewModel instanceof UpdateProductViewModel){
            UpdateProductWindow window = new UpdateProductWindow((UpdateProductViewModel)viewModel);
            window.pack();
            window.setVisible(true);
        }
    }

    public void showError(String text) {
        JOptionPane.showMessageDialog(null, text, "Error occured", JOptionPane.ERROR_MESSAGE);
    }
}
