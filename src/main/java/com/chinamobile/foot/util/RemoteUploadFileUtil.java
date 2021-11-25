package com.chinamobile.foot.util;


import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.*;

public class RemoteUploadFileUtil {
    private static Channel channel = null;
    private static ChannelSftp sftp = null;
    private static Session sshSession = null;
    @Value("${mat.local.path}")
    private static String dPath;

    public static void uploadToRemote(String path, String fileName) {
        JSch jsch = new JSch();
        Session session = null;

        try {
            //用户名，ip地址，端口号
            session = jsch.getSession("ehead", "8.131.242.171", 22);
        } catch (JSchException e) {
            e.printStackTrace();
        }

        //设置登录主机的密码
        session.setPassword("ehead1234");
        //设置第一次登录时候提示，可行值：（ask/yes/no）
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登录超时时间
        try {
            session.connect(300000);
        } catch (JSchException e) {
            e.printStackTrace();
        }

        Channel channel = null;
        try {
            channel = session.openChannel("sftp");
            channel.connect(10000000);
            ChannelSftp sftp = (ChannelSftp) channel;
            String uploadPath = dPath + DateTimeUtils.format(new Date(), "yyyyMMdd");
            //String uploadPath = dPath;

            try {
                sftp.cd(uploadPath);
            } catch (SftpException e) {
                //e.printStackTrace();
                sftp.mkdir(uploadPath);
                sftp.cd(uploadPath);
            }

            /*OutputStream outputStream = null;
            File file = new File(path, fileName);
            outputStream = sftp.put(file.getName());
            outputStream.write(content.toString().getBytes("UTF-8"));
            outputStream.close();*/

            /*InputStream inStream = null;
            outputStream = new FileOutputStream(currentFile);
            // support break point continue
            inStream = cs.get(remoteAbsoluteFile);
            byte[] buffer = new byte[1024];
            while (true) {
                // read file into buffer
                int bytesRead = inStream.read(buffer);
                if (bytesRead == -1) {
                    break;
                    // //if transfer is end, wait for 2000ms
                    // Thread.sleep(2000);
                    // //if original size of file is lower than transferedBytes, symbol file has done transfered
                    // if(1 <= transferedBytes){
                    // break;
                    // }
                }
                // transferedBytes += bytesRead;
                // write content of buffer in output channel
                outputRStream.write(buffer, 0, bytesead);
            }*/


            sftp.put(path + fileName, uploadPath + "/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            channel.disconnect();
        }


    }


    public static List<String> listFileName(Channel channel, String serverPath) {
        List<String> list = new ArrayList<>();
        ChannelSftp sftp = null;

        try {
            sftp = (ChannelSftp) channel;
            Vector vector = sftp.ls(serverPath);
            for (Object item : vector) {
                if (item instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
                    String fileName = ((com.jcraft.jsch.ChannelSftp.LsEntry) item).getFilename();
                    if (fileName.equals(".") || fileName.equals("..")) {
                        continue;
                    }
                    System.out.println(fileName);
                    list.add(fileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 下载文件
     *
     * @param
     */
    public static void getFileFromSftpServer(Channel channel, String serverPath, String localPath) {
        ChannelSftp sftp = null;
        File file = null;

        try {
            File dir = new File(localPath);
            if (dir.isFile()) {
                System.out.println(dir + " is  a file already ");
                return;
            }

            sftp = (ChannelSftp) channel;
            Vector files = sftp.ls(serverPath);
            for (Iterator<ChannelSftp.LsEntry> iterator = files.iterator(); iterator.hasNext(); ) {
                ChannelSftp.LsEntry str = iterator.next();
                String filename = str.getFilename();
                if (filename.equals(".") || filename.equals("..")) {
                    continue;
                }

                SftpATTRS attrs = str.getAttrs();
                boolean isdir = attrs.isDir();

                String localFilePath = localPath + "/" + filename;
                String serverFilePath = serverPath + "/" + filename;
                if (isdir) {
                    File dir2 = new File(localFilePath);
                    if (!dir2.exists()) {
                        System.out.println("###make dir=" + localFilePath);
                        dir2.mkdir();
                    } else {
                        boolean b = dir2.isDirectory();
                        if (!b) {

                        }
                    }
                    getFileFromSftpServer(channel, serverFilePath, localFilePath);
                } else {
                    /**
                     *
                     */
                    long size = attrs.getSize();
                    int aTime = attrs.getATime();
                    int mTime = attrs.getMTime();
                    //getFileFromSftpServer(channel, serverFilePath, localFilePath);

                    //sftp.get(serverFilePath, localPath);

                    file = new File(localPath, filename);
                    if (!file.exists() || file.length() != size) {
                        sftp.get(serverFilePath, localPath);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static Channel getChannel(String host, int port, String username, String password) {

        try {
            JSch jSch = new JSch();
            sshSession = jSch.getSession(username, host, port);
            sshSession.setPassword(password);
            //Properties sshConfig = new Properties();
            //sshConfig.put("StrictHostKeyChecking", "no");
            //sshSession.setConfig(sshConfig);
            sshSession.setConfig("StrictHostKeyChecking", "no");
            sshSession.connect();
            channel = sshSession.openChannel("sftp");
            channel.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return channel;
    }

    private static void closeResource(Channel channel, ChannelSftp sftp, Session session) {
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
        }

        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }

        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }

    }


    public static void main(String[] args) {
        //RemoteUploadFileUtil.uploadToRemote("safdsa", "D:\\project\\文档\\移动\\mat\\A03E.mat");
        //RemoteUploadFileUtil.uploadToRemote("D:\\project\\文档\\移动\\mat\\", "A03E.mat");

        channel = getChannel("8.131.242.171", 22, "ehead", "ehead1234");
        List<String> str = listFileName(channel, "/mat/");
        System.out.println("####str=" + str);

        getFileFromSftpServer(channel, "/mat", "D:\\project\\文档\\移动\\mat");

        closeResource(channel, sftp, sshSession);
    }

}
