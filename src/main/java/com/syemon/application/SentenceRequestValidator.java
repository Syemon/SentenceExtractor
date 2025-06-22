package com.syemon.application;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SentenceRequestValidator {

    public void validate(SentenceExtractorRequest request) {
        List<String> errors = new ArrayList<>();
        if (request.outputFormat() == null) {
            errors.add("Parameter %s must be provided".formatted(SentenceExtractor.EXPECTED_FORMAT_COMMAND));
        }
        if (request.filePath() == null) {
            errors.add("Parameter %s must be provided".formatted(SentenceExtractor.FILE_COMMAND));
        }
        try {
            if (request.filePath() != null) {
                Path path = Paths.get(request.filePath());
                if (!Files.exists(path) || !Files.isRegularFile(path)) {
                    errors.add("Parameter %s must be path to actual file".formatted(SentenceExtractor.FILE_COMMAND));
                }
            }
        } catch (InvalidPathException e) {
            errors.add("Parameter %s must be path to actual file".formatted(SentenceExtractor.FILE_COMMAND));

        }

        if (!errors.isEmpty()) {
            throw new ValidationException("SentenceExtractorRequest is not valid", errors);
        }
    }
}
