package com.hotstrip.jbnode.node;

import com.hotstrip.jbnode.JbNodeApplicationTests;
import com.hotstrip.jbnode.domain.node.NodeVersionService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

class NodeVersionServiceTest extends JbNodeApplicationTests {
    @Resource
    private NodeVersionService nodeVersionService;

    @Test
    void getNodeList() {
        nodeVersionService.getNodeList();
    }
}