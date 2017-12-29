package InputOutput;

import ConfigurationManager.ConfigurationManager;
import Main.Model.role.UndefinedSentence;
import Main.TextSegment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import StandfordParserManager.NLPManager;
import edu.stanford.nlp.simple.*;

/**
 * Created by LucasPelloni on 02.12.17.
 */
public abstract class FileReader {

    public static List<TextSegment> readFile(final String FILE_PATH) {
        List<TextSegment> textSegments = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            //read file into stream, try-with-resources and convert it
            try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
                stream.forEach(text -> {
                    List<Sentence> sentences = NLPManager.splitSentence(text);
                    int sentenceCounter = 0;
                    for (Sentence sentence : sentences) {
                        sentenceCounter++;
                        TextSegment textSegment = createSentence(file, sentence, sentenceCounter);
                        textSegments.add(textSegment);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Please select a file valid");
        }
        return textSegments;
    }

    public static Map<String, List<TextSegment>> walkDatasetDirectory(final String DATASET_PATH) {
        Map<String, List<TextSegment>> corpusMap = new HashMap<>();
        File file = new File(DATASET_PATH);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.exists() && isValidCorpus(f.getName())) {
                        String fileId = f.getName().split("_")[1].split("\\.")[0];
                        corpusMap.put(fileId, readFile(f.getAbsolutePath()));
                    }
                }
            }
        }
        return corpusMap;
    }

    private static boolean isValidCorpus(String fileName) {
        return (fileName.contains(".txt") && fileName.contains("micro_"));
    }

    private static TextSegment createSentence(File file, Sentence sentence, int sentenceCounter) {
        String[] temp = file.getName().split("/");
        String fileId = temp[temp.length - 1].split("_")[1].split("\\.")[0]; // e.g. b001
        String sentenceId = fileId + "_" + String.valueOf(sentenceCounter);

        // TODO: integrate Standford parser and add a list of preprocessed Words
        return new UndefinedSentence(fileId, sentenceId, ConfigurationManager.SENTENCES_LANGUAGE, file, sentence);

    }
}
