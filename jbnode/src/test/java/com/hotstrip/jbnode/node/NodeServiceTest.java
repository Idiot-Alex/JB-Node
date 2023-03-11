package com.hotstrip.jbnode.node;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import com.hotstrip.jbnode.JbNodeApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import javax.annotation.Resource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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