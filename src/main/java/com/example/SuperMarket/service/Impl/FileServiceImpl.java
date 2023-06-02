package com.example.SuperMarket.service.Impl;



import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.example.SuperMarket.service.FileService;
import com.example.SuperMarket.utils.ConstandPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Component
public class FileServiceImpl implements FileService {
    @Override
    public HashMap<String, String> uploadingMusicFile(MultipartFile file) {
        String endpoint = ConstandPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstandPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstandPropertiesUtils.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstandPropertiesUtils.BUCKET_NAME;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String originalFilename = ""+System.currentTimeMillis()+file.getOriginalFilename();
        String fileName = file.getOriginalFilename();
        String datePath = new DateTime().toString("yyyy/MM/dd");
        originalFilename=datePath+"/"+originalFilename;
        try {
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, originalFilename, inputStream);
            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 如果上传成功，则返回200。
            System.out.println(result.getResponse().getStatusCode());
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        }
        catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        String url="https://"+bucketName+"."+endpoint+"/"+originalFilename;
        HashMap<String,String> map=new HashMap<>();
        map.put("url",url);
        map.put("fileName",originalFilename);
        map.put("name",fileName);
        return map;
    }

    @Override
    public boolean removeMusicFileByUrl(String url) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstandPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstandPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstandPropertiesUtils.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstandPropertiesUtils.BUCKET_NAME;
        // 填写文件完整路径。文件完整路径中不能包含Bucket名称。
        String objectName = url;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            System.out.println(bucketName+"====="+objectName);
            // 删除文件或目录。如果要删除目录，目录必须为空。
            boolean b = ossClient.doesObjectExist(bucketName, objectName);
            if(b){ossClient.deleteObject(bucketName, objectName);}
            else {
                return false;
            }
        } catch (OSSException oe) {
            oe.getStackTrace();
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
            return false;
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
            return false;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
return true;
    }
}
