package com.voinearadu.k8s_panel.manager;


import com.voinearadu.k8s_panel.dto.NodeMetadata;
import com.voinearadu.k8s_panel.dto.StorageResource;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.models.V1Node;
import io.kubernetes.client.openapi.models.V1NodeAddress;
import io.kubernetes.client.openapi.models.V1NodeStatus;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class KubernetesManager {

    public @NotNull NodeMetadata createNodeMetadata(@NotNull V1Node node) {
        NodeMetadata.NodeMetadataBuilder builder = NodeMetadata.builder();

        V1ObjectMeta metadata = node.getMetadata();
        V1NodeStatus status = node.getStatus();

        if (metadata != null) {
            builder.name(metadata.getName());

            if (metadata.getUid() != null) {
                builder.uuid(UUID.fromString(metadata.getUid()));
            }
        }

        if (status != null) {
            List<V1NodeAddress> addresses = status.getAddresses();
            Map<String, Quantity> capacity = status.getCapacity();

            if (addresses != null) {
                builder.addresses(addresses.stream().map(V1NodeAddress::getAddress).toList());
            }

            if (capacity != null) {
//                capacity.forEach((key, value) -> System.out.println(key + " " + value));
//                System.out.println();
//                System.out.println();

                builder.capacityCPU(capacity.get("cpu").getNumber().intValue() * 100);
                builder.capacityMemory(new StorageResource(capacity.get("memory").getNumber().longValue(), StorageResource.Unit.B));
                builder.capacityStorage(new StorageResource(capacity.get("ephemeral-storage").getNumber().longValue(), StorageResource.Unit.B));
            }
        }

        return builder.build();
    }

}
