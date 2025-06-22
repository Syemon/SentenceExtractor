package com.syemon.application;

public class SentenceExtractor {

    private final SentenceFacade sentenceFacade;

    public SentenceExtractor(SentenceFacade sentenceFacade) {
        this.sentenceFacade = sentenceFacade;
    }

    public void run(String[] args) {

        String filePath = null;
        for (int i = 0; i < args.length; i++) {
            if ("--file".equals(args[i]) && i + 1 < args.length) {
                filePath = args[i + 1];
                break;
            }
        }
        sentenceFacade.extract(filePath);
    }
}
