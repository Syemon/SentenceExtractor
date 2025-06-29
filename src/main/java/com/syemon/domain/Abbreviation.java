package com.syemon.domain;

public enum Abbreviation {

    MR("Mr.", "Mr"),
    MRS("Mrs.", "Mrs");

    private static final String DOT_PLACEHOLDER = "AbbreviationDotPlaceholder";
    private static final String DOT = ".";

    private final String value;
    private final String valueWithoutSpecialSign;

    Abbreviation(String value, String valueWithoutSpecialSign) {
        this.value = value;
        this.valueWithoutSpecialSign = valueWithoutSpecialSign;
    }

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

    public static String substituteSpecialSignsWithPlaceholders(String text) {
        for (Abbreviation abbreviation : values()) {
            text = text.replace(abbreviation.value, abbreviation.valueWithoutSpecialSign + DOT_PLACEHOLDER);
        }

        return text;
    }
}