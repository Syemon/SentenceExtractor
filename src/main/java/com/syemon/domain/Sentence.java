package com.syemon.domain;

import lombok.Value;
import org.apache.commons.text.StringEscapeUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Value
public class Sentence {
    public static final String NEW_LINE = "\n";
    public static final String XML_SENTENCE_START_TAG = "<sentence>";
    public static final String XML_SENTENCE_END_TAG = "</sentence>";
    public static final String XML_WORD_START_TAG = "<word>";
    public static final String XML_WORD_END_TAG = "</word>";

    List<String> words;

    public static Optional<Sentence> fromText(String text) {
        String sanitizedText = sanitizeRawText(text);

        if (sanitizedText.isBlank()) {
            return Optional.empty();
        }

        List<String> words = Stream.of(sanitizedText.split("\\s+"))
                .sorted(String.CASE_INSENSITIVE_ORDER.thenComparing(Comparator.reverseOrder()))                .toList();

        return Optional.of(
                new Sentence(words)
        );
    }

    public String toXml() {
        StringBuilder xml = new StringBuilder();
        xml.append(XML_SENTENCE_START_TAG);
        words.stream().map(Sentence::escapeSpecialCharacters)
                .forEach(word -> xml
                .append(XML_WORD_START_TAG).append(word).append(XML_WORD_END_TAG)
        );
        xml.append(XML_SENTENCE_END_TAG);
        return xml.toString();
    }

    private static String sanitizeRawText(String text) {
        text = Abbreviation.substituteSpecialSignsWithPlaceholders(text);

        text = text.replaceAll("(?<!\\p{L})['''’`-](?!\\p{L})", " ");

        text = text.replaceAll("[^\\p{L}'''’`\\-_ \\t]", " ").trim();

        text = Abbreviation.replacePlaceholder(text);

        return text;
    }

    private static String escapeSpecialCharacters(String word) {
        String result = StringEscapeUtils.escapeXml11(word);
        return result.replace("’", "&apos;");
    }
}