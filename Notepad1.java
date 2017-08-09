package rishu;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.awt.datatransfer.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class Notepad1 extends JPanel
{
    JTextArea jta = new JTextArea("", 24, 40);
    JScrollPane jsp = new JScrollPane(jta);
    
    JMenuBar jmb = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenu edit = new JMenu("Edit");
    JMenu search = new JMenu("Search");
    JMenu help = new JMenu("Help");
    JMenuItem jmi;  
    
    JToolBar toolBar=new JToolBar();
    Clipboard clipbd = getToolkit().getSystemClipboard();

    class newL implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                jta.setDocument(new PlainDocument());
            }
        }
      
    class openL implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showDialog(Notepad1.this, "Open file");
                if(returnVal == JFileChooser.APPROVE_OPTION)
                {
                    String file = fc.getSelectedFile().getPath();
                    if(file == null)
                    {
                        return;
                    }
                    try
                    {
                        Reader in = new FileReader(file);
                        char[] buff = new char[4096];
                        int nch;
                        while((nch = in.read(buff, 0, buff.length)) != -1)
                        {
                            jta.setDocument(new PlainDocument());
                            jta.append(new String(buff, 0, nch));
                        }
                    }	
                    catch (IOException io)
                    {
                        System.err.println("IOException: " + io.getMessage());
                    }
                }
                else
                {
                    return;
                }
            }
        }

        class saveL implements ActionListener
            {
                public void actionPerformed(ActionEvent e)
                    {
                        JFileChooser fc = new JFileChooser();
                        int returnVal = fc.showSaveDialog(Notepad1.this);
                        if(returnVal == JFileChooser.APPROVE_OPTION)
                            {
                                String savefile = fc.getSelectedFile().getPath();
                                if(savefile == null)
                                    {
                                            return;
                                    }
                                else
                                    {
                                            String docToSave = jta.getText();
                                            if(docToSave != null)
                                                {
                                                        FileOutputStream fstrm = null;
                                                        BufferedOutputStream ostrm = null;
                                                        try
                                                            {
                                                                    fstrm = new FileOutputStream(savefile);
                                                                    ostrm = new BufferedOutputStream(fstrm);
                                                                    byte[] bytes = null;
                                                                    try
                                                                        {
                                                                                bytes = docToSave.getBytes();
                                                                        }
                                                        catch(Exception e1)
                                                                {
                                                                        e1.printStackTrace();
                                                                }
                                                ostrm.write(bytes);
                                }
                        catch(IOException io)
                                {
                                        System.err.println("IOException: " +
                                        io.getMessage());
                                }
                        finally
                        {
                                try
                                {
                                    ostrm.flush();
                                    fstrm.close();
                                    ostrm.close();
                                }
                                catch(IOException ioe)
                                {
                                    System.err.println("IOException: " +
                                            ioe.getMessage());
                                }
                            }
                        }
                    }
                }
                else
                {
                    return;
                }
            }
        }

        class exitL implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        }

        class copyL implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                String selection = jta.getSelectedText();
                StringSelection clipString = new StringSelection(selection);
                clipbd.setContents(clipString, clipString);
            }
        }

        class cutL implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                String selection = jta.getSelectedText();
                StringSelection clipString = new StringSelection(selection);
                clipbd.setContents(clipString, clipString);
                jta.replaceRange("", jta.getSelectionStart(),
                        jta.getSelectionEnd());
            }
        }

        class pasteL implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                Transferable clipData = clipbd.getContents(Notepad1.this);
                try
                {
                    String clipString =
                            (String)clipData.getTransferData(
                            DataFlavor.stringFlavor);
                    jta.replaceRange(clipString,
                            jta.getSelectionStart(), jta.getSelectionEnd());
                }
                catch(Exception ex)
                {
                }
            }
        }

        class deleteL implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                String selection = jta.getSelectedText();
                jta.replaceRange("", jta.getSelectionStart(),
                        jta.getSelectionEnd());
            }
        }

        class selectAllL implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                jta.selectAll();
            }
        }

        class findL implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                String find = "";
                find = JOptionPane.showInputDialog(
                        "Find what: ");
            }
        }

        class findNextL implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
            }
        }

        class aboutL implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null,
                    "Notepad Developed By rishabh");
            }
        }

        public Notepad1()
      { 
          setLayout(new BorderLayout());    

              file.add(jmi = new JMenuItem("New",KeyEvent.VK_N));
              jmi.addActionListener(new newL());
              file.add(jmi = new JMenuItem("Open",KeyEvent.VK_O));
              jmi.addActionListener(new openL());
              file.addSeparator();
              file.add(jmi = new JMenuItem("Save",KeyEvent.VK_A));
              jmi.addActionListener(new saveL());
              file.add(jmi = new JMenuItem("Close",KeyEvent.VK_L));
              jmi.addActionListener(new exitL());
              file.setMnemonic(KeyEvent.VK_F);
              jmb.add(file);

              edit.add(jmi=new JMenuItem("Copy",KeyEvent.VK_C));
              jmi.addActionListener(new copyL());
              edit.add(jmi=new JMenuItem("Cut",KeyEvent.VK_U));
              jmi.addActionListener(new cutL());
              edit.add(jmi=new JMenuItem("Paste",KeyEvent.VK_S));
              jmi.addActionListener(new pasteL());
              edit.add(jmi=new JMenuItem("Delete",KeyEvent.VK_E));
              jmi.addActionListener(new deleteL());
              edit.addSeparator();
              edit.add(jmi=new JMenuItem("SelectAll",KeyEvent.VK_S));
              jmi.addActionListener(new selectAllL());
              edit.setMnemonic(KeyEvent.VK_E);
              jmb.add(edit);
                
              search.add(jmi=new JMenuItem("Find",KeyEvent.VK_F));
              jmi.addActionListener(new findL());
              search.add(jmi=new JMenuItem("FindNext",KeyEvent.VK_T));
              jmi.addActionListener(new findNextL());
              search.setMnemonic(KeyEvent.VK_S);
              jmb.add(search);

              help.add(jmi=new JMenuItem("About",KeyEvent.VK_A));
              jmi.addActionListener(new aboutL());
              help.setMnemonic(KeyEvent.VK_H);
              jmb.add(help);

              add(jmb, BorderLayout.NORTH);
              toolBar.setFloatable(false);
              addButtons(toolBar);
              
              add(toolBar, BorderLayout.CENTER);
              add(jsp, BorderLayout.SOUTH);
            
        }

        protected void addButtons(JToolBar toolBar)
    {
        JButton button = new JButton();
        button.setToolTipText("Create a new document");
        button.addActionListener(new newL());
        toolBar.add(button);

        JButton button1 = new JButton();
        button1.setToolTipText("Open a document");
        button1.addActionListener(new openL());
        toolBar.add(button1);

        JButton button2 = new JButton(new ImageIcon("C:/Users/Rishabh chawla/Desktop/pic.png"));
        button2.setToolTipText("Save the document");
        button2.addActionListener(new saveL());
        toolBar.add(button2);

        JButton button3 = new JButton(new ImageIcon("images/copy.gif"));
        button3.setToolTipText("Copy selected text");
        button3.addActionListener(new copyL());
        toolBar.add(button3);

        JButton button4 = new JButton(new ImageIcon("images/cut.gif"));
        button4.setToolTipText("Cut selected text");
        button4.addActionListener(new cutL());
        toolBar.add(button4);

        JButton button5 = new JButton(new ImageIcon("images/paste.gif"));
        button5.setToolTipText("Paste clipboard");
        button5.addActionListener(new pasteL());
        toolBar.add(button5);

        JButton button6 = new JButton(new ImageIcon("images/about.gif"));
        button6.setToolTipText("About application");
        button6.addActionListener(new aboutL());
        toolBar.add(button6);

    }
    
        public static void main(String args[])
        {
            JFrame f = new JFrame();
            Notepad1 notepad     = new Notepad1();
            f.setTitle("rishabh Creation Notepad");
            f.setBackground(Color.lightGray);
            f.getContentPane().add(notepad,BorderLayout.CENTER);
            f.setSize(800, 500);
            f.setVisible(true);
        }
}
