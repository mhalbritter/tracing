/*
 * Copyright 2013-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.micrometer.tracing.test.reporter.wavefront;

import java.util.concurrent.TimeUnit;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.micrometer.core.instrument.observation.Observation;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.core.util.internal.logging.InternalLogger;
import io.micrometer.core.util.internal.logging.InternalLoggerFactory;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.anyRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

@WireMockTest
class WavefrontOtelSetupTests {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(WavefrontOtelSetupTests.class);

    SimpleMeterRegistry simpleMeterRegistry = new SimpleMeterRegistry();

    @Test
    void should_register_a_span_in_wavefront(WireMockRuntimeInfo wmri) throws InterruptedException {
        WavefrontOtelSetup setup = WavefrontOtelSetup.builder(wmri.getHttpBaseUrl(), "token")
                .applicationName("app-name")
                .serviceName("service-name")
                .source("source")
                .register(this.simpleMeterRegistry);

        WavefrontOtelSetup.run(setup, __ -> {
            Observation sample = Observation.start("the-name", simpleMeterRegistry);
            try (Observation.Scope scope = sample.openScope()) {
                log.info("New observation created");
            }
            sample.stop();
        });

        Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(() ->
                wmri.getWireMock().verifyThat(anyRequestedFor(urlMatching(".*/report\\?f=trace.*")))
        );
    }
}
