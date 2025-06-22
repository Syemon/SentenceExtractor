package com.syemon.application;

import com.syemon.domain.Abbreviation;
import com.syemon.domain.Sentence;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SentenceFacade {

    public static final Set<Character> END_OF_SENTENCE_CHARS = Set.of('.', '!', '?');
    public static final String XML_START_TAG = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
    public static final String XML_TEXT_START_TAG = "<text>";
    public static final String XML_TEXT_END_TAG = "</text>";

    public void extract(final String filePath) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {

            extractXml(reader);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: move into separate class
    private static void extractXml(BufferedReader reader) throws IOException {
        String line;
        System.out.println(XML_START_TAG);
        System.out.println(XML_TEXT_START_TAG);

        StringBuilder rawSentence = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            List<Sentence> sentences = new ArrayList<>();
            for (char letter : line.toCharArray()) {
                rawSentence.append(letter);
                if (END_OF_SENTENCE_CHARS.contains(letter) && !Abbreviation.endsWithAbbreviation(rawSentence.toString())) {
                    Optional<Sentence> optionalSentence = Sentence.fromText(rawSentence.toString());
                    if (optionalSentence.isPresent()) {
                        sentences.add(optionalSentence.get());
                    }
                    rawSentence = new StringBuilder();
                }
            }

            sentences.forEach(sentence -> System.out.println(sentence.toXml()));

        }
        System.out.println(XML_TEXT_END_TAG);
    }

}
