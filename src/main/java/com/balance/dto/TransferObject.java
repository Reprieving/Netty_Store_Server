package com.balance.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.Serializable;

@Setter
@Getter
public class TransferObject implements Serializable{
    public final static String STATUS_SUCCESS = "success";
    public final static String STATUS_FAIL = "fail";
    public static final String OPERATOR_UPLOAD="upload";
    public static final String OPERATOR_DOWNLOAD="download";
    public static final String OPERATOR_DELETE="delete";
    private static final long serialVersionUID = 4891073495942815375L;

    private File file;// 文件
    private String fileName;// 文件名
    private String operatorType; //上传,下载,删除
    private byte[] bytes;// 文件字节数组
    private String message;
    private String status = "success"; // 状态, 成功success，失败fail

}
