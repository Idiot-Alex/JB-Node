package com.hotstrip.jbnode.node;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotstrip.jbnode.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NodeVersionService {

    public String getNodeList() {
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
//            objectMapper.readValue(jsonString, new TypeReference<List<NodeModel>>(){});
            list.forEach(nodeModel -> {
                log.info(nodeModel.toString());
            });



        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "1.0.0";
    }
}
