package com.syemon;

import com.syemon.application.SentenceFacade;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String filePath = null;
        for (int i = 0; i < args.length; i++) {
            if ("--file".equals(args[i]) && i + 1 < args.length) {
                filePath = args[i + 1];
                break;
            }
        }

        SentenceFacade sentenceFacade = new SentenceFacade();
        sentenceFacade.extract(filePath);
    }
}
