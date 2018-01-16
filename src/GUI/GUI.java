package GUI;

import Main.Main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

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
    private JSlider testDataPercentageSlider;
    private JPanel testDataPercentagePanel;
    private JLabel testDataPercentageLabel;
    private JButton runClassifierButton;


    public GUI() {
        rawTextPanel.setVisible(false);
        textFilesPanel.setVisible(false);
        testDataPercentagePanel.setVisible(false);

        rawTextRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                // if Raw Text Button has been selected show RawTextPanel
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    rawTextPanel.setVisible(true);
                    textFilesPanel.setVisible(false);
                    txtFilesRadioButton.setSelected(false);
                    testDataPercentagePanel.setVisible(true);
                } else {
                    rawTextPanel.setVisible(false);
                    testDataPercentagePanel.setVisible(false);
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
                    testDataPercentagePanel.setVisible(true);
                } else {
                    textFilesPanel.setVisible(false);
                    testDataPercentagePanel.setVisible(false);
                }
            }
        });

        testDataPercentageSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                testDataPercentageLabel.setText("Test Data Percentage: " + String.valueOf(testDataPercentageSlider.getValue()));
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

        runClassifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int testDataPercentage = testDataPercentageSlider.getValue();
                Main.runProponentOponentClassifier(testDataPercentage);


            }
        });


    }


    public static void showNotification(List<String> notifications) {
        String str = "You got the following notification: \n";
        for (String notif : notifications) {
            str += "\t-\t" + notif + "\n";
        }
        JOptionPane.showMessageDialog(new GUI().mainPanel, str);
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
