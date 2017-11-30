package MyTestServer;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;

public class MyServer
{
    private ServerSocket MyServerSocket;

    public MyServer(int port) throws IOException
    {
        MyServerSocket = new ServerSocket(port);
        MyServerSocket.setSoTimeout(10000);
        System.out.println("服务器启动成功！服务器端口号为"+MyServerSocket.getLocalPort());
    }

    public void LoadFromClient() throws IOException
    {
           while (true)
           {
               try
               {
                   System.out.println("服务器运行正常，等待连接中...");
                   Socket socket = MyServerSocket.accept();
                   Thread server = new MyTask(socket);
                   server.start();
                   //循环保持回应每个客户端的socket套接字并且进行线程化，保证多个客户端同时访问服务端
               }catch (SocketTimeoutException s)
               {
                   System.out.println("暂时无连接客户端");
               }

           }
    }
    static class MyTask extends Thread
    {
        public Socket server;

        public MyTask(Socket server)
        {
            this.server = server;
            System.out.println("客户端连接，地址及端口为"+server.getRemoteSocketAddress());
        }

        public void run()
        {
            try
            {
               receiveInfo();
            }catch(Exception e)
            {e.printStackTrace();}
        }
        private void receiveInfo() throws IOException
        {
            try
            {
                //接受客户端的消息请求
                DataInputStream infoFromClient = new DataInputStream(server.getInputStream());
                String string = infoFromClient.readUTF();
                System.out.println("客户端请求: "+string);
                String[] message = string.split(" ");
                //三个字段，下载上传，文件名，密令
                DataOutputStream infoToClient = new DataOutputStream(server.getOutputStream());
                if(message[0].equals("DownLoad"))
                {
                    //验证数据库中是否有该下载文件，且密令是否正确
                    infoToClient.writeUTF("下载成功");
                    sendFile();
                }else if(message[0].equals("Update"))
                {
                    //验证数据库中是否有文件名已经存在，存在就覆盖，不存在就创建
                    infoToClient.writeUTF("上传成功");
                    receiveFile();
                }

            }catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        private void receiveFile() throws IOException
        {
            try
            {
                System.out.println("客户端有文件传输...");
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream (server.getInputStream()));
                JTextPane textPane =(JTextPane)ois.readObject();
                ois.close();

                File file = new File("C:\\Users\\Administrator\\Desktop\\Server\\Test");
                if(!file.exists()) {
                    file.createNewFile();
                }
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(textPane);
                oos.flush();
                oos.close();
                System.out.println("文件传输成功");
                server.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        private void sendFile() throws IOException
        {
            try
            {
                System.out.println("开始文件传输");
                File file = new File("C:\\Users\\Administrator\\Desktop\\Server\\test");
                if(!file.exists()) {
                    return;
                }
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                JTextPane textPane = (JTextPane)ois.readObject();
                ois.close();
                System.out.println(textPane);
                ObjectOutputStream oos = new ObjectOutputStream(new DataOutputStream(server.getOutputStream()));
                oos.writeObject(textPane);
                oos.flush();
                oos.close();

                System.out.println("文件传输成功");

                ois.close();
                oos.close();
                server.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args)
    {
        int port = 6066;
        try
        {
            MyServer myServer = new MyServer(port);
            myServer.LoadFromClient();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}