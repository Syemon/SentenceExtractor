package com.syemon.application;

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

    public void extract(final String filePath) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {

            StringBuilder rawSentence = new StringBuilder();
            String line;
            System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
            System.out.println("<text>");
            while ((line = reader.readLine()) != null) {
                List<Sentence> sentences = new ArrayList<>();
                for (char letter : line.toCharArray()) {
                    if (END_OF_SENTENCE_CHARS.contains(letter)) {
                        Optional<Sentence> optionalSentence = Sentence.fromText(rawSentence.toString());
                        if (optionalSentence.isPresent()) {
                            sentences.add(optionalSentence.get());
                        }
                        rawSentence = new StringBuilder();
                    }
                    rawSentence.append(letter);
                }

                sentences.forEach(sentence -> System.out.println(sentence.toXml()));

            }
            System.out.println("</text>");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
