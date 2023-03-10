package com.hotstrip.jbnode.node;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;

@Service
@Slf4j
public class NodeService {

    @Resource
    private RestTemplate restTemplate;


    public void download(String url) throws Exception {

        String filePath = "node-v18.15.0-darwin-arm64.tar.gz";

        downloadFile(url, filePath);

        //定义请求头的接收类型
//        RequestCallback requestCallback = request -> request.getHeaders()
//                .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
//        //对响应进行流式处理而不是将其全部加载到内存中
//        restTemplate.execute(url, HttpMethod.GET, requestCallback, clientHttpResponse -> {
//            Files.copy(clientHttpResponse.getBody(), Paths.get(filePath));
//            return null;
//        });

    }

    public void downloadFile(String fileUrl, String fileName) throws Exception {
        URL url = new URL(fileUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // 如果响应码不是 200，则说明下载出错
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("下载文件时出错，响应码为：" + responseCode);
        }

        // 获取文件长度
        int contentLength = httpConn.getContentLength();
        System.out.println("下载文件的大小（字节）：" + contentLength);

        // 创建一个输入流来读取文件内容
        InputStream inputStream = httpConn.getInputStream();

        // 创建一个文件输出流来保存文件，注意：这里没有使用 BufferedOutputStream，并且将缓冲区设置为较大的值，可以提高性能
        FileOutputStream outputStream = new FileOutputStream(fileName);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        long totalBytesRead = 0;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
            totalBytesRead += bytesRead;

            // 根据需要，可以添加代码用于在控制台上显示下载进度
            // ...

            // 计算下载进度
            int percent = (int) (totalBytesRead * 100 / contentLength);

            // 清除控制台上的上一个输出，以显示进度条更新
            log.info("\r");

            // 使用日志输出进度条
            log.info("\033[2K\r下载进度：[{}%] [{} / {}]", percent, formatFileSize(totalBytesRead),
                    formatFileSize(contentLength));
//            double progress = (double) totalBytesRead / contentLength * 100;
//            log.info(String.format("Downloaded %.2f%%\r", progress));
        }

        // 关闭输入流和输出流，释放资源
        outputStream.close();
        inputStream.close();
        httpConn.disconnect();
    }

    /**
     * 格式化文件大小，将字节数转为带单位的字符串
     */
    private String formatFileSize(long size) {
        String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int index = 0;
        double value = size;

        while (value > 1024 && index < units.length - 1) {
            value = value / 1024;
            index++;
        }

        return String.format("%.2f %s", value, units[index]);
    }
}
