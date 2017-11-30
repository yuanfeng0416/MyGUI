package GUI;

import Tools.MyAttrTool;
import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyGUI_Choose
{
        private static final Font fontBtn = new Font("黑体",Font.PLAIN,25);
        private static final Font fontLabel = new Font("黑体",Font.PLAIN,20);
        private static final Font fontComboBox = new Font("黑体",Font.PLAIN,20);
        private static final String[] str_name = { "黑体", "微软雅黑", "宋体", "仿宋体", "楷体"};
        private static final String[] str_Size = { "8", "12", "14", "18", "25", "32", "42", "56", "64", "72" };
        private Color color;
        private JDialog dialogChoose;
        private Container container;
        private Box box;
        private Box boxTop;
        private Box boxBottom;
        private JComboBox fontName;
        private JComboBox fontSize;
        private JButton btnColor;
        private JColorChooser colorChooser;
        private JButton btnComfirm;
        private JButton btnCancel;
        public MyGUI_Choose(JFrame f,String s,boolean b)
        {
              UIManager.put("Button.font",fontBtn);
              UIManager.put("Label.font",fontLabel);
              UIManager.put("ComboBox.font",fontComboBox);
              this.dialogChoose = new JDialog(f,s,b);
              dialogChoose.setSize(1000,200);
              //初始化容器
              this.container = new Container();
              container=dialogChoose.getContentPane();
              container.setLayout(new BorderLayout());
              //初始化上下BOX
              this.box = Box.createVerticalBox();//竖
              this.boxTop = Box.createHorizontalBox();//横
              this.boxBottom = Box.createHorizontalBox();
              container.add(box);
              box.add(boxTop);
              box.add(Box.createVerticalStrut(8));
              box.add(boxBottom);
              //初始化字体大小颜色选择框
              this.fontName = new JComboBox(str_name); // 字体名称
              this.fontSize = new JComboBox(str_Size); // 字号
              fontSize.setSelectedIndex(4);
              this.btnColor = new JButton(); // 颜色
              btnColor.setText("       ");
              btnColor.setBackground(Color.BLACK);
              this.colorChooser = new JColorChooser();
              boxTop.add(new JLabel("字体：")); // 加入标签
              boxTop.add(fontName); // 加入组件
              boxTop.add(Box.createHorizontalStrut(8)); // 间距
              boxTop.add(new JLabel("大小：")); // 加入标签
              boxTop.add(fontSize); // 加入组件
              boxTop.add(Box.createHorizontalStrut(8)); // 间距
              boxTop.add(new JLabel("颜色：")); // 加入标签
              boxTop.add(btnColor); // 加入组件
              boxTop.add(Box.createHorizontalStrut(8)); // 间距
              //初始确定取消按钮
              btnComfirm = new JButton("确定");
              btnCancel = new JButton("取消");
              boxBottom.add(btnComfirm);
              boxBottom.add(Box.createHorizontalStrut(30));
              boxBottom.add(btnCancel);
              //初始化事件响应
              myGUI_ChooseEvent();
        }

        private void myGUI_ChooseEvent()
        {
            btnComfirm.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object obj = e.getSource();
                    if (obj instanceof JButton)
                    {
                        dialogChoose.dispose();
                        Font font = new Font((String) fontName.getSelectedItem(),Font.PLAIN,Integer.parseInt((String)fontSize.getSelectedItem()));
                        MutableAttributeSet attr = MyAttrTool.setFontAndColorForAttr(font,color);
                        MyGUI.setAttrFortextEdit(attr);
                    }
                }
            });
            btnCancel.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object obj = e.getSource();
                    if (obj instanceof JButton)
                    {
                        dialogChoose.dispose();
                    }
                }
            });
            btnColor.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object obj = e.getSource();
                    if (obj instanceof JButton)
                    {
                        color = JColorChooser.showDialog(MyGUI.getFrame(),"颜色",Color.BLACK);
                        btnColor.setBackground(color);
                    }
                }
            });
        }

        public void setDialogChooseVisible(boolean b)
        {
            this.dialogChoose.setVisible(b);
        }
}
