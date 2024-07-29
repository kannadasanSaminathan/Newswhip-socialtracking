package com.socialtracking.repl.application.domain;


import com.socialtracking.repl.application.domain.service.ScoreOperationService;
import com.socialtracking.repl.application.domain.service.SocialScoreReaderService;
import com.socialtracking.repl.application.domain.util.ScoreUrlValidator;

public class Launcher {

    // Note: We could use text blocks here if >= JDK v15.
    private static final String STARTUP_MESSAGE =
            "+--------------------------------------------------------------------------+\n" +
                    "| -------            URL Storage System Started                    ------- |\n" +
                    "| Commands Available:                                                      |\n" +
                    "| ADD    - Add url for in memory storage for social score                  |\n" +
                    "| REMOVE  - Removed  url for in memory storage and social sore             |\n" +
                    "| EXPORT  - Prints urls and its score                                      |\n" +
                    "|                                                                          |\n" +
                    "+--------------------------------------------------------------------------+\n";

    public static void main(String[] args) {
        System.out.println(STARTUP_MESSAGE);
        ScoreUrlValidator scoreUrlValidator = new ScoreUrlValidator();
        ScoreOperationService scoreOperationService = new ScoreOperationService(scoreUrlValidator);
        SocialScoreReaderService socialScoreReaderService = new SocialScoreReaderService(scoreUrlValidator, scoreOperationService);
        socialScoreReaderService.startRepl();
    }
}