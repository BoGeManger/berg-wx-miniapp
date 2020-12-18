package com.berg.common;

import io.minio.*;
import io.minio.messages.DeleteError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@Component
public class MinioUtil {

    static String minioUrl;
    static String serviceUrl;
    static String minioName;
    static String minioPass;

    public static String getMinioUrl() {
        return minioUrl;
    }

    public static void setMinioUrl(String minioUrl) {
        MinioUtil.minioUrl = minioUrl;
    }

    public static String getServiceUrl() {
        return serviceUrl;
    }

    public static void setServiceUrl(String serviceUrl) {
        MinioUtil.serviceUrl = serviceUrl;
    }

    public static String getMinioName() {
        return minioName;
    }

    public static void setMinioName(String minioName) {
        MinioUtil.minioName = minioName;
    }

    public static String getMinioPass() {
        return minioPass;
    }

    public static void setMinioPass(String minioPass) {
        MinioUtil.minioPass = minioPass;
    }

    public static MinioClient getMinioClient() {
        return minioClient;
    }

    public static void setMinioClient(MinioClient minioClient) {
        MinioUtil.minioClient = minioClient;
    }


    private static MinioClient minioClient = null;

    /**
     * 初始化客户端
     * @return
     */
    public static MinioClient initMinio() throws Exception{
        if (minioClient == null) {
            minioClient = new MinioClient(minioUrl, minioName,minioPass);
        }
        return minioClient;
    }

    /**
     * 判断 bucket是否存在
     * @param bucketName
     * @return
     * @throws Exception
     */
    public static boolean bucketExists(String bucketName)throws Exception{
        return minioClient.bucketExists(bucketName);
    }

    /**
     * 创建 bucket
     * @param bucketName
     * @throws Exception
     */
    public static void makeBucket(String bucketName) throws Exception{
        boolean isExist = minioClient.bucketExists(bucketName);
        if(!isExist) {
            minioClient.makeBucket(bucketName);
        }
    }

    /**
     * 获取文件的元数据
     * @param bucketName
     * @param objectName
     * @return
     * @throws Exception
     */
    public static ObjectStat stat(String bucketName,String objectName) throws Exception{
        return minioClient.statObject(bucketName, objectName);
    }

    /**
     * 校验是否存在文件
     * @param bucketName
     * @param objectName
     * @return
     */
    public static boolean check(String bucketName,String objectName){
        boolean isExist = true;
        try{
            // 调用statObject()来判断对象是否存在。
            // 如果不存在, statObject()抛出异常,
            // 否则则代表对象存在。
            minioClient.statObject(bucketName, objectName);
        }catch (Exception ex){
            isExist = false;
        }
        return isExist;
    }

    /**
     * 以流的形式下载一个文件(调用结束后需stream.close()关闭流)
     * @param bucketName
     * @param objectName
     * @return
     * @throws Exception
     */
    public static InputStream get(String bucketName,String objectName) throws Exception{
        InputStream stream = null;
        if(check(bucketName,objectName)){
            // 获取的输入流。
            stream = minioClient.getObject(bucketName, objectName);
        }
        return stream;
    }

    /**
     * 下载文件指定区域的字节数组做为流。（断点下载）(调用结束后需stream.close()关闭流)
     * @param bucketName
     * @param objectName
     * @param offset
     * @param length
     * @return
     * @throws Exception
     */
    public static InputStream get(String bucketName, String objectName, long offset, Long length)throws Exception{
        InputStream stream = null;
        if(check(bucketName,objectName)){
            // 获取的输入流。
            stream = minioClient.getObject(bucketName, objectName,offset,length);
        }
        return stream;
    }

    /**
     * 下载并将文件保存到本地
     * @param bucketName
     * @param objectName
     * @param fileName
     * @throws Exception
     */
    public static void get(String bucketName, String objectName, String fileName) throws Exception{
        if(check(bucketName,objectName)){
            // 获取的输入流。
            minioClient.getObject(bucketName, objectName,fileName);
        }else{
            throw new Exception("对象不存在");
        }
    }

    /**
     * 下载加密文件(调用结束后需stream.close()关闭流)
     * @param bucketName
     * @param objectName
     * @param key
     * @return
     * @throws Exception
     */
    public static InputStream get(String bucketName, String objectName, ServerSideEncryption key)throws Exception{
        InputStream  stream = null;
        if(check(bucketName,objectName)){
            // 获取的输入流。
            stream = minioClient.getObject(bucketName, objectName,key);
        }
        return  stream;
    }

    /**
     * 获取文件路径
     * @param bucketName
     * @param objectName
     * @return
     * @throws Exception
     */
    public static String getUrl(String bucketName, String objectName) throws Exception{
        return  getServiceUrl() + minioClient.getObjectUrl(bucketName,objectName).replaceAll(getMinioUrl(),"");
    }

    /**
     * 通过MultipartFile上传文件
     * @param bucketName
     * @param objectName
     * @param multipartfile
     * @throws Exception
     */
    public static String put(String bucketName, String objectName, MultipartFile multipartfile) throws Exception{
        InputStream stream = multipartfile.getInputStream();
        PutObjectOptions options = new PutObjectOptions(stream.available(),-1);
        options.setContentType(multipartfile.getContentType());
        minioClient.putObject(bucketName, objectName,stream,options);
        return  getUrl(bucketName,objectName);
    }

    /**
     * 通过文件流上传文件
     * @param bucketName
     * @param objectName
     * @param stream
     * @throws Exception
     */
    public static void put(String bucketName, String objectName, InputStream stream) throws Exception{
        PutObjectOptions options = new PutObjectOptions(stream.available(),-1);
        options.setContentType("application/octet-stream");
        minioClient.putObject(bucketName, objectName,stream,options);
    }

    /**
     * 通过文件流上传文件(可加密)
     * @param bucketName
     * @param objectName
     * @param stream
     * @param options
     * @throws Exception
     */
    public static void put(String bucketName, String objectName, InputStream stream, PutObjectOptions options) throws Exception{
        minioClient.putObject(bucketName, objectName,stream,options);
    }

    /**
     * 删除文件
     * @param bucketName
     * @param objectName
     * @throws Exception
     */
    public static void remove(String bucketName, String objectName) throws Exception{
        minioClient.removeObject(bucketName, objectName);
    }

    /**
     * 删除多个文件
     * @param bucketName
     * @param objectNames
     * @return
     * @throws Exception
     */
    public static Iterable<Result<DeleteError>> remove(String bucketName, Iterable<String> objectNames) throws Exception{
        return minioClient.removeObjects(bucketName, objectNames);
    }

    /**
     * 删除一个未完整上传的文件
     * @param bucketName
     * @param objectName
     * @throws Exception
     */
    public static void removeIncompleteUpload(String bucketName, String objectName) throws Exception{
        minioClient.removeIncompleteUpload(bucketName, objectName);
    }
}
