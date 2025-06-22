package com.syemon.application;

import com.syemon.domain.OutputFormat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SentenceRequestValidatorTest {

    private final SentenceRequestValidator sut = new SentenceRequestValidator();

    @ParameterizedTest
    @MethodSource("validateProvider")
    void validate(String filePath, OutputFormat outputFormat, List<String> expectedErrors) {
        //given
        SentenceExtractorRequest request = new SentenceExtractorRequest(filePath, outputFormat);


        //when/then
        if (expectedErrors.isEmpty()) {
            assertThatCode(() -> sut.validate(request))
                    .doesNotThrowAnyException();
        } else {
            assertThatExceptionOfType(ValidationException.class)
                    .isThrownBy(() -> sut.validate(request))
                    .withMessage("SentenceExtractorRequest is not valid")
                    .hasFieldOrPropertyWithValue("errors", expectedErrors);
        }
    }

    public static Stream<Arguments> validateProvider() {
        return Stream.of(
                Arguments.of(
                        null, null,
                        List.of("Parameter --format must be provided", "Parameter --file must be provided")),
                Arguments.of(
                        null, OutputFormat.XML,
                        List.of("Parameter --file must be provided")),
                Arguments.of(
                        "invalidPath", OutputFormat.XML,
                        List.of("Parameter --file must be path to actual file")),
                Arguments.of(
                        "src/test/resources/small.in", OutputFormat.XML,
                        Collections.emptyList())
        );
    }
}