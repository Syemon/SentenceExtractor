package com.syemon;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.stream.Collectors;

class AppTest {

    @BeforeAll
    static void buildJar() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("mvn", "package", "-DskipTests");
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IllegalStateException("Maven build failed");
        }
    }


    @Test
    void compile() {
    }

    @Test
    void testJarExecution() throws Exception {
        //given
        String jarPath = Paths.get("target", "SentenceExtractor-1.0-SNAPSHOT.jar").toString();
        String inputFile = Paths.get("src", "test", "resources", "small.in").toString();

        String expectedXml = """
            <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            <text>
            <sentence><word>he</word><word>shocking</word><word>shouted</word><word>was</word><word>What</word><word>你这肮脏的掠夺者</word><word>停在那儿</word></sentence>
            <sentence><word>a</word><word>because</word><word>Chinese</word><word>couldn&apos;t</word><word>I</word><word>isn&apos;t</word><word>mother</word><word>my</word><word>perhaps</word><word>tongue</word><word>understand</word><word>word</word></sentence>
            <sentence><word>around</word><word>furious</word><word>he</word><word>I</word><word>just</word><word>marching</word><word>Mr.</word><word>standing</word><word>there</word><word>was</word><word>was</word><word>watching</word><word>Young</word></sentence>
            <sentence><word>anger</word><word>at</word><word>directing</word><word>he</word><word>his</word><word>me</word><word>was</word><word>Why</word></sentence>
            <sentence><word>about</word><word>did</word><word>I</word><word>know</word><word>Little</word><word>that</word></sentence>
            <sentence><word>and</word><word>and</word><word>Baltic</word><word>banking</word><word>capital</word><word>in</word><word>international</word><word>investment</word><word>is</word><word>leading</word><word>markets</word><word>Markets</word><word>Nordea</word><word>Nordic</word><word>operator</word><word>partner</word><word>regions</word><word>Sea</word><word>the</word><word>the</word></sentence>
            <sentence><word>are</word><word>connecting</word><word>door</word><word>global</word><word>located</word><word>markets</word><word>next</word><word>the</word><word>to</word><word>to</word><word>We</word><word>you</word><word>you</word></sentence>
            <sentence><word>a</word><word>and</word><word>combine</word><word>complete</word><word>expertise</word><word>financial</word><word>global</word><word>local</word><word>of</word><word>portfolio</word><word>provide</word><word>services</word><word>solutions</word><word>strength</word><word>to</word><word>We</word><word>with</word><word>with</word><word>you</word></sentence>
            <sentence><word>and</word><word>currencies</word><word>diversified</word><word>have</word><word>in</word><word>in</word><word>liquidity</word><word>local</word><word>most</word><word>Nordics</word><word>of</word><word>offer</word><word>one</word><word>outstanding</word><word>product</word><word>ranges</word><word>strongest</word><word>the</word><word>the</word><word>We</word></sentence>
            <sentence><word>access</word><word>all</word><word>an</word><word>best</word><word>But</word><word>capital</word><word>dedicated</word><word>experts</word><word>facets</word><word>in</word><word>in</word><word>manner</word><word>markets</word><word>more</word><word>of</word><word>of</word><word>offer</word><word>possible</word><word>serving</word><word>significantly</word><word>team</word><word>the</word><word>to</word><word>to</word><word>unequalled</word><word>we</word><word>you</word><word>you</word></sentence>
            <sentence><word>a</word><word>a</word><word>and</word><word>and</word><word>At</word><word>combination</word><word>customer</word><word>expertise</word><word>financial</word><word>for</word><word>gives</word><word>global</word><word>have</word><word>local</word><word>Markets</word><word>Nordea</word><word>of</word><word>of</word><word>opportunity</word><word>our</word><word>rare</word><word>services</word><word>solutions</word><word>strength</word><word>the</word><word>to</word><word>us</word><word>use</word><word>variety</word><word>we</word><word>which</word><word>wide</word><word>you</word></sentence>
            <sentence><word>a</word><word>a</word><word>all</word><word>and</word><word>and</word><word>as</word><word>as</word><word>can</word><word>currencies</word><word>diversified</word><word>excellent</word><word>fact</word><word>finding</word><word>give</word><word>hard</word><word>have</word><word>in</word><word>in</word><word>In</word><word>liquidity</word><word>local</word><word>Nordics</word><word>of</word><word>ours</word><word>product</word><word>range</word><word>strong</word><word>the</word><word>time</word><word>too</word><word>we</word><word>you</word><word>you&apos;d</word></sentence>
            <sentence><word>a</word><word>be</word><word>But</word><word>challenge</word><word>financial</word><word>have</word><word>huge</word><word>importantly</word><word>matter</word><word>might</word><word>most</word><word>no</word><word>of</word><word>outstanding</word><word>ready</word><word>serve</word><word>specialists</word><word>team</word><word>to</word><word>we</word><word>what</word><word>you</word><word>your</word></sentence>
            </text>""";

        ProcessBuilder pb = new ProcessBuilder(
                "java", "-jar", jarPath, "--file", inputFile
        );
        pb.redirectErrorStream(true);

        //when
        Process process = pb.start();

        //then
        String output;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            output = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }

        process.waitFor();

        Assertions.assertThat(output).isEqualTo(expectedXml);
    }
}
