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
        Optional<Sentence> sut = Sentence.fromText(text);

        //then
        if (expectedWords.isEmpty()) {
            assertThat(sut).isNotPresent();
        } else {
            assertThat(sut).isPresent();
            Sentence actual = sut.get();

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
                        Collections.emptyList()),
                Arguments.of(
                        "What\the  shouted was shocking:  停在那儿, 你这肮脏的掠夺者!",
                        List.of("he", "shocking", "shouted", "was", "What", "你这肮脏的掠夺者", "停在那儿")),
                Arguments.of(
                        "   Why was he directing  his  anger at me?",
                        List.of("anger", "at", "directing", "he", "his", "me", "was", "Why")),
                Arguments.of(
                        """
                                I was just standing there watching Mr. Young marching around - he\s
                                was    furious.""",
                        List.of("around", "furious", "he", "I", "just", "marching", "Mr.", "standing", "there", "was", "was", "watching", "Young")),
                Arguments.of(
                        "   you’d",
                        List.of("you’d"))
        );
    }

    @Test
    void toXml() {
        //given
        Sentence sut = new Sentence(
                List.of("a", "had", "lamb", "little", "Mary")
        );

        //when/then
        assertThat(sut.toXml())
                .isEqualTo(
                        "<sentence><word>a</word><word>had</word><word>lamb</word><word>little</word><word>Mary</word></sentence>");
    }
}