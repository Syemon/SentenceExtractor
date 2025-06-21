package com.syemon.domain;

import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Value
public class Sentence {
    List<String> words;

    public static Optional<Sentence> fromText(String text) {
        String sanitizedText = sanitizeRawText(text);

        if (sanitizedText.isBlank()) {
            return Optional.empty();
        }

        List<String> words = Stream.of(sanitizedText.split("\\s+"))
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();

        return Optional.of(
                new Sentence(words)
        );
    }

    private static String sanitizeRawText(String text) {
        text = text.trim();
        return text.replaceAll("[^a-zA-Z ]", "");
    }
}
