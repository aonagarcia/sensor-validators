/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.igu;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 *
 * @author javier
 */
public class CInternalFrameListener implements InternalFrameListener {

    stateObserver so;
    String windowName;

    public CInternalFrameListener(stateObserver s, String wn) {
        this.so = s;
        windowName = wn;
    }

    public void internalFrameOpened(InternalFrameEvent e) {
        
    }

    public void internalFrameClosing(InternalFrameEvent e) {
    }

    public void internalFrameClosed(InternalFrameEvent e) {      
    }

    public void internalFrameIconified(InternalFrameEvent e) {
    }

    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    public void internalFrameActivated(InternalFrameEvent e) {
        if (windowName.equalsIgnoreCase("buildModel")) {
            so.setStateTab(0);
        } else {
            so.setStateTab(1);
        }

    }

    public void internalFrameDeactivated(InternalFrameEvent e) {
    }
}
