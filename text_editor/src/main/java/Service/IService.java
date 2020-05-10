package Service;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public interface IService {
    void saveFile(String path, Views.Main mainView) throws IOException;
    void loadFile(String path, Views.Main mainView);

    String openChooseFile(File selectedFile);
    String saveChooseFile(File selectedFile);

    void searchWord(String word, JTextArea textField);
    void nextSearch(String word, JTextArea textArea);
    void previousSearch(String word, JTextArea textArea);
    String countOfFoundWords(JLabel label);
}
