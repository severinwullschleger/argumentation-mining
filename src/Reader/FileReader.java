package Reader;

import Main.Sentence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    private static Sentence createSentence(File file, String sentenceText, int sentenceCounter) {
        String[] temp = file.getName().split("/");
        String fileId = temp[temp.length - 1].split("_")[1].split("\\.")[0]; // e.g. b001
        String sentenceId = fileId + "_" + String.valueOf(sentenceCounter);

        // TODO: integrate Standford parser and add a list of preprocessed Words

        return new Sentence(fileId, sentenceId, true, file, sentenceText, new ArrayList<>());
    }
}
