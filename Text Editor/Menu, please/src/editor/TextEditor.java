package editor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextEditor extends JFrame {
    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new BorderLayout()); // set the layout of the JFrame component
        initComponents();
        setVisible(true);
    }

    private void initComponents() {

        JTextArea textArea = new JTextArea(20, 20);
        textArea.setName("TextArea");
        JScrollPane scrollPane = new JScrollPane(textArea); // scroll panel for JTextArea
        scrollPane.setName("ScrollPane");
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        JTextField txtField = new JTextField(); // for entering file name
        txtField.setName("FilenameField");
        forceSize(txtField, 100, 25);

        JButton saveButton = new JButton("save");
        saveButton.setName("SaveButton");
        saveButton.addActionListener(e -> saveButtonAction(txtField.getText(), textArea));
        forceSize(saveButton, 80, 25);

        JButton loadButton = new JButton("load");
        loadButton.setName("LoadButton");
        loadButton.addActionListener(e -> loadButtonAction(txtField.getText(), textArea));
        forceSize(loadButton, 80, 25);

        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout()); // set the layout the JPanel component
        pane.setBounds(40,150,220,70);

        // add the text field and buttons components to panel, not frame
        pane.add(txtField);
        pane.add(saveButton);
        pane.add(loadButton);
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        add(pane, BorderLayout.NORTH);

        // create menu
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File"); // the submenu save, load, exit must be placed under the file menu
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem save = new JMenuItem("Save");
        save.setName("MenuSave");
        // save button and save menu option performs the same action
        save.addActionListener(e -> saveButtonAction(txtField.getText(), textArea));

        JMenuItem load = new JMenuItem("Load");
        load.setName("MenuLoad");
        // load button and load menu option performs the same action
        load.addActionListener(e -> loadButtonAction(txtField.getText(), textArea));

        JMenuItem exit = new JMenuItem("Exit");
        exit.setName("MenuExit");
        exit.addActionListener(e -> System.exit(0));

        fileMenu.add(save);
        fileMenu.add(load);
        fileMenu.add(exit);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

    }
    // the method for save button and save menu option
    private void saveButtonAction(String fileName, JTextArea textArea) {
        String context = textArea.getText();
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(context);
        } catch (IOException error) {
            System.out.println("File Not Found");
        }
    }
    //the method for load button and load menu option
    private void loadButtonAction(String fileName, JTextArea textArea) {
        String context = null;
        try {
            context = Files.readString(Paths.get(fileName)); // read the whole context
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        textArea.setText(context);
    }

    public static void forceSize(JComponent component, int width, int height) {
        Dimension d = new Dimension(width, height);
        component.setMinimumSize(d);
        component.setMaximumSize(d);
        component.setPreferredSize(d);
    }

    public static void setMargin(JComponent aComponent, int aTop,
                                 int aRight, int aBottom, int aLeft) {

        Border border = aComponent.getBorder();

        Border marginBorder = new EmptyBorder(new Insets(aTop, aLeft,
                aBottom, aRight));
        aComponent.setBorder(border == null ? marginBorder
                : new CompoundBorder(marginBorder, border));
    }
}
