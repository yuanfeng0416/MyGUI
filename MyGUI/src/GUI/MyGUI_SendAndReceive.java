package GUI;

import Tools.MyClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class MyGUI_SendAndReceive {
    private static final Font fontBtn = new Font("黑体", Font.PLAIN, 25);
    private static final Font fontLabel = new Font("黑体", Font.PLAIN, 24);
    private static final Font fontTextField = new Font("黑体", Font.PLAIN, 20);
    private JDialog dialogSendAndReceive;
    private Container container;
    private Box box;
    private Box boxTop;
    private Box boxMiddle;
    private Box boxBottom;
    private JTextField textFileName;
    private JTextField textPassword;
    private JButton btnSend;
    private JButton btnReceive;
    public static JLabel serverInfo;

    public MyGUI_SendAndReceive(JFrame f, String s, boolean b) {
        UIManager.put("Button.font", fontBtn);
        UIManager.put("Label.font", fontLabel);
        UIManager.put("TextField.font", fontTextField);
        this.dialogSendAndReceive = new JDialog(f, s, b);
        dialogSendAndReceive.setSize(400, 200);
        //初始化容器
        this.container = new Container();
        container = dialogSendAndReceive.getContentPane();
        container.setLayout(new BorderLayout());
        //初始化上下BOX
        this.box = Box.createVerticalBox();//竖
        this.boxTop = Box.createHorizontalBox();//横
        this.boxMiddle = Box.createHorizontalBox();//横
        this.boxBottom = Box.createHorizontalBox();
        container.add(box);
        box.add(boxTop);
        box.add(Box.createVerticalStrut(8));
        box.add(boxMiddle);
        box.add(Box.createVerticalStrut(8));
        box.add(boxBottom);
        //初始化发送和密令输入框
        this.textFileName = new JTextField();
        boxTop.add(new JLabel("云件名")); // 加入标签
        boxBottom.add(Box.createHorizontalStrut(8));
        boxTop.add(textFileName); // 加入组件
        this.textPassword = new JTextField();
        boxMiddle.add(new JLabel("云密令")); // 加入标签
        boxMiddle.add(textPassword); // 加入组件
        this.serverInfo = new JLabel("状态栏");
        this.btnSend = new JButton("上传");
        this.btnReceive = new JButton("下载");
        boxBottom.add(serverInfo); // 加入标签
        boxBottom.add(Box.createHorizontalStrut(8));
        boxBottom.add(btnSend);
        boxBottom.add(Box.createHorizontalStrut(8));
        boxBottom.add(btnReceive);
        //初始化事件
        myGUI_Send();
    }

    private void myGUI_Send() {
        btnReceive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //true是send，false是receive
                    new MyClient(false, textFileName.getText(), textPassword.getText(), MyGUI.getTextEdit()).start();
                } catch (IOException ioe) {
                }

            }
        });
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //true是send，false是receive
                    new MyClient(true, textFileName.getText(), textPassword.getText(), MyGUI.getTextEdit()).start();
                } catch (IOException ioe) {
                }

            }
        });
    }

    public static void setServerInfo(String s)
    {
        serverInfo.setText(s);
    }
    public void setDialogNetVisible(boolean b)
    {
        this.dialogSendAndReceive.setVisible(b);
    }
}
