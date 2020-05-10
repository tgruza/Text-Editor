package Service;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements IService {
    private File file;
    private List<Integer> listOfIndexes = new ArrayList<>();
    private int counter = 0;

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


    @Override
    public void searchWord(String word, JTextArea textArea) {
        if (word.isEmpty()) {
            return;
        }
        listOfIndexes.clear();
        int index;
        counter = -1;
        String text = textArea.getText();

        if (text.contains(word)) {

            index = textArea.getText().indexOf(word);
            textArea.setCaretPosition(index + word.length());
            textArea.select(index, index + word.length());
            textArea.grabFocus();

            while (index != -1) {
                index = textArea.getText().indexOf(word, index + word.length());
                if (index != -1) {
                    listOfIndexes.add(index);
                }
            }
        }
    }

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

    @Override
    public String countOfFoundWords(JLabel label) {
        if (listOfIndexes.isEmpty()) {
            return "Words found: 0/0";
        }
        return "Words found: " + (counter + 2) + "/" + (listOfIndexes.size() + 1);
    }
}
