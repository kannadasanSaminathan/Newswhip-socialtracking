package com.socialtracking.repl.application.domain;

import java.math.BigDecimal;
import java.net.URL;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public record SocialModel(URL url, BigDecimal score) {
}
