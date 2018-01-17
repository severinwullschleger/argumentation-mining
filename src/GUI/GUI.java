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
import java.io.File;
import InputOutput.FileReader;
import java.util.ArrayList;
import java.util.List;
import Main.TextSegment;

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
    private JButton selectTxtFileDir;
    private JLabel selectedTxtFileLabel;
    private JPanel radioButtonsPanel;
    private List<String> stringSentences;
    private List<JTextField> jTextFields;
    private File selectedFile;


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
                    setSubPanelVisible();
                } else {
                    rawTextPanel.setVisible(false);
                    setSubPanelsInvisible();
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
                    setSubPanelVisible();
                } else {
                    textFilesPanel.setVisible(false);
                    setSubPanelsInvisible();
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
                Main.runClassifier(testDataPercentage);


            }
        });


        useClassifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rawTextRadioButton.isSelected()) {
                    if (sentencesAreWellFormed()) {
                        List<String> sentences = new ArrayList<>();
                        for (JTextField jTextField : jTextFields) {
                            if (!jTextField.getText().equals("")) {
                                String sentence = jTextField.getText();
                                sentences.add(sentence);
                            }
                        }
                        if (!sentences.isEmpty())
                            Main.useClassifier(sentences);
                    }

                }
                else if (txtFilesRadioButton.isSelected()) {
                    if (selectedFile == null){
                        List<String> errors = new ArrayList<>();
                        errors.add("Please selected a valid file before using the Classifier!");
                        showNotification(errors);
                    }
                    else {
                        List<String> stringSentences = FileReader.readFileAsStrings(selectedFile.getPath());
                        if (!stringSentences.isEmpty()) {
                            Main.useClassifier(stringSentences);
                        }

                    }
                }

            }
        });
        selectTxtFileDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedFile = openFileChooser("Choose a directory containing your APK(s)", false);
                if (selectedFile != null) {
                    selectedTxtFileLabel.setText("Selected file: " + selectedFile.getName());
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

    private static File openFileChooser(String dialogTitle, boolean onlyDirectories) {
        JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
        if (onlyDirectories) {
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }

        jfc.setDialogTitle(dialogTitle);
        int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile();
        }
        return null;
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

    private void setSubPanelVisible() {
        testDataPercentagePanel.setVisible(true);
        runClassifierPanel.setVisible(true);
        useClassifierPanel.setVisible(true);
    }

    private void setSubPanelsInvisible() {
        testDataPercentagePanel.setVisible(false);
        runClassifierPanel.setVisible(false);
        useClassifierPanel.setVisible(false);
    }

    private boolean sentencesAreWellFormed() {
        // TODO: check the formatting of the sentences
        return true;
    }

    private void initGUIElements() {
        rawTextPanel.setVisible(false);
        textFilesPanel.setVisible(false);
        setSubPanelsInvisible();
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
