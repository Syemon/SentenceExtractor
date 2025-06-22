package com.syemon.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Abbreviation {

    // Titles
    MR("Mr."),
    MRS("Mrs.");

    private static final String PLACEHOLDER = "<DOT>";

    private final String value;

    public static boolean endsWithAbbreviation(String text) {
        String upperCaseText = text.toUpperCase();
        for (Abbreviation abbreviation : values()) {
            if (upperCaseText.endsWith(abbreviation.value.toUpperCase())) {
                return true;
            }
        }
        return false;
    }


}