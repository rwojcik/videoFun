/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem8.tirt;

import java.awt.AWTEvent;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import sem8.tirt.configsPanels.*;
import sem8.tirt.configsPanels.InvertColorsConfigMemo;

/**
 * Main class to run. It's graphic user interface window.
 * 
 * @author Jacek Skoczylas
 */
public class VideoFunDesigner extends javax.swing.JFrame {

    public static final Class<? extends AbstractVNodeConfigMemo>[] VNODES_CONFIGS = new Class[]{
        CameraSrcConfigMemo.class,
        InvertColorsConfigMemo.class,
        BlurConfigMemo.class,
        CirclesConfigMemo.class,
        DerivativeConfigMemo.class,
        GreyConfigMemo.class,
        SepiaConfigMemo.class,
        InvertColorsConfigMemo.class,
        FramesCounterConfigMemo.class,
        ResizeConfigMemo.class,
        ShowInWindowConfigMemo.class
    };

    public static final int INTERNAL_FRAME_OFFSET = 30;
    public static final String CONFIG_DEFAUTL_DESC = "Click buttons above to add video block to diagram. Move mose over button to see here more information about adding video block.";

    private int vnodesCounter;
    private LinksDesktopPane linksDesktopPane;
    private ArrayList<VNodeFrame> vNodeFrames;
    private ArrayList<VideoLink> videoLinks;
    private OpenSaveManager openSaveManager;

    /**
     * Creates new form ViedoFunDesigner
     */
    public VideoFunDesigner() {
        System.out.println(new File("").getAbsolutePath());
        linksDesktopPane = new LinksDesktopPane();
        vNodeFrames = new ArrayList<VNodeFrame>();
        videoLinks = new ArrayList<VideoLink>();
        openSaveManager = new OpenSaveManager(this, new JFileChooser());
        initComponents();
        linksDesktopPane.setParent(this);
        setDescText(CONFIG_DEFAUTL_DESC);

        newDiagram();
    }

    /**
     * Removes current diagram and creates new diagram.
     */
    private void newDiagram() {
        jDesktopPane1.removeAll();
        vNodeFrames.clear();
        videoLinks.clear();
        addVNode(VNODES_CONFIGS[0]);
        addVNode(VNODES_CONFIGS[VNODES_CONFIGS.length - 1], 200, -30);
        videoLinks.add(new VideoLink(0, 0, 1, 0));
        repaint();
    }

    /**
     * Add buttons to <code>jPanelButtons</code>.
     */
    private void addButtons() {
        for (Class<? extends AbstractVNodeConfigMemo> c : VNODES_CONFIGS) {
            jPanelButtons.add(new ConfigButton(c));
        }
    }

    public ArrayList<VideoLink> getVideoLinks() {
        return videoLinks;
    }

    public VNodeFrame getVNodeFrame(int n) {
        return vNodeFrames.get(n);
    }

    protected void addVNode(Class<? extends AbstractVNodeConfigMemo> configMemoClass) {
        addVNode(configMemoClass, 0, 0);
    }

    protected void addVNode(Class<? extends AbstractVNodeConfigMemo> configMemoClass, int dx, int dy) {
        try {
            AbstractVNodeConfigMemo configMemo = configMemoClass.newInstance();
            VNodeMemo vNodeMemo = new VNodeMemo(vNodeFrames.size(), configMemo);
            int i = jDesktopPane1.getComponentCount() + 1;
            vNodeMemo.setLocation(newPositionOfVNode(i, dx, dy));
            final VNodeFrame vNodeFrame = new VNodeFrame(vNodeMemo);
            vNodeFrame.setParent(this);
            vNodeFrames.add(vNodeFrame);
            putToWindowVNode(vNodeFrame);
        } catch (InstantiationException ex) {
            Logger.getLogger(VideoFunDesigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(VideoFunDesigner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Point newPositionOfVNode(int i, int dx, int dy) {
        int ix = i;
        int iy = i;
        while (iy > 8) {
            iy -= 8;
            ix++;
        }
        return new Point(ix * INTERNAL_FRAME_OFFSET + dx, iy * INTERNAL_FRAME_OFFSET + dy);
    }

    public void putToWindowVNode(VNodeFrame node) {
        node.setVisible(true);
        jDesktopPane1.add(node);
    }

    public void addVideoLink(VideoLink link) {
        videoLinks.add(link);
        repaint();
    }

    public void deleteAllLinksWithInLink(String inLink) {
        ArrayList<VideoLink> toDelete = new ArrayList<VideoLink>();
        for (VideoLink l : videoLinks) {
            if (l.cotainsUrl(inLink)) {
                toDelete.add(l);
            }
        }
        for (VideoLink l : toDelete) {
            videoLinks.remove(l);
        }
        repaint();
        System.out.println("videoLinks.size() = " + videoLinks.size());
    }

    public void setDescText(String desc) {
        jLabelConfigDesc.setText(desc);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDesktopPane1 = linksDesktopPane;
        jPanelButtons = new javax.swing.JPanel();
        jLabelConfigDesc = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Video Fun Designer");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jDesktopPane1.setDragMode(javax.swing.JDesktopPane.OUTLINE_DRAG_MODE);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 284, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jDesktopPane1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jPanelButtons.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelButtonsMouseExited(evt);
            }
        });
        jPanelButtons.setLayout(new javax.swing.BoxLayout(jPanelButtons, javax.swing.BoxLayout.LINE_AXIS));
        addButtons();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanelButtons, gridBagConstraints);

        jLabelConfigDesc.setText("jLabel1");
        jPanel1.add(jLabelConfigDesc, new java.awt.GridBagConstraints());

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("New diagram");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Open diagram");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Save diagram");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Save diagram as");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator1);

        jMenuItem5.setText("Export diagram do xml");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Export to cmd in TCP mode");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("Export to cmd in UDP mode");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);
        jMenu1.add(jSeparator2);

        jMenuItem8.setText("Close application");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");

        jMenuItem9.setText("About");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanelButtonsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelButtonsMouseExited
        setDescText(CONFIG_DEFAUTL_DESC);
    }//GEN-LAST:event_jPanelButtonsMouseExited

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        saveAs();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void saveAs() {
        openSaveManager.gui_save(true, vNodeFrames, videoLinks);
    }

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        ArrayList<VNodeMemo> memos = openSaveManager.gui_openInto_orNull(videoLinks);
        if (memos != null) {
            jDesktopPane1.removeAll();
            vNodeFrames.clear();
            for (VNodeMemo memo : memos) {
                final VNodeFrame vNodeFrame = new VNodeFrame(memo);
                vNodeFrames.add(vNodeFrame);
                vNodeFrame.setParent(this);
                putToWindowVNode(vNodeFrame);
            }
            repaint();
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        openSaveManager.gui_save(false, vNodeFrames, videoLinks);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Create new diagram?", "New diagram", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
            newDiagram();
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        openSaveManager.exportXml(vNodeFrames, videoLinks);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        openSaveManager.exportCmd(vNodeFrames, videoLinks, true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        openSaveManager.exportCmd(vNodeFrames, videoLinks, false);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        closingApplication();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closingApplication();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        aboutShow();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VideoFunDesigner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VideoFunDesigner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VideoFunDesigner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VideoFunDesigner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VideoFunDesigner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabelConfigDesc;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    // End of variables declaration//GEN-END:variables

    private void closingApplication() {
        int answer = JOptionPane.showConfirmDialog(this, "Do you want to save diagram before close?", "Close confirm", JOptionPane.YES_NO_CANCEL_OPTION);
        if(answer == JOptionPane.YES_OPTION) {
            saveAs();
        }
        if(answer == JOptionPane.YES_OPTION || answer == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    private void aboutShow() {
        JOptionPane.showMessageDialog(this, "by Jacek Skoczylas & Rafał Wójcik", "Video Fun Desginer", JOptionPane.INFORMATION_MESSAGE);
    }

    private class ConfigButton extends JButton {

        Class<? extends AbstractVNodeConfigMemo> configMemoClass;
        String desc;

        public ConfigButton(Class<? extends AbstractVNodeConfigMemo> configMemoClass) {
            super(getConfigName(configMemoClass));
            this.configMemoClass = configMemoClass;
            desc = getConfigDesc(configMemoClass);
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addVNode(configMemoClass);
                }
            });
        }

        String getConfigDesc(Class<? extends AbstractVNodeConfigMemo> c) {
            try {
                Field field = c.getField(AbstractVNodeConfigMemo.CONFIG_DESCRIPTION_FIELD_NAME);
                return field.get(null).toString();
            } catch (Exception ex) {
                return c.getSimpleName();
            }
        }

        @Override
        protected void processMouseEvent(MouseEvent e) {
            super.processMouseEvent(e);
            final int id = e.getID();
            if (id == MouseEvent.MOUSE_EXITED) {
                setDescText(CONFIG_DEFAUTL_DESC);
            } else if (id == MouseEvent.MOUSE_ENTERED || id == MouseEvent.MOUSE_MOVED) {
                setDescText(desc);
            }
        }
    }

    static String getConfigName(Class c) {
        try {
            Field field = c.getField(AbstractVNodeConfigMemo.CONFIG_NAME_FIELD_NAME);
            return field.get(null).toString();
        } catch (Exception ex) {
            return c.getSimpleName();
        }
    }

}
