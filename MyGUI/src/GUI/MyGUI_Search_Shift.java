package GUI;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyGUI_Search_Shift
{
    private static final Font fontBtn = new Font("黑体",Font.PLAIN,25);
    private static final Font fontLabel = new Font("黑体",Font.PLAIN,24);
    private static final Font fontTextField = new Font("黑体",Font.PLAIN,20);
    private boolean isAlreadySearch = false;
    private boolean isAbleToShift = true;
    private JDialog dialogSearchShift;
    private Container container;
    private Box box;
    private Box boxTop;
    private Box boxBottom;
    private JTextField textSearch;
    private JTextField textShift;
    private JButton btnSearch;
    private JButton btnCancel;
    private JButton btnShift;
    private JButton btnShiftAll;
    private String stringEdit;
    private String stringSearch;
    private String stringShift;
    private Document docsForTextEidt;

    public MyGUI_Search_Shift(JFrame f, String s, boolean b)
    {
        UIManager.put("Button.font",fontBtn);
        UIManager.put("Label.font",fontLabel);
        UIManager.put("TextField.font",fontTextField);
        this.dialogSearchShift = new JDialog(f,s,b);
        dialogSearchShift.setSize(600,200);
        //初始化容器
        this.container = new Container();
        container=dialogSearchShift.getContentPane();
        container.setLayout(new BorderLayout());
        //初始化上下BOX
        this.box = Box.createVerticalBox();//竖
        this.boxTop = Box.createHorizontalBox();//横
        this.boxBottom = Box.createHorizontalBox();
        container.add(box);
        box.add(boxTop);
        box.add(Box.createVerticalStrut(8));
        box.add(boxBottom);
        //初始化查找和替换
        textSearch = new JTextField();
        textShift = new JTextField();
        boxTop.add(new JLabel("查找文字")); // 加入标签
        boxTop.add(textSearch); // 加入组件
        boxTop.add(Box.createHorizontalStrut(8)); // 间距
        boxTop.add(new JLabel("替换文字")); // 加入标签
        boxTop.add(textShift); // 加入组件
        boxTop.add(Box.createHorizontalStrut(8)); // 间距
        //初始化查找替换按钮
        btnSearch = new JButton("查找");
        btnCancel = new JButton("取消");
        btnShift = new JButton("替换");
        btnShiftAll = new JButton("替换全部");
        boxBottom.add(btnSearch);
        boxBottom.add(Box.createHorizontalStrut(30));
        boxBottom.add(btnCancel);
        boxBottom.add(Box.createHorizontalStrut(30));
        boxBottom.add(btnShift);
        boxBottom.add(Box.createHorizontalStrut(30));
        boxBottom.add(btnShiftAll);
        //初始化事件响应
        myGUI_Search_ShiftEvent();
    }
    private void myGUI_Search_ShiftEvent()
    {
        btnSearch.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Object obj = e.getSource();
                if (obj instanceof JButton)
                {
                    int now=0,last=0;
                    //获得文本框文本对象
                    docsForTextEidt = MyGUI.getTextEdit().getDocument();
                    stringSearch = textSearch.getText();
                    if(!stringSearch.equals("") && isAlreadySearch == false)
                    {
                        try
                        {
                            while (now!=-1)
                            {
                                stringEdit = docsForTextEidt.getText(0,docsForTextEidt.getLength());
                                now=stringEdit.indexOf(stringSearch,last);
                                docsForTextEidt.remove(now,stringSearch.length());
                                docsForTextEidt.insertString(now, "【"+stringSearch+"】",null);
                                last = now+stringSearch.length()+2;
                            }
                        }catch(Exception ale) {}
                        isAlreadySearch = true;
                        isAbleToShift = true;
                    }
                }
            }
        });
        btnCancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Object obj = e.getSource();
                if (obj instanceof JButton) {
                    int now = 0, last = 0;
                    //获得文本框文本对象
                    docsForTextEidt = MyGUI.getTextEdit().getDocument();
                    if (!stringSearch.equals("") && isAlreadySearch == true) {
                        try {
                            while (now != -1) {
                                stringEdit = docsForTextEidt.getText(0, docsForTextEidt.getLength());
                                System.out.println(stringEdit);
                                now = stringEdit.indexOf("【" + stringSearch + "】", last);
                                docsForTextEidt.remove(now, stringSearch.length() + 2);
                                docsForTextEidt.insertString(now, stringSearch, null);
                                last = now + stringSearch.length();
                                System.out.println();
                            }
                        } catch (Exception ble) {
                        }
                        isAlreadySearch = false;
                        isAbleToShift = false;
                    }
                }
            }
        });
        btnShift.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Object obj = e.getSource();
                if (obj instanceof JButton)
                {
                    int now = 0;
                    int last = 0;
                    //获得文本框文本对象
                    docsForTextEidt = MyGUI.getTextEdit().getDocument();
                    stringSearch = textSearch.getText();
                    stringShift = textShift.getText();
                    if(stringSearch != ""&& isAbleToShift == true)
                    {
                        try
                        {
                            stringEdit = docsForTextEidt.getText(0,docsForTextEidt.getLength());
                            now=stringEdit.indexOf("【" + stringSearch + "】",last);
                            docsForTextEidt.remove(now,stringSearch.length()+2);
                            docsForTextEidt.insertString(now,stringShift,null);
                            last = now+stringShift.length();
                        }catch(Exception ble) {}
                    }
                }
            }
        });
        btnShiftAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Object obj = e.getSource();
                if (obj instanceof JButton)
                {
                    int now = 0;
                    int last = 0;
                    //获得文本框文本对象
                    docsForTextEidt = MyGUI.getTextEdit().getDocument();
                    stringSearch = textSearch.getText();
                    stringShift = textShift.getText();
                    if(stringSearch != ""&& isAbleToShift == true)
                    {
                        try
                        {
                            while (now!=-1)
                            {
                                stringEdit = docsForTextEidt.getText(0,docsForTextEidt.getLength());
                                System.out.println(stringEdit);
                                now=stringEdit.indexOf("【" + stringSearch + "】",last);
                                docsForTextEidt.remove(now,stringSearch.length()+2);
                                docsForTextEidt.insertString(now,stringShift,null);
                                last = now+stringShift.length();
                            }
                        }catch(Exception ble) {}
                    }
                }
            }
        });
    }
    public void setDialogSearchShiftVisible(boolean b)
    {
        this.dialogSearchShift.setVisible(b);
    }
}
