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
        System.out.println(XML_START_TAG);
        System.out.println(XML_TEXT_START_TAG);

        StringBuilder rawSentence = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            processLine(line, rawSentence);
        }

        processRemainingSentence(rawSentence);

        System.out.println(XML_TEXT_END_TAG);
    }

    private static void processLine(String line, StringBuilder rawSentence) {
        for (char letter : line.toCharArray()) {
            rawSentence.append(letter);

            if (isEndOfSentence(letter, rawSentence)) {
                outputSentence(rawSentence);
                rawSentence.setLength(0);
            }
        }

        addSpaceBeforeNextLine(rawSentence);
    }

    private static void addSpaceBeforeNextLine(StringBuilder rawSentence) {
        if (!rawSentence.isEmpty()) {
            rawSentence.append(' ');
        }
    }

    private static boolean isEndOfSentence(char letter, StringBuilder rawSentence) {
        return END_OF_SENTENCE_CHARS.contains(letter) &&
                !Abbreviation.endsWithAbbreviation(rawSentence.toString());
    }

    private static void outputSentence(StringBuilder rawSentence) {
        String sentenceText = rawSentence.toString().trim();
        if (!sentenceText.isEmpty()) {
            Optional<Sentence> optionalSentence = Sentence.fromText(sentenceText);
            optionalSentence.ifPresent(sentence -> System.out.println(sentence.toXml()));
        }
    }

    private static void processRemainingSentence(StringBuilder rawSentence) {
        if (!rawSentence.isEmpty()) {
            outputSentence(rawSentence);
        }
    }

}
