package io.renren.webreptile;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

public class DownloadUtils {

    public static String get(String url){
        String filename = "";
        String tergetUrl = "http://" + url;
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(tergetUrl);
            CloseableHttpResponse response = httpclient.execute(httpGet);

            try {
                if (response != null
                        && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    System.out.println(response.getStatusLine());
                    HttpEntity entity = response.getEntity();
                    filename = download(entity);
                }
            } finally {
                httpclient.close();
                response.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return filename;
    }
    private static String download(HttpEntity resEntity) {
        //图片要保存的路径
        String dirPath = "d:\\img\\";
        //图片名称，可以自定义生成
        String fileName = "b_logo.png";
        //如果没有目录先创建目录，如果没有文件名先创建文件名
        File file = new File(dirPath);
        if(file == null || !file.exists()){
            file.mkdir();
        }
        String realPath = dirPath.concat(fileName);
        File filePath = new File(realPath);
        if (filePath == null || !filePath.exists()) {
            try {
                filePath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //得到输入流，然后把输入流放入缓冲区中，缓冲区--->输出流flush，关闭资源
        BufferedOutputStream out = null;
        InputStream in = null;
        try {
            if (resEntity == null) {
                return null;
            }
            in = resEntity.getContent();

            out = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] bytes = new byte[1024];
            int len = -1;
            while((len = in.read(bytes)) != -1){
                out.write(bytes,0,len);
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }

        }
        return filePath.toString();
    }
}
