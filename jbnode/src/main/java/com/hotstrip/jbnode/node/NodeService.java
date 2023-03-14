package com.hotstrip.jbnode.node;

import com.hotstrip.jbnode.common.annotations.CalcExecTime;
import com.hotstrip.jbnode.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
public class NodeService {

    @Resource
    private RestTemplate restTemplate;


    @CalcExecTime
    public void download(String url, String filePath) throws Exception {
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
        CommonUtil.createParentDirectory(fileName);

        URL url = new URL(fileUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // 如果响应码不是 200，则说明下载出错
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("下载文件时出错，响应码为：" + responseCode);
        }

        // 获取文件长度
        int contentLength = httpConn.getContentLength();
        log.info("下载文件的大小：{}", CommonUtil.formatFileSize(contentLength));

        // 创建输入流 输出流
        try (InputStream inputStream = httpConn.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(fileName)
        ) {
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            long totalBytesRead = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                // 计算下载进度
                int percent = (int) (totalBytesRead * 100 / contentLength);

                // 使用日志输出进度条
                log.info("\033[2K\r下载进度：[{}%] [{} / {}]",
                        percent, CommonUtil.formatFileSize(totalBytesRead),
                        CommonUtil.formatFileSize(contentLength));
            }
        }

        // 关闭连接，释放资源
        httpConn.disconnect();

        log.info("下载完成");
    }

}
