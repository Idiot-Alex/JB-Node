package com.hotstrip.jbnode.node;

import com.hotstrip.jbnode.JbNodeApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

@Slf4j
class NodeServiceTest extends JbNodeApplicationTests {
    @Resource
    private NodeService nodeService;
    @Test
    void download() throws Exception {
        String url = "https://nodejs.org/download/release/v18.15.0/node-v18.15.0-darwin-arm64.tar.gz";
        nodeService.download(url);

    }

}