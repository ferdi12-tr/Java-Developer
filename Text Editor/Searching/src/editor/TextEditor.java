package editor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {
    private JFileChooser jFileChooser;
    private JTextComponent textArea;
    private JTextComponent  searchArea;
    private JCheckBox regexCheckBox ;
    private String filePath = null;
    private static final Path ICONROOT = Path.of("C:\\Users\\fkoca\\IdeaProjects\\Text Editor\\Text Editor\\task\\src\\editor\\icons");
    private final List<String> words = new ArrayList<>(); // results regex matcher finds
    private final List<Integer> indexes = new ArrayList<>(); // index of found words
    private String context;
    private int current;

    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLayout(new BorderLayout()); // set the layout of the JFrame component
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        initTextArea();
        initTextField();
        initFileChooser();
        setJMenuBar(initMenu());
        add(initPanel(), BorderLayout.NORTH);
    }

    private JMenuBar initMenu() {
        // create menu
        JMenuBar menuBar = new JMenuBar();

        // create File menu items
        JMenu fileMenu = new JMenu("File"); // the submenu save, load, exit must be placed under the file menu
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem save = new JMenuItem("Save");
        save.setName("MenuSave");
        save.addActionListener(e -> saveFile()); // save button and save menu option performs the same action

        JMenuItem open = new JMenuItem("Open");
        open.setName("MenuOpen");
        open.addActionListener(e -> openFile()); // load button and load menu option performs the same action

        JMenuItem exit = new JMenuItem("Exit");
        exit.setName("MenuExit");
        exit.addActionListener(e -> System.exit(0));

        //create Search menu items
        JMenu searchMenu = new JMenu("Search"); // the submenu 'start search', 'previous search', 'next match', 'use regular expressions' must be placed under the Search menu
        searchMenu.setName("MenuSearch");

        JMenuItem startSearch = new JMenuItem("Start Search");
        startSearch.setName("MenuStartSearch");
        startSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // search action must be done at background thread
                new SwingWorker<>() {
                    @Override
                    protected Object doInBackground() {
                        findAllWords();
                        return null;
                    }
                }.execute();
            }
        });

        JMenuItem previousSearch = new JMenuItem("Previous Search");
        previousSearch.setName("MenuPreviousMatch");
        previousSearch.addActionListener(e -> previousMatchButton());

        JMenuItem nextMatch = new JMenuItem("Next Match");
        nextMatch.setName("MenuNextMatch");
        nextMatch.addActionListener(e -> nextMatchButton());

        JMenuItem regexMenu = new JMenuItem("Use Regular Expressions");
        regexMenu.setName("MenuUseRegExp");
        regexMenu.addActionListener(e -> regexCheckBox.setSelected(!regexCheckBox.isSelected())); // with the regex menu, we also change the state of check box

        fileMenu.add(save);
        fileMenu.add(open);
        fileMenu.add(exit);

        searchMenu.add(startSearch);
        searchMenu.add(previousSearch);
        searchMenu.add(nextMatch);
        searchMenu.add(regexMenu);

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);

        return menuBar;
    }

    private JPanel initPanel() {
        ImageIcon originalSaveIcon = new ImageIcon(resolveImage("save.png"));
        Image scaleSave = scaleImage(originalSaveIcon.getImage(), 30, 30);
        ImageIcon scaledSave = new ImageIcon(scaleSave);
        JButton saveButton = new JButton(scaledSave);
        saveButton.setName("SaveButton");
        saveButton.addActionListener(e -> saveFile());

        ImageIcon originalOpenIcon = new ImageIcon(resolveImage("load.png"));
        Image scaleOpen = scaleImage(originalOpenIcon.getImage(), 30,30);
        ImageIcon scaledOpen = new ImageIcon(scaleOpen);
        JButton openButton = new JButton(scaledOpen);
        openButton.setName("OpenButton");
        openButton.addActionListener(e -> openFile());

        ImageIcon originalSearchIcon = new ImageIcon(resolveImage("search.png"));
        Image scaleSearch = scaleImage(originalSearchIcon.getImage(), 30, 30);
        ImageIcon scaledSearch = new ImageIcon(scaleSearch);
        JButton search = new JButton(scaledSearch);
        search.setName("StartSearchButton");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker<>() { // search action must be done at background thread
                    @Override
                    protected Object doInBackground() {
                        findAllWords();
                        return null;
                    }
                }.execute();
            }
        });

        ImageIcon originalPreviousIcon = new ImageIcon(resolveImage("previous.png"));
        Image scalePrevious = scaleImage(originalPreviousIcon.getImage(), 30, 30);
        ImageIcon scaledPrevious = new ImageIcon(scalePrevious);
        JButton previous = new JButton(scaledPrevious);
        previous.setName("PreviousMatchButton");
        previous.addActionListener(e -> previousMatchButton());

        ImageIcon originalNextIcon = new ImageIcon(resolveImage("next.png"));
        Image scaleNext = scaleImage(originalNextIcon.getImage(), 30, 30);
        ImageIcon scaledNext = new ImageIcon(scaleNext);
        JButton next = new JButton(scaledNext);
        next.setName("NextMatchButton");
        next.addActionListener(e -> nextMatchButton());

        regexCheckBox = new JCheckBox("Use Regex", true);
        regexCheckBox.setName("UseRegExCheckbox");

        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout()); // set the layout the JPanel component
        pane.setBounds(40,150,220,70);

        // add the text field and buttons components to panel, not frame
        pane.add(saveButton);
        pane.add(openButton);
        pane.add(searchArea);
        pane.add(search);
        pane.add(previous);
        pane.add(next);
        pane.add(regexCheckBox);
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        return pane;
    }

    private void initTextField() {
        searchArea = new JTextField(); // for entering file name
        searchArea.setName("SearchField");
        forceSize(searchArea, 200, 40);
    }

    private void initTextArea() {
        textArea = new JTextArea(20, 20);
        textArea.setName("TextArea");
        JScrollPane scrollPane = new JScrollPane(textArea); // scroll panel for JTextArea
        scrollPane.setName("ScrollPane");
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
    }

    private void initFileChooser() {
        jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooser.setName("FileChooser");
        jFileChooser.setVisible(false);
        add(jFileChooser, BorderLayout.PAGE_END);
    }

    private void saveFile() {
        jFileChooser.setDialogTitle("Save file");
        jFileChooser.setVisible(true);
        int retval = jFileChooser.showSaveDialog(null);
        if (retval == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();

            context = textArea.getText();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(context);
            } catch (IOException error) {
                System.out.println("File Not Found");
            } finally {
                jFileChooser.setVisible(false);
            }
        }
    }

    private void previousMatchButton() {
        if (current == 0) {current = words.size();}
        if (current > 0) {
            current--;
            String foundText = words.get(current);
            Integer index = indexes.get(current) ;

            textArea.setCaretPosition(index + foundText.length());
            textArea.select(index, index + foundText.length());
            textArea.grabFocus();
        }

    }

    private void nextMatchButton() {
        if (current == words.size() - 1) {current = -1;} // if we reach the end of found words, we must keep going in the first place.
        if (current < words.size()) {
            current++;
            String foundText = words.get(current);
            Integer index = indexes.get(current) ;

            textArea.setCaretPosition(index + foundText.length());
            textArea.select(index, index + foundText.length());
            textArea.grabFocus();
        } else {
            current = words.size();
        }
    }

    private void findAllWords() {
        String allText = textArea.getText();
        String word = searchArea.getText();
        Pattern p = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(allText);

        if (!words.isEmpty()) {
            words.clear();
            indexes.clear();
        }

        while (matcher.find()) {
            words.add(matcher.group());
            indexes.add(matcher.start());
        }

        current = -1;
        nextMatchButton();
    }

    private void openFile() {
        context = "";
        jFileChooser.setDialogTitle("Open file");
        jFileChooser.setVisible(true);
        int returnValue = jFileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            filePath = selectedFile.getAbsolutePath();
        }
        try {
            context = Files.readString(Paths.get(filePath)); // read the whole context
        } catch (IOException ex) {
            System.out.println("File Not Found");
        } finally {
            jFileChooser.setVisible(false);
        }
        textArea.setText(context);
    }

    public static void forceSize(JComponent component, int width, int height) {
        Dimension d = new Dimension(width, height);
        component.setMinimumSize(d);
        component.setMaximumSize(d);
        component.setPreferredSize(d);
    }

    private String resolveImage(final String imageName) { // to load icons from local
        return ICONROOT.resolve(imageName).toString();
    }

    private Image scaleImage(Image image, int w, int h) {

        Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);

        return scaled;
    }
}
