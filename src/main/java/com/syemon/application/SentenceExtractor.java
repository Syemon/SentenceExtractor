package com.syemon.application;

import com.syemon.domain.OutputFormat;

public class SentenceExtractor {

    public static final String FILE_COMMAND = "--file";
    public static final String EXPECTED_FORMAT_COMMAND = "--format";
    private final SentenceFacade sentenceFacade;

    public SentenceExtractor(SentenceFacade sentenceFacade) {
        this.sentenceFacade = sentenceFacade;
    }

    public void run(String[] args) {

        String filePath = null;
        for (int i = 0; i < args.length; i++) {
            if (FILE_COMMAND.equals(args[i]) && i + 1 < args.length) {
                filePath = args[i + 1];
                break;
            }
        }

        //There is not CSV implementation
        OutputFormat format = OutputFormat.XML;
        sentenceFacade.extract(filePath, format);
    }
}
