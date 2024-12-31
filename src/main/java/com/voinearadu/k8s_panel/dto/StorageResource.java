package com.voinearadu.k8s_panel.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

public record StorageResource(double value, StorageResource.Unit unit) {

    public enum Unit {
        B(1, "B"),

        KB(1000 * B.factor, "KB"),
        MB(1000 * KB.factor, "MB"),
        GB(1000 * MB.factor, "GB"),
        TB(1000 * GB.factor, "T"),

        KiB(1024 * B.factor, "KiB"),
        MiB(1024 * KiB.factor, "MiB"),
        GiB(1024 * MiB.factor, "MiB"),
        TiB(1024 * GiB.factor, "TiB");

        public static final List<List<Unit>> CONVERSION_PATHS = List.of(
                List.of(B, KB, MB, GB, TB),
                List.of(B, KiB, MiB, GiB, TiB)
        );

        private final double factor;
        private final String suffix;

        Unit(double factor, String suffix) {
            this.factor = factor;
            this.suffix = suffix;
        }

        public double convert(double value, Unit targetUnit) {
            return value * factor / targetUnit.factor;
        }
    }

    @Override
    public String toString() {
        StorageResource bestUnit = this.convertToBestUnit();
        return Math.round(bestUnit.value * 100) / 100.0 + bestUnit.unit.suffix;
    }

    public StorageResource convertToBestUnit() {
        for (List<Unit> conversionPath : Unit.CONVERSION_PATHS) {
            if (conversionPath.contains(this.unit)) {
                for (Unit newUnit : conversionPath) {
                    double newValue = this.unit.convert(this.value, newUnit);
                    if (newValue < 1000) {
                        return new StorageResource(newValue, newUnit);
                    }
                }
            }
        }

        return this;
    }
}
