package com.voinearadu.k8s_panel.controller;

import com.voinearadu.k8s_panel.manager.KubernetesManager;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    private final CoreV1Api kubernetesAPI;
    private final KubernetesManager kubernetesManager;

    public WebController(CoreV1Api kubernetesAPI, KubernetesManager kubernetesManager) {
        this.kubernetesAPI = kubernetesAPI;
        this.kubernetesManager = kubernetesManager;
    }

    @GetMapping("/")
    public String root() {
        return "server";
    }

    @SneakyThrows
    @GetMapping("/pods")
    public String pods(Model model) {
//        model.addAttribute("pods", kubernetesAPI.listNode().execute().getItems().stream().map(V1Node::getMetadata).filter(Objects::nonNull).map(V1ObjectMeta::getName).toList());

        return "pods";
    }

    @SneakyThrows
    @GetMapping("/nodes")
    public String nodes(Model model) {
        model.addAttribute("nodes",
                kubernetesAPI.listNode().execute().getItems()
                        .stream().map(kubernetesManager::createNodeMetadata)
                        .toList()
        );
        model.addAttribute("active_namespace", "test");
        model.addAttribute("namespaces", List.of("test", "default"));

        return "nodes";
    }

}
