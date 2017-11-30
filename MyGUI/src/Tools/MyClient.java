package Tools;

import GUI.MyGUI;
import GUI.MyGUI_SendAndReceive;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class MyClient extends Thread
{
    public static final String serverName = "localhost";
    public static final int port = 6066;
    private String myFileName;
    private String myPassword;
    private boolean sendOrReceive;
    private static JTextPane myTextPane;
    private Socket client;

    public MyClient(boolean flag,String fileName,String password,JTextPane textPane) throws IOException
    {
        //true是send，false是receive
        this.sendOrReceive = flag;
        this.myFileName = fileName;
        this.myPassword = password;
        this.myTextPane = textPane;
        System.out.println("连接到主机：" + serverName + " ，端口号：" + port);
        this.client = new Socket(serverName, port);
        System.out.println("欲连接服务器地址：" + client.getRemoteSocketAddress());
    }
    public void run()
    {
        try
        {

            if(sendOrReceive == true)
                 send();
            else
                receive();
        }catch(Exception e) {}

    }
    private void send()
    {
        String receiveInfo = null;
        try
        {
            DataOutputStream infoToServer = new DataOutputStream(client.getOutputStream());
            infoToServer.writeUTF("Update"+" "+myFileName+" "+myPassword);

            DataInputStream infoFromServer = new DataInputStream(client.getInputStream());
            receiveInfo = infoFromServer.readUTF();
            System.out.println("服务器响应：" + receiveInfo);
            if(receiveInfo.equals("上传成功"))
            {
                System.out.println("开始传输文件 ");
                ObjectOutputStream oos = new ObjectOutputStream(new DataOutputStream(client.getOutputStream()));
                oos.writeObject(myTextPane);
                oos.flush();
                oos.close();
                System.out.println("传输文件完毕 ");
                MyGUI_SendAndReceive.setServerInfo(receiveInfo);
                MyGUI.getFrame().setTitle("My编辑器-"+myFileName);
                return;
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private void receive()
    {
        String receiveInfo = null;
        try
        {
            DataOutputStream infoToServer = new DataOutputStream(client.getOutputStream());
            infoToServer.writeUTF("DownLoad"+" "+myFileName+" "+myPassword);

            DataInputStream infoFromServer = new DataInputStream(client.getInputStream());
            receiveInfo = infoFromServer.readUTF();
            System.out.println("服务器响应：" + receiveInfo);
            if(receiveInfo.equals("下载成功"))
            {
                System.out.println("开始接收文件 ");
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream (client.getInputStream()));
                JTextPane textPane =(JTextPane)ois.readObject();
                MyGUI.setTextEditForscrollPaneEdit(textPane);
                ois.close();
                System.out.println("接收文件完成");
                MyGUI_SendAndReceive.setServerInfo(receiveInfo);
                MyGUI.getFrame().setTitle("My编辑器-"+myFileName);
                return;
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
