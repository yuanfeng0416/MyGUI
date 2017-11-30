package Tools;

import javax.swing.*;

public class LabelTool extends Thread
{
    private JLabel label;
    public LabelTool(JLabel label)
    {
        this.label = label;
    }
    public void run()
    {
        while (true)
        {
            try
            {
                label.setText("状态栏");
                sleep(3000);
            }catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }

    }
}
