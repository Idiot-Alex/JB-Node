package com.hotstrip.jbnode.node;

import com.hotstrip.jbnode.JbNodeApplicationTests;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class NodeVersionServiceTest extends JbNodeApplicationTests {
    @Resource
    private NodeVersionService nodeVersionService;

    @Test
    void getNodeList() {
        nodeVersionService.getNodeList();
    }
}