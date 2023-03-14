package com.hotstrip.jbnode.domain.node;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotstrip.jbnode.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NodeVersionService {

    public List<NodeModel> getNodeList() {
        String fileName ="node-list.json";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }

            String jsonString = jsonStringBuilder.toString();
            ObjectMapper objectMapper = new ObjectMapper();

            List<NodeModel> list = JacksonUtil.toArray(jsonString, NodeModel.class);

            Map<String, NodeModel> map = list.stream()
                    .filter(n -> !"false".equals(n.getLts()))
                    .collect(Collectors.toMap(n -> n.getVersion().split("\\.")[0],
                            Function.identity(), (n1, n2) -> n1));

//            map.forEach(n -> log.info(n.toString()));
            log.info("map: {}", map.size());

            new ArrayList<>(map.values()).forEach(n -> log.info(n.toString()));
            return map.values().stream().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
