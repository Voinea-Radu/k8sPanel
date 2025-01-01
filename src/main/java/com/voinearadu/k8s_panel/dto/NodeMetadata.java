package com.voinearadu.k8s_panel.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class NodeMetadata {

    private String name;
    private UUID uuid;
    @Builder.Default
    private Status status = Status.NOT_READY;

    @Builder.Default
    private String address = "N/A";

    @Builder.Default
    private int capacityCPU = -1;
    @Builder.Default
    private StorageResource capacityMemory = new StorageResource(0, StorageResource.Unit.B);
    @Builder.Default
    private StorageResource capacityStorage = new StorageResource(0, StorageResource.Unit.B);

    @Builder.Default
    private int allocatedCPU = -1;
    @Builder.Default
    private StorageResource allocatedMemory = new StorageResource(0, StorageResource.Unit.B);
    @Builder.Default
    private StorageResource allocatedStorage = new StorageResource(0, StorageResource.Unit.B);

    public enum Status {
        READY("Ready"),
        NOT_READY("Not Ready");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

}
