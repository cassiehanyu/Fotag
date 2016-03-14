import javax.imageio.ImageIO;
import javax.swing.*;

import Model.Model;
import View.View;
import View.MyToolBar;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Created by cassiehanyu on 2016-03-04.
 */
public class Fotag extends JFrame{
    private Model model;
    private View view;
    private MyToolBar myToolBar;
    private JScrollPane scrollPane;

    public Fotag(){
        model = new Model();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Fotag.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                view = new View(model);
                myToolBar = new MyToolBar(model);

//                try {
//                    Fotag.this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("pic/chalkboard-black.jpg")))));
//                }catch (Exception e){
//
//                }

                Container container = Fotag.this.getContentPane();
                container.setLayout(new BorderLayout());

                container.add(myToolBar, BorderLayout.NORTH);

                scrollPane = new JScrollPane(view);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                container.add(scrollPane, BorderLayout.CENTER);

//                Fotag.this.setSize(new Dimension(645,655));
                Fotag.this.setMinimumSize(new Dimension(425,430));
                Fotag.this.setSize(new Dimension(645,655));

                Fotag.this.setVisible(true);


                Fotag.this.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                        model.loadHistory();
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.out.println("window is closing");
                        model.saveHistory();
                    }

                });

                Fotag.this.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        super.componentResized(e);
                    }
                });

            }
        });

    }


    public static void main(String [ ] args)

    {
        Fotag fotag = new Fotag();
    }
}
