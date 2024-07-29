package com.socialtracking.repl.application.domain.service;


import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.socialtracking.repl.application.domain.OperationType;
import com.socialtracking.repl.application.domain.util.ScoreUrlValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SocialScoreReaderService {

    private static final String SINGLE_WHITESPACE = " ";
    private static final String INVALID_COMMAND_ERROR_MESSAGE = "Invalid command entered - Please use [ADD|REMOVE|EXPORT].";

    private final ScoreUrlValidator scoreUrlValidator;
    private final ScoreOperationService scoreOperationService;

    public void startRepl() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String[] input = scanner.nextLine().split(SINGLE_WHITESPACE);
            List<String> inputList = Arrays.asList(input);
            try {
                String commandInputItem = inputList.get(0);
                if (scoreUrlValidator.isInvalidOperationCommand(commandInputItem)) {
                    throw new IllegalStateException(INVALID_COMMAND_ERROR_MESSAGE);
                }
                OperationType operationType = OperationType.valueOf(commandInputItem);
                scoreUrlValidator.validateOperationTypeArguments(inputList.size(), operationType);
                performOperation(inputList, operationType);
            } catch (IllegalStateException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void performOperation(List<String> inputList, OperationType operationType) {
        // Note: We could use enhanced switch statement here if >= JDK v11
        switch (operationType) {
            case ADD:
                scoreOperationService.addUrlWithSocialScore(inputList.get(1), inputList.get(2));
                break;
            case REMOVE:
                scoreOperationService.findUrlInStoreAndRemove(inputList.get(1));
                break;
            case EXPORT:
                scoreOperationService.exportUrlSocialScores();
                break;
            default:
                throw new IllegalStateException("Operation Type has not been implemented");
        }
    }
}