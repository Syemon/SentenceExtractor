package com.syemon.application;

import com.syemon.domain.OutputFormat;
import com.syemon.domain.SentenceXmlExtractor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SentenceFacade {

    private final SentenceXmlExtractor sentenceXmlExtractor;

    public void extract(final String filePath, OutputFormat outputFormat) {
        switch (outputFormat) {
            case XML -> sentenceXmlExtractor.extract(filePath);
            default -> throw new IllegalArgumentException("Unrecognized output format %s".formatted(outputFormat));
        }
    }
}
