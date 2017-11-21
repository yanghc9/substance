/*
 * @(#)ColorChooserMainPanel.java  1.4  2005-12-18
 *
 * Copyright (c) 2005 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

package org.pushingpixels.substance.internal.contrib.randelshofer.quaqua.colorchooser;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EnumSet;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import org.pushingpixels.substance.api.SubstanceSlices;
import org.pushingpixels.substance.api.colorscheme.SubstanceColorScheme;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.internal.utils.icon.TransitionAwareIcon;

/**
 * The main panel of the color chooser UI.
 * 
 * @author Werner Randelshofer
 * @version 1.4 2005-12-18 ColorPicker added. <br>
 *          1.3 2005-09-05 Get font from UIManager. <br>
 *          1.2 2005-08-27 Remember chooser panel. <br>
 *          1.1 2005-04-23 Removed main method. <br>
 *          1.0 30 March 2005 Created.
 */
public class ColorChooserMainPanel extends javax.swing.JPanel {
	/**
	 * We store here the name of the last selected chooser. When the
	 * ColorChooserMainPanel is recreated multiple times in the same applicatin,
	 * the application 'remembers' which panel the user had opened before.
	 */
	private static String lastSelectedChooserName = null;

	/** Creates new form. */
	public ColorChooserMainPanel() {
		initComponents();
	}

	public void setPreviewPanel(JComponent c) {
		previewPanelHolder.removeAll();
		if (c != null) {
			previewPanelHolder.add(c);
		}
	}

	public void addColorChooserPanel(final SubstanceColorChooserPanel ccp) {
		final String displayName = ccp.getDisplayName();

		if (displayName.equals("Color Picker")) {
			northPanel.add(ccp, BorderLayout.WEST);
		} else {
			JToggleButton tb = new JToggleButton();
			// Create a transition-aware wrapper around our icon so that it is colorized
			// based on the color scheme that matches the current state of our toggle button
            tb.setIcon(new TransitionAwareIcon(tb,
                    (SubstanceColorScheme scheme) -> ccp.getHiDpiAwareIcon(18, scheme),
                    ccp.getDisplayName()));
			
			tb.setToolTipText(displayName);
			tb.setFocusable(false);
			tb.putClientProperty(SubstanceLookAndFeel.BUTTON_SIDE_PROPERTY, 
					EnumSet.allOf(SubstanceSlices.Side.class));
			JPanel centerView = new JPanel(new BorderLayout());
			centerView.add(ccp);
			chooserPanelHolder.add(centerView, displayName);
			toolBarButtonGroup.add(tb);
			toolBar.add(tb);

			if (toolBar.getComponentCount() == 1 || lastSelectedChooserName != null
					&& lastSelectedChooserName.equals(displayName)) {
				tb.setSelected(true);
				CardLayout cl = (CardLayout) chooserPanelHolder.getLayout();
				cl.show(chooserPanelHolder, displayName);
			}

			tb.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					if (evt.getStateChange() == ItemEvent.SELECTED) {
						CardLayout cl = (CardLayout) chooserPanelHolder.getLayout();
						cl.show(chooserPanelHolder, displayName);
						lastSelectedChooserName = displayName;
					}
				}
			});
		}
	}

	public void removeAllColorChooserPanels() {
		Component[] tb = toolBar.getComponents();
		for (int i = 0; i < tb.length; i++) {
			if (tb[i] instanceof AbstractButton) {
				toolBarButtonGroup.remove((AbstractButton) tb[i]);
			}
		}
		toolBar.removeAll();
		chooserPanelHolder.removeAll();

		northPanel.removeAll();
		northPanel.add(previewPanelHolder);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		toolBarButtonGroup = new javax.swing.ButtonGroup();
		toolBar = new javax.swing.JToolBar();
		mainPanel = new javax.swing.JPanel();
		northPanel = new javax.swing.JPanel();
		previewPanelHolder = new javax.swing.JPanel();
		chooserPanelHolder = new javax.swing.JPanel();

		setLayout(new java.awt.BorderLayout());

		toolBar.setFloatable(false);
		add(toolBar, java.awt.BorderLayout.NORTH);

		mainPanel.setLayout(new java.awt.BorderLayout());

		mainPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 4, 7, 4)));
		northPanel.setLayout(new java.awt.BorderLayout());

		previewPanelHolder.setLayout(new java.awt.BorderLayout());
		boolean isLtr = northPanel.getComponentOrientation().isLeftToRight();
		previewPanelHolder.setBorder(new EmptyBorder(0, isLtr ? 4 : 0, 0, isLtr ? 0 : 4));

		northPanel.add(previewPanelHolder, java.awt.BorderLayout.CENTER);

		mainPanel.add(northPanel, java.awt.BorderLayout.NORTH);

		chooserPanelHolder.setLayout(new java.awt.CardLayout());

		chooserPanelHolder
				.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 0, 0, 0)));
		mainPanel.add(chooserPanelHolder, java.awt.BorderLayout.CENTER);

		add(mainPanel, java.awt.BorderLayout.CENTER);

	}// GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel chooserPanelHolder;
	private javax.swing.JPanel mainPanel;
	private javax.swing.JPanel northPanel;
	private javax.swing.JPanel previewPanelHolder;
	private javax.swing.JToolBar toolBar;
	private javax.swing.ButtonGroup toolBarButtonGroup;
	// End of variables declaration//GEN-END:variables

}
