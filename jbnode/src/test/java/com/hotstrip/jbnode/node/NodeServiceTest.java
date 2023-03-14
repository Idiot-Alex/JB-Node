package com.hotstrip.jbnode.node;

import com.hotstrip.jbnode.JbNodeApplicationTests;
import com.hotstrip.jbnode.util.CompressUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
class NodeServiceTest extends JbNodeApplicationTests {
    @Resource
    private NodeService nodeService;
    @Test
    void download() throws Exception {
        String url = "https://nodejs.org/download/release/v18.15.0/node-v18.15.0-darwin-arm64.tar.gz";
        String filePath = "build-dist/node-v18.15.0-darwin-arm64.tar.gz";
        nodeService.download(url, filePath);

        Assert.isTrue(Files.exists(Paths.get(filePath)), "file not exists");
    }

    @Test
    void decompress() throws Exception {
        String filePath = "build-dist/node-v18.15.0-darwin-arm64.tar.gz";
        String outPath = "build-dist/node-v18.15.0-darwin-arm64";

        CompressUtil.decompressTarGz(filePath, outPath);

        Assert.isTrue(Files.exists(Paths.get(outPath)));
    }

}