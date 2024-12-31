package com.voinearadu.k8s_panel.service;

import com.voinearadu.k8s_panel.manager.KubernetesManager;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class KubernetesService {

    @SneakyThrows
    @Bean
    public ApiClient kubernetesAPIClient() {
        return Config.defaultClient();
    }

    @SneakyThrows
    @Bean
    public CoreV1Api kubernetesAPI(ApiClient apiClient) {
        return new CoreV1Api(apiClient);
    }

    @Bean
    public KubernetesManager kubernetesManager() {
        return new KubernetesManager();
    }

}
