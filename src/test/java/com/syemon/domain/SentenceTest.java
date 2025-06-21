package com.syemon.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SentenceTest {

    @ParameterizedTest
    @MethodSource("fromTextProvider")
    void fromText(String text, List<String> expectedWords) {
        //when
        Optional<Sentence> sentence = Sentence.fromText(text);

        //then
        if (expectedWords.isEmpty()) {
            assertThat(sentence).isNotPresent();
        } else {
            assertThat(sentence).isPresent();
            Sentence actual = sentence.get();

            assertThat(actual.getWords()).containsExactlyElementsOf(expectedWords);
        }

    }

    public static Stream<Arguments> fromTextProvider() {
        return Stream.of(
                Arguments.of(
                        "Mary had a little lamb.",
                        List.of("a", "had", "lamb", "little", "Mary")),
                Arguments.of(
                        " Mary had a little lamb .",
                        List.of("a", "had", "lamb", "little", "Mary")),
                Arguments.of(
                        "Peter called for the wolf, and Aesop came.",
                        List.of("Aesop", "and", "called", "came", "for", "Peter", "the", "wolf")),
                Arguments.of(
                        "Peter called for the wolf , and Aesop came .",
                        List.of("Aesop", "and", "called", "came", "for", "Peter", "the", "wolf")),
                Arguments.of(
                        ".",
                        Collections.emptyList()),
                Arguments.of(
                        ",",
                        Collections.emptyList()),
                Arguments.of(
                        "           ",
                        Collections.emptyList())
        );
    }

    @Test
    void toXml() {
        //given
        Sentence sentence = new Sentence(
                List.of("a", "had", "lamb", "little", "Mary")
        );

        //when/then
        assertThat(sentence.toXml())
                .isEqualTo("""
                        <sentence>
                          <word>a</word>
                          <word>had</word>
                          <word>lamb</word>
                          <word>little</word>
                          <word>Mary</word>
                        </sentence>""");
    }
}