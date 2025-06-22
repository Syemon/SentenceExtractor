package com.syemon.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AbbreviationTest {

    @Test
    void substituteSpecialSignsWithPlaceholders() {
        //given
        String text = "Mr. and Mrs. Example";

        //when/then
        assertThat(Abbreviation.substituteSpecialSignsWithPlaceholders(text))
                .isEqualTo("MrAbbreviationDotPlaceholder and MrsAbbreviationDotPlaceholder Example");
    }

    @Test
    void replacePlaceholder() {
        //given
        String text = "MrAbbreviationDotPlaceholder and MrsAbbreviationDotPlaceholder Example";

        //when/then
        assertThat(Abbreviation.replacePlaceholder(text))
                .isEqualTo("Mr. and Mrs. Example");
    }
}