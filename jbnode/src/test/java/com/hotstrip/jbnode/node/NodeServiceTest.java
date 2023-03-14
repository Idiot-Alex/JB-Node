package com.hotstrip.jbnode.node;

import com.hotstrip.jbnode.JbNodeApplicationTests;
import com.hotstrip.jbnode.domain.node.NodeService;
import com.hotstrip.jbnode.common.util.CommonUtil;
import com.hotstrip.jbnode.common.util.CompressUtil;
import com.hotstrip.jbnode.domain.node.PackageJsonModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
class NodeServiceTest extends JbNodeApplicationTests {
    @Resource
    private NodeService nodeService;

    private static String url;
    private static String filePath;

    static {
        url = "https://nodejs.org/download/release/v18.15.0/node-v18.15.0-darwin-x64.tar.gz";
        String[] splits = url.split("/");
        filePath = String.format("build-dist/%s", splits[splits.length-1]);
    }
    @Test
    void download() throws Exception {
        nodeService.downloadFile(url, filePath);

        Assert.isTrue(Files.exists(Paths.get(filePath)), "file not exists");
    }

    @Test
    void decompress() throws Exception {
        String outPath = "build-dist/node";

        CompressUtil.decompressTarGz(filePath, outPath);

        Assert.isTrue(Files.exists(Paths.get(outPath)));
    }

    @Test
    void exec_node_version() {
        Path filePath = Paths.get("build-dist/node/node-v18.15.0-darwin-x64/bin/node");
        if (filePath.toFile().canExecute()) {
            log.info("node can execute");
            String cmd = String.format("%s -v", filePath);
            log.info("result: {}", CommonUtil.exec(cmd));
        } else {
            log.error("node can not execute");
        }

        log.info("result: {}", CommonUtil.exec("ls -l"));
    }

    @Test
    void parsePackageJson() throws Exception {
        File packageJsonFile = ResourceUtils.getFile("classpath:package.json");

        PackageJsonModel packageJsonModel = nodeService.parsePackageJsonFile(packageJsonFile);

        log.info("packageJsonModel: {}", packageJsonModel);

        Assert.notNull(packageJsonModel, "packageJsonModel is null");
    }

    @Test
    void exec_npm_install_to_path() {
        Path filePath = Paths.get("build-dist/node/node-v18.15.0-darwin-x64/bin/npm");
        if (filePath.toFile().canExecute()) {
            String cmd = String.format("%s install -g --prefix build-dist/node/node-v18.15.0-darwin-x64/node_moudles vue", filePath);
            log.info("result: {}", CommonUtil.exec(cmd));
        }  else {
            log.error("{} can not execute", filePath);
        }
    }

}