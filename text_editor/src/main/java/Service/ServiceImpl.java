package Service;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//Class which implements method from IService interface
public class ServiceImpl implements IService {
    private File file;
    private List<Integer> listOfIndexes = new ArrayList<>();
    private int counter = 0;

    // Method takes path from first attribute, and saves text from second attribute to new file
    @Override
    public void saveFile(String path, Views.Main mainView) {
        try {
            if (path.equals("")) {
                return;
            }

            this.file = new File(path);

            if (file.exists()) {
                byte[] txtToBytes = mainView.getTextField().getText().getBytes();
                Files.write(Paths.get(path), txtToBytes);
            }
            if (!file.exists()) {
                Files.createFile(Paths.get(path));
                saveFile(path, mainView);
            }

        } catch (IOException e) {
            System.out.println("ex");
        }
    }

    //Method takes path from first attribute, and open .txt file
    @Override
    public void loadFile(String path, Views.Main mainView) {
        try {
            if (path.equals("")) {
                return;
            }
            String data = new String(Files.readAllBytes(Paths.get(path)));
            mainView.getTextField().setText(data);
        } catch (IOException ex) {
            mainView.getTextField().setText("Wrong path, file doesn't exist.");
        }
    }


    //Opens fileChooser Open Window, and allow user to select file. Then method returns path to file
    @Override
    public String openChooseFile(File selectedFile) {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = jfc.getSelectedFile();
        }
        if (returnValue == JFileChooser.CANCEL_OPTION) {
            return "";
        }
        return selectedFile.getAbsolutePath();
    }

    //Opens fileChooser Save Window, and allow user to select file. Then method returns path to file
    @Override
    public String saveChooseFile(File selectedFile) {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = jfc.getSelectedFile();
        }
        if (returnValue == JFileChooser.CANCEL_OPTION) {
            return "";
        }
        return selectedFile.getAbsolutePath();
    }

    //Allows user to search a word from first attribute in text, saves all matches in List
    @Override
    public void searchWord(String word, JTextArea textArea) {
        if (word.isEmpty()) {
            return;
        }
        listOfIndexes.clear();
        int index;
        counter = 0;
        String text = textArea.getText();

        if (text.contains(word)) {

            index = textArea.getText().indexOf(word);
            textArea.setCaretPosition(index + word.length());
            textArea.select(index, index + word.length());
            textArea.grabFocus();
            listOfIndexes.add(index);

            while (index != -1) {
                index = textArea.getText().indexOf(word, index + word.length());
                if (index != -1) {
                    listOfIndexes.add(index);
                }
            }
        }
    }

    //Methods chooses next math for searched word
    @Override
    public void nextSearch(String word, JTextArea textArea) {
        if (listOfIndexes.isEmpty() || counter == listOfIndexes.size()) {
            return;
        }
        if (counter < listOfIndexes.size() - 1) {
            counter++;
        }
        textArea.setCaretPosition(listOfIndexes.get(counter) + word.length());
        textArea.select(listOfIndexes.get(counter), listOfIndexes.get(counter) + word.length());
        textArea.grabFocus();

    }

    //Chooses previous math for searched word
    @Override
    public void previousSearch(String word, JTextArea textArea) {
        if (listOfIndexes.isEmpty() || counter == listOfIndexes.size()) {
            return;
        }
        if (counter > 0) {
            counter--;
        }
        textArea.setCaretPosition(listOfIndexes.get(counter) + word.length());
        textArea.select(listOfIndexes.get(counter), listOfIndexes.get(counter) + word.length());
        textArea.grabFocus();
    }

    //Counts all search words
    @Override
    public String countOfFoundWords(JLabel label) {
        if (listOfIndexes.isEmpty()) {
            return "Words found: 0/0";
        }
        return "Words found: " + (counter + 1) + "/" + listOfIndexes.size();
    }
}
