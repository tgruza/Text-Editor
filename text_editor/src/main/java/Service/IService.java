package Service;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

//Service interface. Contains methods used in application
public interface IService {

    void saveFile(String path, Views.Main mainView) throws IOException; // Method takes path from first attribute, and saves text from second attribute to new file
    void loadFile(String path, Views.Main mainView); //Method takes path from first attribute, and open .txt file

    String openChooseFile(File selectedFile); //Opens fileChooser Open Window, and allow user to select file. Then method returns path to file
    String saveChooseFile(File selectedFile); //Opens fileChooser Save Window, and allow user to select file. Then method returns path to file

    void searchWord(String word, JTextArea textField); //Allows user to search a word from first attribute in text, saves all matches in List
    void nextSearch(String word, JTextArea textArea); //Methods chooses next math for searched word
    void previousSearch(String word, JTextArea textArea); //Chooses previous math for searched word

    String countOfFoundWords(JLabel label); //Counts all search words
}
