package com.hotstrip.jbnode.util;

import com.hotstrip.jbnode.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
class CommonUtilTest {

    @Test
    void createParentDirectory() throws IOException {
        String file = "a/b/c.txt";
        CommonUtil.createParentDirectory(file);

        Assert.isTrue(Files.exists(Paths.get(file).getParent()));

        CommonUtil.deleteDirectory("a");
    }
}