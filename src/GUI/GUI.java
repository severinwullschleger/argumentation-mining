package GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by LuckyP on 16.01.18.
 */
public class GUI {
    private JLabel LToolTitle;
    private JPanel mainPanel;
    private JRadioButton rawTextRadioButton;
    private JRadioButton txtFilesRadioButton;
    private JPanel rawTextPanel;
    private JPanel textFilesPanel;
    private JTextField thirdSentenceTextField;
    private JTextField secondSentenceTextField;
    private JTextField firstSentenceTextField;
    private JTextField fourthSentenceTextField;
    private JTextField fifthSentenceTextField;


    public GUI() {
        rawTextPanel.setVisible(false);
        textFilesPanel.setVisible(false);

        rawTextRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                // if Raw Text Button has been selected show RawTextPanel
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    rawTextPanel.setVisible(true);
                    textFilesPanel.setVisible(false);
                    txtFilesRadioButton.setSelected(false);
                } else {
                    rawTextPanel.setVisible(false);
                }
            }
        });
        txtFilesRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    rawTextPanel.setVisible(false);
                    textFilesPanel.setVisible(true);
                    rawTextRadioButton.setSelected(false);
                }
                else {
                    textFilesPanel.setVisible(false);
                }
            }
        });

        firstSentenceTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        secondSentenceTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        thirdSentenceTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        fourthSentenceTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        fifthSentenceTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    public static void startGUI() {
        JFrame frame = new JFrame("Argumentation Mining HS2017");
        JScrollPane pane = new JScrollPane(new GUI().mainPanel);
        frame.setContentPane(pane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = new Dimension();
        dim.setSize(1000, 750);
        frame.setSize(dim);
        frame.setVisible(true);
    }
}
