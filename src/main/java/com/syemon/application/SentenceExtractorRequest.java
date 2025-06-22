package com.syemon.application;

import com.syemon.domain.OutputFormat;

public record SentenceExtractorRequest(String filePath, OutputFormat outputFormat) {
}
