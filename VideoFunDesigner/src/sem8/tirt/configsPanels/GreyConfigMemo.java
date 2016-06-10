/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem8.tirt.configsPanels;

import sem8.tirt.AbstractVNodeConfigMemo;

/**
 *
 * @author Jacek Skoczylas
 */
public class GreyConfigMemo extends SimpleFilterConfigMemo {
    
    public static final String CONFIG_NAME = "Grey Colors";
    public static final String CONFIG_DESCRIPTION = "Changes colors of image to gray scale.";
    private static final long serialVersionUID = 8734704261947259399L;

    public GreyConfigMemo() {
        super(CONFIG_NAME, "FrameEditorGreyscale");
    }
    
}
