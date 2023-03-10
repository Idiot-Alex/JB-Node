package com.hotstrip.jbnode.command;

import com.hotstrip.jbnode.node.NodeModel;
import com.hotstrip.jbnode.node.NodeVersionService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.annotation.Resource;
import java.util.List;

@ShellComponent
public class NodeVersionCommand {

    @Resource
    private NodeVersionService nodeVersionService;

    @ShellMethod("show node version list")
    public List<NodeModel> nodeList() {
        return nodeVersionService.getNodeList();
    }
}
