package com.syemon;

import com.syemon.application.SentenceFacade;
import com.syemon.application.SentenceExtractor;
import com.syemon.application.SentenceRequestValidator;
import com.syemon.domain.SentenceXmlExtractor;

public class App 
{
    public static void main(String[] args) {
        SentenceFacade sentenceFacade = new SentenceFacade(
                new SentenceXmlExtractor(),
                new SentenceRequestValidator()
        );

        SentenceExtractor sentenceExtractor = new SentenceExtractor(
                sentenceFacade
        );

        sentenceExtractor.run(args);
    }
}
