/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem8.tirt;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jskoczyl
 */
public class OpenSaveManager {

    private Component parentComponent;
    private JFileChooser chooser;
    private File lastSave_orNull;

    public OpenSaveManager(Component parentComponent, JFileChooser chooser) {
        this.parentComponent = parentComponent;
        this.chooser = chooser;
        lastSave_orNull = null;
    }

    public void save(File file, ArrayList<VNodeFrame> vNodeFrames, ArrayList<VideoLink> videoLinks) throws IOException {
        ArrayList<VNodeMemo> memos = toMemos(vNodeFrames);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(memos);
            out.writeObject(videoLinks);
        }
    }

    private ArrayList<VNodeMemo> toMemos(ArrayList<VNodeFrame> vNodeFrames) {
        ArrayList<VNodeMemo> memos = new ArrayList<VNodeMemo>();
        for (VNodeFrame n : vNodeFrames) {
            memos.add(n.getMemo());
        }
        return memos;
    }

    public ArrayList<VNodeMemo> readInto(File file, ArrayList<VideoLink> videoLinks) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            ArrayList<VNodeMemo> memos = (ArrayList<VNodeMemo>) in.readObject();
            ArrayList<VideoLink> readed_VideoLinks = (ArrayList<VideoLink>) in.readObject();
            videoLinks.clear();
            for (VideoLink l : readed_VideoLinks) {
                videoLinks.add(l);
            }
            return memos;
        }
    }

    public void gui_save(boolean saveAs, ArrayList<VNodeFrame> vNodeFrames, ArrayList<VideoLink> videoLinks) {
        File file = null;
        if (!saveAs) {
            file = lastSave_orNull != null ? lastSave_orNull : askFile("Save diagram");
        } else {
            file = askFile("Save diagram");
        }
        if (file == null) {
            return;
        }
        lastSave_orNull = file;
        try {
            save(file, vNodeFrames, videoLinks);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentComponent, "Exception messege: " + e.getMessage(), "Not able to save", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<VNodeMemo> gui_openInto_orNull(ArrayList<VideoLink> videoLinks) {
        File file = askFile("Open diagram");
        if (file == null) {
            return null;
        }
        lastSave_orNull = file;
        try {
            return readInto(file, videoLinks);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentComponent, "Exception messege: " + e.getMessage(), "Not able to open", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private File askFile(String aproveButtonText) {
        int answer = chooser.showDialog(parentComponent, aproveButtonText);
        if (answer == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    void exportXml(ArrayList<VNodeFrame> vNodeFrames, ArrayList<VideoLink> videoLinks) {
        File file = askFile("Export xml");
        if (file == null) {
            return;
        }
        try {
            JAXBContext context = JAXBContext.newInstance(DiagramXmlRoot.class);

            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            ArrayList<VNodeMemo> memos = toMemos(vNodeFrames);
            DiagramXmlRoot root = new DiagramXmlRoot(memos, videoLinks);

            m.marshal(root, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentComponent, "Exception messege: " + e.getMessage(), "Not able to export xml", JOptionPane.ERROR_MESSAGE);
        }
    }


}