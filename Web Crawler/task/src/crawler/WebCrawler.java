package crawler;

import javax.swing.*;

public class WebCrawler extends JFrame {
    public WebCrawler() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setTitle("Simple Window");
        setLayout(null);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(50,50,200,200);
        textArea.setName("TextArea");
        textArea.setText("HTML code?");
        textArea.setEnabled(false);
        add(textArea);
        //


        setVisible(true);
    }
}