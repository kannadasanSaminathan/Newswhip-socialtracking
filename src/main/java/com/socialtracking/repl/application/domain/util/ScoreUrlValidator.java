package com.socialtracking.repl.application.domain.util;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.socialtracking.repl.application.domain.OperationType;
import org.apache.commons.validator.UrlValidator;

public class ScoreUrlValidator {

    private static final String ADD_ARGS_LENGTH_ERROR_MESSAGE = "Invalid input - The 'ADD' operation requires 2 subsequent arguments.";
    private static final String REMOVE_ARGS_LENGTH_ERROR_MESSAGE = "Invalid input - The 'REMOVE' operation requires 1 subsequent argument.";

    private final UrlValidator urlValidator = new UrlValidator();

    private static final Set<String> operationTypes = EnumSet.allOf(OperationType.class).stream()
            .map(OperationType::name)
            .collect(Collectors.toSet());

    public boolean isInvalidOperationCommand(String commandInputItem) {
        return !operationTypes.contains(commandInputItem);
    }

    public boolean isValidUrl(String url) {
        return urlValidator.isValid(url);
    }

    public void validateOperationTypeArguments(int listSize, OperationType operationType) {
        if (operationType == OperationType.ADD) {
            if (listSize != 3) {
                throw new IllegalStateException(ADD_ARGS_LENGTH_ERROR_MESSAGE);
            }
        } else if (operationType == OperationType.REMOVE) {
            if (listSize != 2) {
                throw new IllegalStateException(REMOVE_ARGS_LENGTH_ERROR_MESSAGE);
            }
        }
    }
}