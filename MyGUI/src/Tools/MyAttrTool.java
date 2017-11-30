package Tools;

import GUI.MyGUI_Choose;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.jar.Attributes;

public class MyAttrTool {
    public static MutableAttributeSet setFontAndColorForAttr(Font font,Color color)
    {
        if(color == null)color =Color.BLACK;
        MutableAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attr,font.getFamily());
        StyleConstants.setFontSize(attr,font.getSize());
        StyleConstants.setForeground(attr,color);
        return attr;
    }
}
