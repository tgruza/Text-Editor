package Controllers;

import Service.IService;
import Views.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

//Menu Controller Class, adds Action Listeners to Menu Buttons
public class MenuBarController extends MainController {

    public MenuBarController(Main mainView, IService service) {
        super(mainView, service);

        mainView.setSaveMenuButton(new setSaveMenuButton());
        mainView.setLoadMenuButton(new setOpenMenuButton());
        mainView.setExitMenuButton(new setExitMenuButton());
        mainView.setSearchMenuButton(new setSearchMenuButton());
        mainView.setNextMenuButton(new setNextMenuButton());
        mainView.setPreviousMenuButton(new setPreviousMenuButton());
    }

    //Inner classes, which sets Action Listeners to Menu Buttons
    class setSaveMenuButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                File selectedFile = new File(service.saveChooseFile(getSelectedFile()));

                if (selectedFile.getPath().equals("")) {
                    return;
                }
                if (!selectedFile.getAbsolutePath().contains(".txt")) {
                    selectedFile = new File (selectedFile.getAbsolutePath() + ".txt");
                }
                if (selectedFile.exists()) {
                    int result = savePopupConfirmationWindow();
                    if (result == 0) {
                        service.saveFile(selectedFile.getAbsolutePath(), mainView);
                    } if(result == 1) {
                        actionPerformed(e);
                    }
                }
                if (!selectedFile.exists()) {
                    service.saveFile(selectedFile.getAbsolutePath(), mainView);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    class setOpenMenuButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (!getMainView().getTextField().getText().isEmpty() && loadPopupConfirmationWindow() == 0) {
                    service.loadFile(service.openChooseFile(getSelectedFile()), getMainView());
                }
                if (getMainView().getTextField().getText().isEmpty()) {
                    service.loadFile(service.openChooseFile(getSelectedFile()), getMainView());
                }
            } catch (Exception ex) {
                mainView.getTextField().setText("Wrong path, select new file.");
            }
        }
    }
    class setExitMenuButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!getMainView().getTextField().getText().isEmpty() && exitPopupConfirmationWindow() == 0) {
                System.exit(5);
            } if (getMainView().getTextField().getText().isEmpty()) {
                System.exit(5);
            }
        }
    }

    class setSearchMenuButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                service.searchWord(mainView.getPathField().getText(), mainView.getTextField());
                mainView.getWordsCounter().setText(service.countOfFoundWords(mainView.getWordsCounter()));
            } catch (Exception ex) {
                mainView.getTextField().setText("wrong");
            }
        }
    }
    class setNextMenuButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                service.nextSearch(mainView.getPathField().getText(), mainView.getTextField());
                mainView.getWordsCounter().setText(service.countOfFoundWords(mainView.getWordsCounter()));
            } catch (Exception ex) {
                mainView.getTextField().setText("wrong");
            }
        }
    }
    class setPreviousMenuButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                service.previousSearch(mainView.getPathField().getText(), mainView.getTextField());
                mainView.getWordsCounter().setText(service.countOfFoundWords(mainView.getWordsCounter()));
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }
    }

}
