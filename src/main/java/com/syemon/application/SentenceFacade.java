package com.syemon.application;

import com.syemon.domain.SentenceXmlExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SentenceFacade {

    private static final Logger log = LoggerFactory.getLogger(SentenceFacade.class);

    private final SentenceXmlExtractor sentenceXmlExtractor;
    private final SentenceRequestValidator sentenceRequestValidator;

    public SentenceFacade(SentenceXmlExtractor sentenceXmlExtractor, SentenceRequestValidator sentenceRequestValidator) {
        this.sentenceXmlExtractor = sentenceXmlExtractor;
        this.sentenceRequestValidator = sentenceRequestValidator;
    }

    public void extract(SentenceExtractorRequest request) {
        try {
            sentenceRequestValidator.validate(request);

            switch (request.outputFormat()) {
                case XML -> sentenceXmlExtractor.extract(request.filePath());
                default -> throw new IllegalArgumentException("Unrecognized output format %s".formatted(request.outputFormat()));
            }

        } catch (ValidationException e) {
            log.error("Request is not valid. Received errors: {}", e.getErrors());
        } catch (Exception e) {
            log.error("Unexpected error has occurred: {}", e.getMessage(), e);
        }
    }
}
