package com.hotstrip.jbnode.domain.node;

import com.hotstrip.jbnode.common.annotations.CalcExecTime;
import com.hotstrip.jbnode.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
public class NodeService {

    @Resource
    private RestTemplate restTemplate;

    @CalcExecTime
    public void downloadFile(String fileUrl, String filePath) throws Exception {
        CommonUtil.createParentDirectory(filePath);

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
             FileOutputStream outputStream = new FileOutputStream(filePath)
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
