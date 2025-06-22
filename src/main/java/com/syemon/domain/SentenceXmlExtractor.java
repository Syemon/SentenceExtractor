package com.syemon.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class SentenceXmlExtractor {

    private static final Logger log = LoggerFactory.getLogger(SentenceXmlExtractor.class);

    public static final Set<Character> END_OF_SENTENCE_CHARS = Set.of('.', '!', '?');
    public static final String XML_START_TAG = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
    public static final String XML_TEXT_START_TAG = "<text>";
    public static final String XML_TEXT_END_TAG = "</text>";

    public void extract(String filePath) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            extractXml(reader);
        } catch (IOException e) {
            log.error("Error while processing file", e);
        }
    }

    private static void extractXml(BufferedReader reader) throws IOException {
        ConsoleOutput.outputWithNewLine(XML_START_TAG);
        ConsoleOutput.outputWithNewLine(XML_TEXT_START_TAG);

        StringBuilder rawSentence = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            for (char letter : line.toCharArray()) {
                rawSentence.append(letter);

                if (isEndOfSentence(letter, rawSentence)) {
                    outputSentence(rawSentence);
                    rawSentence = new StringBuilder();
                }
            }

            addSpaceBeforeNextLine(rawSentence);
        }

        processRemainingSentence(rawSentence);

        ConsoleOutput.outputWithoutNewLine(XML_TEXT_END_TAG);
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
            Sentence.fromText(sentenceText)
                    .map(Sentence::toXml)
                    .ifPresent(ConsoleOutput::outputWithNewLine);
        }
    }

    private static void processRemainingSentence(StringBuilder rawSentence) {
        if (!rawSentence.isEmpty()) {
            outputSentence(rawSentence);
        }
    }
}
