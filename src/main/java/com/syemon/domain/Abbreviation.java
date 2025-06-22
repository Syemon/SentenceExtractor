package com.syemon.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Abbreviation {

    // Titles
    MR("Mr."),
    MRS("Mrs.");

    private static final String DOT_PLACEHOLDER = "___DOT___";
    private static final String DOT = ".";

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

    public static String replacePlaceholder(String text) {
        return text.replace(DOT_PLACEHOLDER, DOT);
    }


}