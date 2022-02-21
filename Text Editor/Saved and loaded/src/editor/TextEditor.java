package editor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
        forceSize(saveButton, 80, 25);

        JButton loadButton = new JButton("load");
        loadButton.setName("LoadButton");
        forceSize(loadButton, 80, 25);

        loadButton.addActionListener(e -> { // save the text from TextArea to the file whose name is entered to TextField
            String fileName = txtField.getText(); // get the entered file name
            String context = null;
            try {
                context = Files.readString(Paths.get(fileName)); // read the whole context
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            textArea.setText(context);
        });

        saveButton.addActionListener(e -> { // get the edited text and write to the same file
            String fileName = txtField.getText();
            String context = textArea.getText();
            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write(context);
            } catch (IOException error) {
                System.out.println("File Not Found");
            }
        });

        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout()); // set the layout the JPanel component
        pane.setBounds(40,150,220,70);

        // add the text field and buttons to panel, not frame
        pane.add(txtField);
        pane.add(saveButton);
        pane.add(loadButton);
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        add(pane, BorderLayout.NORTH);
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
