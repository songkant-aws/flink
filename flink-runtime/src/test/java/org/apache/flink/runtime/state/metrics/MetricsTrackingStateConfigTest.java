/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.state.metrics;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.StateLatencyTrackOptions;
import org.apache.flink.configuration.StateSizeTrackOptions;
import org.apache.flink.metrics.groups.UnregisteredMetricsGroup;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** Tests for {@link LatencyTrackingStateConfig}. */
class MetricsTrackingStateConfigTest {

    @Test
    void testDefaultDisabledLatencyTrackingStateConfig() {
        LatencyTrackingStateConfig latencyTrackingStateConfig =
                LatencyTrackingStateConfig.newBuilder().build();
        assertThat(latencyTrackingStateConfig.isEnabled()).isFalse();
    }

    @Test
    void testDefaultEnabledLatencyTrackingStateConfig() {
        UnregisteredMetricsGroup metricsGroup = new UnregisteredMetricsGroup();
        LatencyTrackingStateConfig latencyTrackingStateConfig =
                LatencyTrackingStateConfig.newBuilder()
                        .setEnabled(true)
                        .setMetricGroup(metricsGroup)
                        .build();
        assertThat(latencyTrackingStateConfig.isEnabled()).isTrue();
        assertThat(latencyTrackingStateConfig.getSampleInterval())
                .isEqualTo(
                        (int)
                                StateLatencyTrackOptions.LATENCY_TRACK_SAMPLE_INTERVAL
                                        .defaultValue());
        assertThat(latencyTrackingStateConfig.getHistorySize())
                .isEqualTo(
                        (long) StateLatencyTrackOptions.LATENCY_TRACK_HISTORY_SIZE.defaultValue());
        assertThat(latencyTrackingStateConfig.isStateNameAsVariable())
                .isEqualTo(
                        StateLatencyTrackOptions.LATENCY_TRACK_STATE_NAME_AS_VARIABLE
                                .defaultValue());
    }

    @Test
    void testSetLatencyTrackingStateConfig() {
        UnregisteredMetricsGroup metricsGroup = new UnregisteredMetricsGroup();
        LatencyTrackingStateConfig latencyTrackingStateConfig =
                LatencyTrackingStateConfig.newBuilder()
                        .setMetricGroup(metricsGroup)
                        .setEnabled(true)
                        .setSampleInterval(10)
                        .setHistorySize(500)
                        .build();
        assertThat(latencyTrackingStateConfig.isEnabled()).isTrue();
        assertThat(latencyTrackingStateConfig.getSampleInterval()).isEqualTo(10);
        assertThat(latencyTrackingStateConfig.getHistorySize()).isEqualTo(500);
    }

    @Test
    void testConfigureFromReadableConfig() {
        LatencyTrackingStateConfig.Builder builder = LatencyTrackingStateConfig.newBuilder();
        Configuration configuration = new Configuration();
        configuration.set(StateLatencyTrackOptions.LATENCY_TRACK_ENABLED, true);
        configuration.set(StateLatencyTrackOptions.LATENCY_TRACK_SAMPLE_INTERVAL, 10);
        configuration.set(StateLatencyTrackOptions.LATENCY_TRACK_HISTORY_SIZE, 500);
        LatencyTrackingStateConfig latencyTrackingStateConfig =
                builder.configure(configuration)
                        .setMetricGroup(new UnregisteredMetricsGroup())
                        .build();
        assertThat(latencyTrackingStateConfig.isEnabled()).isTrue();
        assertThat(latencyTrackingStateConfig.getSampleInterval()).isEqualTo(10);
        assertThat(latencyTrackingStateConfig.getHistorySize()).isEqualTo(500);
    }

    @Test
    void testDefaultDisabledSizeTrackingStateConfig() {
        SizeTrackingStateConfig sizeTrackingStateConfig =
                SizeTrackingStateConfig.newBuilder().build();
        assertThat(sizeTrackingStateConfig.isEnabled()).isFalse();
    }

    @Test
    void testDefaultEnabledSizeTrackingStateConfig() {
        UnregisteredMetricsGroup metricsGroup = new UnregisteredMetricsGroup();
        SizeTrackingStateConfig sizeTrackingStateConfig =
                SizeTrackingStateConfig.newBuilder()
                        .setEnabled(true)
                        .setMetricGroup(metricsGroup)
                        .build();
        assertThat(sizeTrackingStateConfig.isEnabled()).isTrue();
        assertThat(sizeTrackingStateConfig.getSampleInterval())
                .isEqualTo((int) StateSizeTrackOptions.SIZE_TRACK_SAMPLE_INTERVAL.defaultValue());
        assertThat(sizeTrackingStateConfig.getHistorySize())
                .isEqualTo((long) StateSizeTrackOptions.SIZE_TRACK_HISTORY_SIZE.defaultValue());
        assertThat(sizeTrackingStateConfig.isStateNameAsVariable())
                .isEqualTo(StateSizeTrackOptions.SIZE_TRACK_STATE_NAME_AS_VARIABLE.defaultValue());
    }

    @Test
    void testSetSizeTrackingStateConfig() {
        UnregisteredMetricsGroup metricsGroup = new UnregisteredMetricsGroup();
        SizeTrackingStateConfig sizeTrackingStateConfig =
                SizeTrackingStateConfig.newBuilder()
                        .setMetricGroup(metricsGroup)
                        .setEnabled(true)
                        .setSampleInterval(10)
                        .setHistorySize(500)
                        .build();
        assertThat(sizeTrackingStateConfig.isEnabled()).isTrue();
        assertThat(sizeTrackingStateConfig.getSampleInterval()).isEqualTo(10);
        assertThat(sizeTrackingStateConfig.getHistorySize()).isEqualTo(500);
    }

    @Test
    void testConfigureFromReadableConfig1() {
        SizeTrackingStateConfig.Builder builder = SizeTrackingStateConfig.newBuilder();
        Configuration configuration = new Configuration();
        configuration.set(StateSizeTrackOptions.SIZE_TRACK_ENABLED, true);
        configuration.set(StateSizeTrackOptions.SIZE_TRACK_SAMPLE_INTERVAL, 10);
        configuration.set(StateSizeTrackOptions.SIZE_TRACK_HISTORY_SIZE, 500);
        SizeTrackingStateConfig sizeTrackingStateConfig =
                builder.configure(configuration)
                        .setMetricGroup(new UnregisteredMetricsGroup())
                        .build();
        assertThat(sizeTrackingStateConfig.isEnabled()).isTrue();
        assertThat(sizeTrackingStateConfig.getSampleInterval()).isEqualTo(10);
        assertThat(sizeTrackingStateConfig.getHistorySize()).isEqualTo(500);
    }
}
