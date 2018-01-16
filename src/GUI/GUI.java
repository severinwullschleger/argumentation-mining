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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuckyP on 16.01.18.
 */
public class GUI {
    private static GUI gui;
    public JLabel correctInstancesLabel;
    public JLabel incorrectInstancesLabel;
    public JLabel runClassifierInfoLabel;
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
    private JPanel runClassifierPanel;
    private JPanel useClassifierPanel;
    private JButton useClassifierButton;
    private List<String> stringSentences;
    private List<JTextField> jTextFields;


    public GUI() {
        initGUIElements();

        rawTextRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                // if Raw Text Button has been selected show RawTextPanel
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    rawTextPanel.setVisible(true);
                    textFilesPanel.setVisible(false);
                    txtFilesRadioButton.setSelected(false);
                    testDataPercentagePanel.setVisible(true);
                    runClassifierPanel.setVisible(true);
                } else {
                    rawTextPanel.setVisible(false);
                    testDataPercentagePanel.setVisible(false);
                    runClassifierPanel.setVisible(false);
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
                    runClassifierPanel.setVisible(true);
                } else {
                    textFilesPanel.setVisible(false);
                    testDataPercentagePanel.setVisible(false);
                    runClassifierPanel.setVisible(false);
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
                List<String> notif = new ArrayList<>();
                notif.add("Classifier has been started...");
                showNotification(notif);
                Main.runProponentOponentClassifier(testDataPercentage);


            }
        });


        useClassifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rawTextRadioButton.isSelected()) {
                    if (sentencesAreWellFormed()) {
                        List<String> sentences = new ArrayList<>();
                        for (JTextField jTextField: jTextFields) {
                            if (!jTextField.getText().equals("")) {
                                sentences.add(jTextField.getText());
                            }
                        }

                        Main.useClassifier(sentences);
                    }

                }

            }
        });
    }

    public static void showNotification(List<String> notifications) {
        String str = "";
        for (String notif : notifications) {
            str += notif + "\n";
        }
        JOptionPane.showMessageDialog(GUI.getGUI().mainPanel, str);
    }

    public static void startGUI() {
        gui = new GUI();
        JFrame frame = new JFrame("Argumentation Mining HS2017");
        JScrollPane pane = new JScrollPane(gui.mainPanel);
        frame.setContentPane(pane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = new Dimension();
        dim.setSize(1000, 750);
        frame.setSize(dim);
        frame.setVisible(true);
    }

    public static GUI getGUI() {
        return gui;
    }

    private boolean sentencesAreWellFormed() {
        // TODO: check the formatting of the sentences
        return true;
    }

    private void initGUIElements() {
        rawTextPanel.setVisible(false);
        textFilesPanel.setVisible(false);
        testDataPercentagePanel.setVisible(false);
        runClassifierPanel.setVisible(false);
        testDataPercentageSlider.setValue(10);
        testDataPercentageLabel.setText("Test Data Percentage: " + String.valueOf(testDataPercentageSlider.getValue()));
        correctInstancesLabel.setText("");
        incorrectInstancesLabel.setText("");
        this.stringSentences = new ArrayList<>();
        this.jTextFields = new ArrayList<>();


        firstSentenceTextField.setText("");
        secondSentenceTextField.setText("");
        thirdSentenceTextField.setText("");
        fourthSentenceTextField.setText("");
        fifthSentenceTextField.setText("");
        jTextFields.add(firstSentenceTextField);
        jTextFields.add(secondSentenceTextField);
        jTextFields.add(thirdSentenceTextField);
        jTextFields.add(fourthSentenceTextField);
        jTextFields.add(fifthSentenceTextField);
    }


}
