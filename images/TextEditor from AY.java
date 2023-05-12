import java.awt.*;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.net.*;
import java.text.SimpleDateFormat;


public class TextEditor implements ActionListener, CaretListener {
    public JTextArea page;
    public JFrame frame;
    public String path ="";
    JTextField findText, replaceText;
    JLabel statusBar; 
    int linenum =1, word =1 , columnnum=1, linenumber, columnnumber;
    String text ="", words[];
    JPanel panel;

    public TextEditor(){
        frame= new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Editor");
        frame.pack();
        frame.setVisible(true);
        JMenuBar menuContainer = new JMenuBar();
        JMenu fileMenu= new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem newPage = new JMenuItem("New");

        
        newPage.setAccelerator(KeyStroke.getKeyStroke('N',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        fileMenu.add(newPage);

        newPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                page.setText("");
            }
        });



        JMenuItem open= new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));

        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                 JFileChooser fopen= new JFileChooser();
                 int option = fopen.showOpenDialog(frame);
                 int fileToOpen= option;
                 JFileChooser fileOpen = fopen;
                 if(fileToOpen== JFileChooser.APPROVE_OPTION){
                    page.setText("");
                    try{
                        Scanner scan = new Scanner(new FileReader(fileOpen.getSelectedFile().getPath()));
                        while(scan.hasNext()){
                            page.append(scan.nextLine()+"\n");
                        }
                        frame.setTitle(fileOpen.getSelectedFile().getPath() + "-Editor");
                        path= fileOpen.getSelectedFile().getPath();
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                 }
            }
        });

        fileMenu.add(open);






        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke('S',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                if(path.equals("")){
                    saveAsFile();
                }else{
                    saveFile();
                }
            }
            
        });

        fileMenu.add(save);

        
        JMenuItem newWindow = new JMenuItem("New Window");
        newWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.SHIFT_MASK | ActionEvent.CTRL_MASK));
        newWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                new TextEditor();
            }
        });
        
        fileMenu.add(newWindow);




        JMenuItem saveas = new JMenuItem("Save as");
        saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK));
        saveas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                saveAsFile();
            }
        });
        
        fileMenu.add(saveas);
        fileMenu.addSeparator();

        JMenuItem saveall = new JMenuItem("Save all");
        saveall.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        fileMenu.add(saveall);

        JMenuItem pagesetup = new JMenuItem("Page Setup");
        pagesetup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.SHIFT_MASK | ActionEvent.ALT_MASK));
        pagesetup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                PrinterJob pj = PrinterJob.getPrinterJob();
                pj.pageDialog(pj.defaultPage());
            }
        });
        
        fileMenu.add(pagesetup);


        JMenuItem print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                PrinterJob pj= PrinterJob.getPrinterJob();
                if(pj.printDialog()){
                    try {
                        pj.print();
                    } catch (Exception e) {
                     System.out.println(e);
                    }
                }
            }
        });
        fileMenu.add(print);




        JMenuItem closetab = new JMenuItem("Close Tab");
        saveall.setAccelerator(KeyStroke.getKeyStroke('W', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        fileMenu.add(closetab);

        JMenuItem closeWindow = new JMenuItem("Close Window");
        saveall.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        fileMenu.add(closeWindow);
        fileMenu.addSeparator();
        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.ALT_MASK));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to save? ", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
                if(option==0){
                    if(path== null){
                        saveAsFile();
                    }else{
                        saveFile();
                    }
                    System.exit(0);
                }else if(option==1){
                    System.exit(0);
                }

            }
            
        });
        fileMenu.add(exit);


        menuContainer.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");

        JMenuItem undo = new JMenuItem("Undo");
        
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        editMenu.add(undo);


        JMenuItem cut = new JMenuItem("Cut");
        
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                page.cut();
            }
        });
        editMenu.add(cut);




        JMenuItem copy = new JMenuItem("Copy");
        
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                page.copy();
            }
        });
        editMenu.add(copy);

        JMenuItem paste = new JMenuItem("Paste");
        
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                page.paste();
            }
        });
        editMenu.add(paste);


        JMenuItem delete = new JMenuItem("Delete");
        
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    String text = page.getSelectedText();
                    int n = page.getText().indexOf(text);
                    page.replaceRange("", n, n+text.length());
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,"No Text is selected");
                }
            }
        });
        editMenu.add(delete);

        JMenuItem findReplace = new JMenuItem("Find and Replace");
        findReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        findReplace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                 findAndReplace();
            }
        });
            editMenu.add(findReplace);


        JMenuItem findWithG = new JMenuItem("Find with Google");
        findWithG.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.SHIFT_MASK));
        findWithG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                 try{
                    Desktop.getDesktop().browse(URI.create("www.google.com"));
                 }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Not able to open url");
                 }
            }
        });
        editMenu.add(findWithG);
        JMenuItem goTo = new JMenuItem("Go to");
     goTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
     goTo.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
         goToLine();  
        }
    });
    editMenu.add(goTo);

    JMenuItem select = new JMenuItem("Select All");
    select.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
    select.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
         page.selectAll();
        }
    });
    editMenu.add(select);

    JMenuItem dateTime = new JMenuItem("Date/Time");
    dateTime.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
    dateTime.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
        //  String  date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //  page.setText(date);
        }
    });
    editMenu.add(dateTime);




        menuContainer.add(editMenu);


        JMenu formatMenu = new JMenu("Format");
        final JCheckBoxMenuItem wrap = new JCheckBoxMenuItem("Word wrap");
        wrap.addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent ae){
                boolean checkState = wrap.isSelected();
                if(checkState==true){
                    page.setLineWrap(true);
                    page.setWrapStyleWord(true);
                }else{
                    page.setLineWrap(false);
                    page.setWrapStyleWord(false);
                }
            }
        });
        formatMenu.add(wrap);


        JMenuItem fontItem = new JMenuItem("Font");
fontItem.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent ae) {
        showFontDialog();
    }
});
formatMenu.add(fontItem);

       
        



        menuContainer.add(formatMenu);

        JMenu viewMenu = new JMenu("View");
        JMenu zoom = new JMenu("Zoom");
        JMenuItem zoomIn = new JMenuItem("zoom in");
        zoomIn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                Font font = page.getFont();
                if(font.getSize()<200){
                    page.setFont(new Font(null, 0, font.getSize()+10));
                    frame.validate();
                }else{
                    JOptionPane.showMessageDialog(null, "Zoom in can not go above maximum zoom level");
                }
            }
        });



        zoom.add(zoomIn);

        JMenuItem zoomOut = new JMenuItem("Zoom out");
        zoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                Font font = page.getFont();
                if(font.getSize()>=20){
                    page.setFont(new Font (null, 0, font.getSize()-10));
                    frame.validate();
                }else{
                    JOptionPane.showMessageDialog(null, "Zoom in can not go above maximum zoom level");
                }
            }
        });
        zoom.add(zoomOut);

        JMenuItem defaultZoom = new JMenuItem("Restore default zoom");
        defaultZoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                page.setFont(new Font (null,0,20));
                frame.validate();
            }
        });
        zoom.add(defaultZoom);

        viewMenu.add(zoom);
        final JCheckBoxMenuItem status = new JCheckBoxMenuItem("Status Bar");
        status.addActionListener(new ActionListener (){
            @Override 
            public void actionPerformed(ActionEvent ae){
                boolean checkState = status.isSelected();
                if(checkState){
                    //give the status update value
                    updateStatus(linenum, columnnum, word,text);
                    panel.add(statusBar);
                    panel.repaint();
                }else{
                    panel.remove(statusBar);
                    panel.repaint();
                }
            }
        });
        viewMenu.add(status);
        menuContainer.add(viewMenu);


        menuContainer.add(viewMenu);
        frame.add(menuContainer, BorderLayout.NORTH);

        JPanel mainPage = new JPanel();
        mainPage.setLayout(new BorderLayout());
        page= new JTextArea();
        page.setPreferredSize(new Dimension(800,600));
        page.setFont(new Font("Serif", Font.PLAIN, 20));
        page.setFocusable(true);
        mainPage.add( new JScrollPane(page), BorderLayout.EAST);
        mainPage.add(page, BorderLayout.CENTER);
        frame.add(mainPage, BorderLayout.CENTER);
        statusBar= new JLabel("Line:" + linenumber + "Column" + columnnumber + "Characters:" + text.length() + "Words:" +word+ "");
        panel = new JPanel(new FlowLayout());
        frame.add(panel, BorderLayout.SOUTH);
        page.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce){
                JTextArea editArea = (JTextArea)ce.getSource();
                try{
                    text= page.getText();
                    words= text.split("");
                    word = words.length;
                    int caretpos = editArea.getCaretPosition();
                    linenum = editArea.getLineOfOffset(caretpos);
                    columnnum = caretpos- editArea.getLineStartOffset(linenum);
                    linenum +=1;
                    // this will set the line number
                }
                catch(Exception ex){}
                updateStatus(linenum, columnnum, word, text);
            }
        });
        frame.pack();
        frame.setVisible(true);
    }   

    //functions
    private void showFontDialog() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = env.getAvailableFontFamilyNames();

        JList<String> fontList = new JList<>(fontNames);
        fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fontList.setLayoutOrientation(JList.VERTICAL);
        fontList.setVisibleRowCount(-1);

        JScrollPane fontScrollPane = new JScrollPane(fontList);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Select a font:"));
        panel.add(fontScrollPane);

        int result = JOptionPane.showConfirmDialog(null, panel, "Select Font", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);



        if (result == JOptionPane.OK_OPTION) {
            String selectedFontName = fontList.getSelectedValue();
            int selectedFontStyle = Font.PLAIN;
            int selectedFontSize = 12;
            Font selectedFont = new Font(selectedFontName, selectedFontStyle, selectedFontSize);
            page.setFont(selectedFont);
        }
    }

    @Override
    public void caretUpdate(CaretEvent e){
        throw new UnsupportedOperationException("Not Supported Yet.");

    }
    private void updateStatus (int linenumber, int columnnumber, int word, String text){
        //create and add a status bar here
        statusBar.setText("Line:" + linenumber + " Column:" + columnnumber + "Characters:" + text.length() + "Words:" + word);
    }

    public void saveFile(){
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write(page.getText());
            out.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }

    public void saveAsFile(){
        JFileChooser fsave = new JFileChooser();
        int option = fsave.showSaveDialog(fsave);
        int fileToSave =option;
        JFileChooser fileSave =fsave;
        if(fileToSave ==JFileChooser.APPROVE_OPTION){
            try{
                BufferedWriter out = new BufferedWriter(new FileWriter(fileSave.getSelectedFile().getPath()));
                out.write(page.getText());
                out.close();
                frame.setTitle(fileSave.getSelectedFile().getPath() + "- Editor");
                path = fileSave.getSelectedFile().getPath();

            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,ex.getMessage());
            }
        }
    }

    public void findAndReplace(){
        JDialog findR = new JDialog();
        findR.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        findR.setLayout(new GridLayout(3,1));
        findR.setSize(400, 150);
        findR.setLocation(500, 300);
        replaceText = new JTextField("Enter text to replace", 20);
        findText = new JTextField("Enter text to find", 20);
        findR.setTitle("Find and replace");
        final JButton find = new JButton("Find");
        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                try{
                    String search = findText.getText();
                    int n = page.getText().indexOf(search);
                    page.select(n, n+search.length());
                }catch(Exception a){
                    JOptionPane.showMessageDialog(null, "No text is selected");
                }
            }
        });

    final JButton replace = new JButton("Replace");
    replace.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae){
            try{
                String search = findText.getText();
                int n = page.getText().indexOf(search);
                String replacing = replaceText.getText();
                page.replaceRange(replacing, n, n+ search.length());
            }catch(Exception e1){
                JOptionPane.showMessageDialog(null,"No text is selected");
            }
        }

    });
    JPanel panel = new JPanel();
    findR.add(findText);
    findR.add(replaceText);
    panel.add(find);
    panel.add(replace);
    findR.add(panel);
    findR.setVisible(true);
    }

    public void goToLine(){
        final JDialog go2 = new JDialog(frame);
        go2.setSize(200, 100);
        go2.setLocation(200,300);
        JPanel pa = new JPanel(new BorderLayout());
        JLabel lb = new JLabel("Enter line number");
        pa.add(lb, BorderLayout.NORTH);
        final JTextField tf = new JTextField(10);
        pa.add(tf, BorderLayout.CENTER);
        JButton bn = new JButton("Go to line");
        bn.setSize(5, 5);
        bn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                try{
                    String line = tf.getText();
                    page.setCaretPosition(page.getDocument().getDefaultRootElement().getElement(Integer.parseInt(line)-1).getStartOffset());
                    page.requestFocusInWindow();
                    go2.dispose();
                }catch(Exception e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });
        pa.add(bn, BorderLayout.SOUTH);
        go2.add(pa);
        go2.setVisible(true);
    }


    public void actionPerformed(ActionEvent ae){

    }

        public static void main(String[] args){
            TextEditor ex = new TextEditor();
        }
}