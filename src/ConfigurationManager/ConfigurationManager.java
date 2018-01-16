package ConfigurationManager;

import Main.Enums.Language;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.UUID;

/**
 * This class represents the connection between the project and the <config.properties> file
 *
 * @author Lucas Pelloni
 * @version 1.0
 * @since 02.12.2017
 */
public class ConfigurationManager {

    public static Language SENTENCES_LANGUAGE;
    private static final String STATIC_CONTENT = "---------STATIC+CONTENT";
    private static ConfigurationManager instance;
    private static String filename;
    private static String content;

    static {
        setFilename("config.properties");
    }

    private String myFilename;
    private Properties properties;

    private ConfigurationManager(String filename) throws IOException {
        this.properties = new Properties();
        this.properties.load(new FileInputStream(filename));
        this.myFilename = filename;
    }

    private ConfigurationManager() {
        this.myFilename = "";
    }

    /**
     * Sets the filename of the configuration file. To be called before <code>getInstance</code>.
     *
     * @return
     */
    public static void setFilename(String pFilename) {
        filename = pFilename;
        content = null;
    }

    public static void setDirectContent(String pContent) {
        content = pContent;
        filename = STATIC_CONTENT + UUID.randomUUID();
    }

    /**
     * Returns an instance of the configuration manager. Call <code>setFilename</code> before this method.
     *
     * @return the instance of <ConfigurationManager>
     */
    public static ConfigurationManager getInstance() {
//        instance = new ConfigurationManager();
        try {
            if (instance == null || !instance.myFilename.equals(filename)) {
                if (!filename.startsWith(STATIC_CONTENT))
                    instance = new ConfigurationManager(filename);
                else {
                    instance = new ConfigurationManager();
                    instance.directLoadContent(filename, content);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            System.err.println("A problem occurred with the config Manager");
        }
        return instance;
    }

    public Method[] getMethods() {
        return this.getClass().getMethods();
    }

    private void directLoadContent(String pFilename, String pContent) throws IOException {
        this.properties = new Properties();
        this.properties.load(new StringReader(pContent));
        this.myFilename = pFilename;
    }

    public String getFilePath() {
        final String DATASET_PATH = this.properties.getProperty("files");
        if (DATASET_PATH.contains("de")) {
            SENTENCES_LANGUAGE = Language.DEUTSCH;
        }
        else {
            SENTENCES_LANGUAGE = Language.ENGLISH;
        }
        return DATASET_PATH ;
    }

    public String getOutputXMLDir() {
        return this.properties.getProperty("output_xml_dir");
    }
}