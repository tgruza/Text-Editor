package Controllers;

import Service.IService;
import Views.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

//Main Controller class, which have logic to AppButtons
public class MainController {


    private static boolean alreadyExecuted = false; //ensures that Action Listeners to App Buttons execute only once
    Views.Main mainView;
    IService service;
    JFrame frame;
    File selectedFile;

    public MainController(Main mainView, IService service) {
        this.mainView = mainView;
        this.service = service;

        if (!alreadyExecuted) {
            mainView.setSaveButton(new setSaveButton());
            mainView.setLoadButton(new setOpenButton());
            mainView.setSearchButton(new setSearchButton());
            mainView.setNextButton(new setNextButton());
            mainView.setPreviousButton(new setPreviousButton());
            alreadyExecuted = true;
        }
    }

    //Inner classes, which sets an Action Listeners to particular AppButtons
    class setSaveButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                selectedFile = new File (service.saveChooseFile(selectedFile));

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
    class setOpenButton implements ActionListener {
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

    class setSearchButton implements ActionListener {
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
    class setNextButton implements ActionListener {
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
    class setPreviousButton implements ActionListener {
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

    //PopUp windows, which prevent from overwrite files, loose text, or exit program for accident
    int savePopupConfirmationWindow() {
        return JOptionPane.showConfirmDialog(frame, "Do you want to overwrite file?", "Overwrite", JOptionPane.YES_NO_OPTION);
    }
    int loadPopupConfirmationWindow() {
        return JOptionPane.showConfirmDialog(frame, "Do you want to load new file? You'll loose your progress.", "Load", JOptionPane.YES_NO_OPTION);
    }
    int exitPopupConfirmationWindow() {
        return JOptionPane.showConfirmDialog(frame, "Do you want to exit? You'll loose your progress.", "Exit", JOptionPane.YES_NO_OPTION);
    }

    //Shows Frame
    public void show(JFrame frame) {
        mainView.show(frame);
    }
    //Shows Menu Bar
    public void showMenuBar(JFrame frame) { mainView.showMenuBar(frame);}


    public Main getMainView() {
        return mainView;
    }
    public File getSelectedFile() {
        return selectedFile;
    }
}
