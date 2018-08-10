package com.balance.server;

import com.balance.dto.TransferObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class FileServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory
            .getLogger(FileServerHandler.class);
    private String file_dir = "D:";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        TransferObject transferObject = new TransferObject();
        try{
            if (msg instanceof TransferObject) {
                transferObject= (TransferObject) msg;
                switch (transferObject.getOperatorType()){
                    case TransferObject.OPERATOR_UPLOAD:
                        upload(transferObject);
                        break;
                    case TransferObject.OPERATOR_DOWNLOAD:
                        download(transferObject);
                        break;
                    case TransferObject.OPERATOR_DELETE:
                        delete(transferObject);
                        break;
                }
            }else{
                buildTransferObjectOnException(transferObject,"msg must be transferObject");
            }
        }catch (IOException e){
            buildTransferObjectOnException(transferObject,e.getMessage());
        }
        ctx.write(transferObject);
    }

    public void upload(TransferObject transferObject) throws IOException {
        String fileName = transferObject.getFileName();//文件名
        String path = file_dir + File.separator + fileName;
        File file = new File(path);
        FileUtils.writeByteArrayToFile(file,transferObject.getBytes());
    }

    public void download(TransferObject transferObject) throws IOException {
        String fileName = transferObject.getFileName();
        String path = file_dir + File.separator + fileName;
        File file = new File(path);
        if(!file.exists()){
            throw new IOException("path does's exist");
        }
        transferObject.setBytes(FileUtils.readFileToByteArray(file));
    }

    public void delete(TransferObject transferObject) throws IOException {
        String fileName = transferObject.getFileName();
        String path = file_dir + File.separator + fileName;
        File file = new File(path);
        if(!file.exists()){
            throw new IOException("path does's exist");
        }
        FileUtils.forceDelete(file);
    }


    public void buildTransferObjectOnException(TransferObject transferObject,String message){
        transferObject.setStatus(TransferObject.STATUS_FAIL);
        transferObject.setMessage(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
