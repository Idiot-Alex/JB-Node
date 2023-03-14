package com.hotstrip.jbnode.node;

import com.hotstrip.jbnode.JbNodeApplicationTests;
import com.hotstrip.jbnode.util.CommonUtil;
import com.hotstrip.jbnode.util.CompressUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;
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
    void exec_node_version() throws Exception {
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

}