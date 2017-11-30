package Tools;
import javax.swing.*;
import java.io.*;

public class ItemTool {
    public static void myWriteObject(JTextPane textPane, File file)
    {
        try
        {
            if(!file.exists()) {
                file.createNewFile();
            }

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

            oos.writeObject(textPane);
            oos.flush();
            oos.close();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static JTextPane myReadObject(File file)
    {
        JTextPane textPane = new JTextPane();
        try
        {
            if(!file.exists()) {
                file.createNewFile();
            }

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            textPane = (JTextPane)ois.readObject();
            ois.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return textPane;
    }
}
