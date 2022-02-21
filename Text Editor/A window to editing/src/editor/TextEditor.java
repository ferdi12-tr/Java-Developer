package editor;

import javax.swing.*;

public class TextEditor extends JFrame {
    public TextEditor() {
        super("TextArea");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);

        initComponents();

        setVisible(true);
        setLayout(null);
    }

    private void initComponents() {

        JTextArea area = new JTextArea();
        area.setBounds(140,20, 120,30);
        area.setName("TextArea");
        add(area);
    }
}
