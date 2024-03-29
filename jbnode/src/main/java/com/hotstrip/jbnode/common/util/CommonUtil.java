package com.hotstrip.jbnode.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class CommonUtil {
    /**
     * format time millis
     * @param millis time millis
     * @return String
     */
    public static String formatTimeMillis(long millis) {
        String[] units = new String[] { "ms", "s", "m", "h", "d" };
        int index = 0;
        double value = millis;

        while (value > 1024 && index < units.length - 1) {
            if (index == 0) {
                value = value / 1000;
            } else if (index == 3) {
                value = value / 24;
            } else {
                value = value / 60;
            }
            index++;
        }

        return String.format("%.2f %s", value, units[index]);
    }

    /**
     * format File size
     * @param size size
     * @return
     */
    public static String formatFileSize(long size) {
        String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int index = 0;
        double value = size;

        while (value > 1024 && index < units.length - 1) {
            value = value / 1024;
            index++;
        }

        return String.format("%.2f %s", value, units[index]);
    }

    /**
     * create parent directory
     * @param fileName file name
     * @throws IOException
     */
    public static void createParentDirectory(String fileName) throws IOException {
        createParentDirectory(Paths.get(fileName));
    }

    /**
     * create parent directory
     * @param path path
     * @throws IOException
     */
    public static void createParentDirectory(Path path) throws IOException {
        if (null == path) {
            return;
        }

        path = path.getParent();
        createParentDirectory(path);

        if (null != path && !Files.exists(path)) {
            Files.createDirectory(path);
        }
    }

    /**
     * delete directory
     * @param fileName fileName
     * @return true or false
     */
    public static boolean deleteDirectory(String fileName) {
        Path filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            return true;
        }

        try {
            Files.walk(filePath)
                    .sorted((a, b) -> -a.compareTo(b))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            log.warn("Failed to delete {}, reason: {}", path,  e.getMessage());
                        }
                    });
            return true;
        } catch(IOException e) {
            log.warn("Failed to delete {}, reason: {}", filePath, e.getMessage());
        }
        return false;
    }

    /**
     * exec command
     * @param cmd
     * @return Map {
     *     "cmd": "cmd",
     *     "code": "0",
     *     "result": "result"
     * }
     */
    public static Map<String, String> exec(String cmd) {
        Map<String, String> map = new HashMap<>();
        map.put("cmd", cmd);
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd.split(" "));
            Process p = pb.start();

            // 读取进程输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append("\n").append(line);
            }
            p.waitFor();

            map.put("code", String.valueOf(p.exitValue()));
            map.put("result", buffer.toString());
        } catch (Exception e) {
            map.put("code", "-1");
            log.warn("Failed to exec {}, reason: {}", cmd, e.getMessage());
        }
        return map;
    }

    /**
     * read file
     * @param file
     * @return
     * @throws Exception
     */
    public static String readFile(File file) throws Exception {
        StringBuffer jsonString = new StringBuffer();
        // 读取文件内容
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // 将读取的内容存储为字符串
            String line = reader.readLine();
            while (line != null) {
                jsonString.append(line);
                line = reader.readLine();
            }
        }
        return jsonString.toString();
    }
}
