/*******************************************************************************
 * Copyright (C) 2005 Chris Miles
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 ******************************************************************************/
package com.jalak.harupat.capture.ui.field;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.ActiveAutoTextEditField;

public class ColorEditField extends ActiveAutoTextEditField {

    private int color = Color.BLACK;

    public ColorEditField(String label,String value,int max,long style) {
        super(label,value,max,style);
    }
    
    public void setFont(Font font) {
    	super.setFont(font);
    	invalidate();
    }


    public void setColor(int newColor) {
        color = newColor;
    }

    protected void paint(Graphics graphics) {
        graphics.setColor(color);
        super.paint(graphics);
    }
}