package com.syemon;

import com.syemon.application.SentenceFacade;
import com.syemon.application.SentenceExtractor;

public class App 
{
    public static void main(String[] args) {
        SentenceExtractor sentenceExtractor = new SentenceExtractor(
                new SentenceFacade()
        );

        sentenceExtractor.run(args);
    }
}
