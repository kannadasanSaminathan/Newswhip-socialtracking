package com.socialtracking.repl.application.domain.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.socialtracking.repl.application.domain.util.ScoreUrlValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ScoreOperationServiceTest {

    private ScoreOperationService scoreOperationService;

    @Before
    public void setUp() {
        ScoreUrlValidator scoreUrlValidator = new ScoreUrlValidator();
        this.scoreOperationService = new ScoreOperationService(scoreUrlValidator);
    }

    @Test
    public void addUrlWithSocialScore_shouldAddSocialScore() {
        String url = "https://www.test.com/junit";
        String score = "50.40";

        scoreOperationService.addUrlWithSocialScore(url, score);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        scoreOperationService.exportUrlSocialScores();

        String expectedOutput = "domain;urls;social_score\r\n" +
                "test.com;1;50.40\r\n";

        Assert.assertNotNull(outputStream.toString());
        Assert.assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void addUrlWithSocialScore_shouldThrowException_nonNumericScorePassed() {
        String url = "https://www.test.com/junit";
        String score = "NON_NUMERIC_VALUE";
        IllegalStateException e = Assertions.assertThrows(IllegalStateException.class,
                () -> scoreOperationService.addUrlWithSocialScore(url, score)
        );
        Assert.assertEquals("The value passed is not a valid numeric type [integer/decimal].", e.getMessage());
    }

    @Test
    public void removeUrlWithSocialScore_shouldRemoveUrlFromDomainGroup() {
        String url = "https://www.test.com/junit";
        String score = "50.40";

        scoreOperationService.addUrlWithSocialScore(url, score);
        scoreOperationService.findUrlInStoreAndRemove(url);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        scoreOperationService.exportUrlSocialScores();

        String expectedOutput = "domain;urls;social_score\r\n";

        Assert.assertNotNull(outputStream.toString());
        Assert.assertEquals(expectedOutput, outputStream.toString());
    }

}
