package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import Tools.ItemTool;
import Tools.LabelTool;

public class MyGUI
{
    //静态常量区
    private static final Font fontBar = new Font("黑体",Font.PLAIN,25);
    private static final Font fontState = new Font("黑体",Font.PLAIN,21);
    private static final FileNameExtensionFilter filter = new FileNameExtensionFilter("图片(jpg,gif,png)","gif","jpg","png");
    private static File file = null;
    //组件定义区
    private FileDialog load ;
    private FileDialog save ;
    private JFileChooser chooseIamge;
    private Font fontEdit;
    private Image myImage;
    private static JFrame frame;
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem itemNewOfFile;
    private JMenuItem itemOpenOfFile;
    private JMenuItem itemWriteOfFile;
    private JMenuItem itemOtherWriteOfFile;
    private JMenuItem itemNetReadAndWriteOfFile;
    private JMenu menuFunction;
    private JMenuItem itemImageOfFunction;
    private JMenuItem itemSearchAndShiftOfFunction;
    private JMenuItem itemFontAndColorOfFunction;
    private JMenu menuHelp;
    private JMenuItem itemUseOfHelp;
    private JMenuItem itemOurInfoOfHelp;
    private JPanel panelEdit;
    private static JScrollPane scrollPaneEdit;
    private static JTextPane textEdit;
    private JPanel panelState;
    private static JLabel labelState;
    private MyGUI_Choose myGUI_choose;
    private MyGUI_Search_Shift myGUI_search_shift;
    private MyGUI_SendAndReceive myGUI_sendAndReceive;

    public MyGUI()
    {
        //设置字体
        UIManager.put("Menu.font",fontBar);
        UIManager.put("MenuItem.font",fontBar);
        UIManager.put("Button.font", fontBar);
        UIManager.put("Label.font", fontBar);
        UIManager.put("ColorChooser.font",fontBar);
        Toolkit toolkit =Toolkit.getDefaultToolkit();
        myImage=toolkit.createImage("./Image/MyIcon.png");
        //初始化窗体
        this.frame = new JFrame();
        frame = new JFrame("My编辑器-未命名");
        frame.setIconImage(myImage);
        frame.setLayout(new BorderLayout());
        frame.setSize(1000, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //初始化菜单栏
        this.menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        //初始化菜单栏文字
        this.menuFile= new JMenu("文件");
        this.itemNewOfFile = new JMenuItem("新建");
        this.itemOpenOfFile= new JMenuItem("打开");
        this.itemWriteOfFile= new JMenuItem("保存");
        this.itemOtherWriteOfFile = new JMenuItem("另存为");
        this.itemNetReadAndWriteOfFile = new JMenuItem("云端存储");
        //
        this.menuFunction = new JMenu("功能");
        this.itemImageOfFunction = new JMenuItem("插入图片");
        this.itemSearchAndShiftOfFunction = new JMenuItem("查找/替换文字");
        this.itemFontAndColorOfFunction = new JMenuItem("文字设置");
        //
        this.menuHelp = new JMenu("帮助");
        this.itemUseOfHelp= new JMenuItem("使用");
        this.itemOurInfoOfHelp = new JMenuItem("关于我们");
        //初始化菜单栏添加菜单子项
        menuBar.add(menuFile);
        menuFile.add(itemNewOfFile);
        menuFile.add(itemOpenOfFile);
        menuFile.add(itemWriteOfFile);
        menuFile.add(itemOtherWriteOfFile);
        menuFile.add(itemNetReadAndWriteOfFile);
        //
        menuBar.add(menuFunction);
        menuFunction.add(itemImageOfFunction);
        menuFunction.add(itemFontAndColorOfFunction);
        menuFunction.add(itemSearchAndShiftOfFunction);
        //
        menuBar.add(menuHelp);
        menuHelp.add(itemUseOfHelp);
        menuHelp.add(itemOurInfoOfHelp);
        //初始化编辑容器
        this.panelEdit = new JPanel();
        panelEdit.setLayout(new BorderLayout());
        frame.add(panelEdit,BorderLayout.CENTER);
        //初始化滚动容器
        scrollPaneEdit = new JScrollPane();
        //初始化文本编辑框
        this.textEdit = new JTextPane();
        fontEdit = new Font("黑体", Font.BOLD, 25);
        textEdit.setForeground(Color.BLACK);
        textEdit.setFont(fontEdit);
        //初始化编辑容器嵌套
        scrollPaneEdit.setViewportView(textEdit);
        panelEdit.add(scrollPaneEdit,BorderLayout.CENTER);
        //初始化状态栏容器
        panelState = new JPanel();
        panelState.setLayout(new BorderLayout());
        frame.add(panelState,BorderLayout.SOUTH);
        //初始化编辑容器嵌套
        labelState = new JLabel("状态栏");
        labelState.setFont(fontState);
        panelState.add(labelState,BorderLayout.CENTER);
        new LabelTool(labelState).start();
        //初始化事件响应
        myEvent();
        //初始化文件打开保存对话框
        load = new FileDialog(frame,"读取文件",FileDialog.LOAD);
        load.setIconImage(myImage);
        save= new FileDialog(frame,"保存文件",FileDialog.SAVE);
        save.setIconImage(myImage);
        chooseIamge = new JFileChooser();
        chooseIamge.setDialogTitle("图片选择");
        chooseIamge.setFileFilter(filter);
        //创建选择字体大小颜色窗口
        this.myGUI_choose = new MyGUI_Choose(frame,"属性编辑",true);
        this.myGUI_search_shift = new MyGUI_Search_Shift(frame,"查找替换",false);
        this.myGUI_sendAndReceive = new MyGUI_SendAndReceive(frame,"云存储文件",true);
        //创建完成显示窗口界面
        frame.setVisible(true);
    }
    private void setSaveFile()
    {
        save.setVisible(true);
        String dir = save.getDirectory();
        String lf = save.getFile();
        if(dir==null || lf==null)
            return;
        file = new File(dir,lf);
    }
    //全部事件
    private void myEvent()
    {
        menuFileEvent();
        menuFunctionEvent();
        MenuHelpEvent();
    }
    //菜单事件响应处理
    private void menuFileEvent()
    {
        itemNewOfFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //重新加载textEdit
                frame.setTitle("my编辑器-未命名");
                textEdit.setText(null);
                //初始化字体大小颜色
                textEdit.setForeground(Color.BLACK);
                textEdit.setFont(fontEdit);
                file = null;
                labelState.setText("新建成功");
            }
        });
        itemOpenOfFile.addActionListener(new ActionListener() {
            @Override
            public void  actionPerformed(ActionEvent e) {
                Object obj = e.getSource();
                if (obj instanceof JMenuItem)
                {
                    load.setVisible(true);
                    String dir = load.getDirectory();
                    String lf = load.getFile();
                    if (dir == null || lf == null)
                    {
                        labelState.setText("打开异常");
                        return;
                    }
                    file = new File(dir, lf);
                    frame.setTitle("my编辑器-"+file.getName());
                    textEdit = ItemTool.myReadObject(file);
                    scrollPaneEdit.setViewportView(textEdit);
                    labelState.setText("打开成功");
                }
            }
        });
        itemWriteOfFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = e.getSource();
                if (obj instanceof JMenuItem)
                {
                    if(file == null)
                    {
                        setSaveFile();
                        if(file != null)
                        {
                            ItemTool.myWriteObject(textEdit,file);
                            frame.setTitle("my编辑器-"+file.getName());
                            labelState.setText("保存成功");
                        }else labelState.setText("保存异常");
                    }else
                    {
                        ItemTool.myWriteObject(textEdit,file);
                        labelState.setText("保存成功");
                    }

                }
            }
        });
        itemOtherWriteOfFile.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Object obj = e.getSource();
                if (obj instanceof JMenuItem)
                {
                   setSaveFile();
                   if(file != null)
                   {
                       ItemTool.myWriteObject(textEdit,file);
                       labelState.setText("另存为成功");
                   }
                   else labelState.setText("另存为异常");

                }
            }
        });
        itemNetReadAndWriteOfFile.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Object obj = e.getSource();
                if (obj instanceof JMenuItem)
                {
                    myGUI_sendAndReceive.setDialogNetVisible(true);
                }
            }
        });
    }
    //功能事件响应处理
    private void menuFunctionEvent()
    {
        itemFontAndColorOfFunction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Object obj = e.getSource();
                if (obj instanceof JMenuItem)
                {
                    myGUI_choose.setDialogChooseVisible(true);
                }
            }
        });
        itemImageOfFunction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = e.getSource();
                if (obj instanceof JMenuItem)
                {
                    try
                    {
                        int position = textEdit.getCaretPosition();
                        System.out.println(position);
                        chooseIamge.showOpenDialog(frame);//选择图片
                        textEdit.setCaretPosition(position); // 设置插入位置
                        textEdit.insertIcon(new ImageIcon(chooseIamge.getSelectedFile().getPath()));
                        textEdit.requestFocus();// 插入图片
                    }catch (NullPointerException npe)
                    {
                        labelState.setText("未选择图片");
                    }
                }
            }
        });
        itemSearchAndShiftOfFunction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = e.getSource();
                if (obj instanceof JMenuItem)
                {
                    myGUI_search_shift.setDialogSearchShiftVisible(true);
                }
            }
        });
    }
    //帮助事件响应处理
    private void MenuHelpEvent()
    {
        itemUseOfHelp.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = e.getSource();
                if (obj instanceof JMenuItem)
                {

                }
            }
        });
    }
    //静态变量获取区域
    public static void setAttrFortextEdit(MutableAttributeSet attr)
    {
        textEdit.setCharacterAttributes(attr,true);
    }
    public static JFrame getFrame()
    {
    return frame;
    }
    public static JTextPane getTextEdit()
    {
    return textEdit;
    }
    public static void setTextEditForscrollPaneEdit(JTextPane textPane)
    {
            textEdit = textPane;
            scrollPaneEdit.setViewportView(textEdit);
    }
    public static void main(String[] args)
    {
        MyGUI myGUI = new MyGUI();
    }
}

