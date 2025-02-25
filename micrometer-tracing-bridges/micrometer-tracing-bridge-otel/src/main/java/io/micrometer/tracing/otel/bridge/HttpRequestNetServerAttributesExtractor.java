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

package io.micrometer.tracing.otel.bridge;

import io.micrometer.core.instrument.transport.http.HttpRequest;
import io.micrometer.core.lang.Nullable;
import io.opentelemetry.instrumentation.api.instrumenter.net.NetServerAttributesGetter;

/**
 * Extracts OpenTelemetry network semantic attributes value for server http spans.
 *
 * @author Nikita Salnikov-Tarnovski
 */
class HttpRequestNetServerAttributesExtractor implements NetServerAttributesGetter<HttpRequest> {

    @Nullable
    @Override
    public String transport(HttpRequest httpRequest) {
        return null;
    }

    @Nullable
    @Override
    public String peerName(HttpRequest httpRequest) {
        return null;
    }

    @Override
    public Integer peerPort(HttpRequest httpRequest) {
        return httpRequest.remotePort();
    }

    @Nullable
    @Override
    public String peerIp(HttpRequest httpRequest) {
        return httpRequest.remoteIp();
    }

}
