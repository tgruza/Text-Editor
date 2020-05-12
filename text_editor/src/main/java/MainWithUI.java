import Controllers.MainController;
import Controllers.MenuBarController;
import Service.IService;
import Service.ServiceImpl;
import Views.Main;

import javax.swing.*;


//Main class with main method to run the application
public class MainWithUI {

    public static void main(String[] args) {

        IService service = new ServiceImpl();
        Main mainView = new Main();
        JFrame frame = new JFrame();
        MainController mainController = new MainController(mainView, service);
        mainController.showMenuBar(frame);
        MainController mainController1 = new MenuBarController(mainView, service);


        mainController.show(frame);
    }
}