package Reader;

import Main.Corpus;
import Main.Language;
import Main.Sentence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by LucasPelloni on 02.12.17.
 */
public abstract class FileReader {

    public static List<Sentence> readFile(final String FILE_PATH) {
        List<Sentence> sentences = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            //read file into stream, try-with-resources and convert it
            try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {

                stream.forEach(text -> {
                    String[] sentencesText = text.split("\\.");
                    int sentenceCounter = 0;
                    for (String sentenceText : sentencesText) {
                        sentenceCounter++;
                        Sentence sentence = createSentence(file, sentenceText, sentenceCounter);
                        sentences.add(sentence);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Please select a file valid");
        }
        return sentences;
    }

    public static Map<String, List<Sentence>> walkDatasetDirectory(final String DATASET_PATH) {
        Map<String, List<Sentence>> corpusMap = new HashMap<>();
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

    private static Sentence createSentence(File file, String sentenceText, int sentenceCounter) {
        String[] temp = file.getName().split("/");
        String fileId = temp[temp.length - 1].split("_")[1].split("\\.")[0]; // e.g. b001
        String sentenceId = fileId + "_" + String.valueOf(sentenceCounter);

        // TODO: integrate Standford parser and add a list of preprocessed Words
        return new Sentence(fileId, sentenceId, Language.ENGLISH, file, sentenceText, new ArrayList<>());
    }
}
