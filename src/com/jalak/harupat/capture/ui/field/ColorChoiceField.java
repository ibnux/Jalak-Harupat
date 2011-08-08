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
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.ObjectChoiceField;

/**
 * @author Chris Miles
 * 
 * A new Field to allow a color to be chosen
 */
public class ColorChoiceField extends ObjectChoiceField {

    public static String[] COLOR_NAMES = new String[] { "Aliceblue",
                                                       "Antiquewhite", "Aqua",
                                                       "Aquamarine", "Azure",
                                                       "Beige", "Bisque",
                                                       "Black",
                                                       "Blanchedalmond",
                                                       "Blue", "Blueviolet",
                                                       "Brown", "Burlywood",
                                                       "Cadetblue",
                                                       "Chartreuse",
                                                       "Chocolate", "Coral",
                                                       "Cornflowerblue",
                                                       "Cornsilk", "Crimson",
                                                       "Cyan", "Darkblue",
                                                       "Darkcyan",
                                                       "Darkgoldenrod",
                                                       "Darkgray", "Darkgreen",
                                                       "Darkkhaki",
                                                       "Darkmagenta",
                                                       "Darkolivegreen",
                                                       "Darkorange",
                                                       "Darkorchid", "Darkred",
                                                       "Darksalmon",
                                                       "Darkseagreen",
                                                       "Darkslateblue",
                                                       "Darkslategray",
                                                       "Darkturquoise",
                                                       "Darkviolet",
                                                       "Deeppink",
                                                       "Deepskyblue",
                                                       "Dimgray", "Dodgerblue",
                                                       "Firebrick",
                                                       "Floralwhite",
                                                       "Forestgreen",
                                                       "Fuchsia", "Gainsboro",
                                                       "Ghostwhite", "Gold",
                                                       "Goldenrod", "Gray",
                                                       "Green", "Greenyellow",
                                                       "Honeydew", "Hotpink",
                                                       "Indianred", "Indigo",
                                                       "Ivory", "Khaki",
                                                       "Lavender",
                                                       "Lavenderblush",
                                                       "Lawngreen",
                                                       "Lemonchiffon",
                                                       "Lightblue",
                                                       "Lightcoral",
                                                       "Lightcyan",
                                                       "Lightgoldenrodyellow",
                                                       "Lightgreen",
                                                       "Lightgrey",
                                                       "Lightpink",
                                                       "Lightsalmon",
                                                       "Lightseagreen",
                                                       "Lightskyblue",
                                                       "Lightslategray",
                                                       "Lightsteelblue",
                                                       "Lightyellow", "Lime",
                                                       "Limegreen", "Linen",
                                                       "Magenta", "Maroon",
                                                       "Mediumaquamarine",
                                                       "Mediumblue",
                                                       "Mediumorchid",
                                                       "Mediumpurple",
                                                       "Mediumseagreen",
                                                       "Mediumslateblue",
                                                       "Mediumspringgreen",
                                                       "Mediumturquoise",
                                                       "Mediumvioletred",
                                                       "Midnightblue",
                                                       "Mintcream",
                                                       "Mistyrose", "Moccasin",
                                                       "Navajowhite", "Navy",
                                                       "Oldlace", "Olive",
                                                       "Olivedrab", "Orange",
                                                       "Orangered", "Orchid",
                                                       "Palegoldenrod",
                                                       "Palegreen",
                                                       "Paleturquoise",
                                                       "Palevioletred",
                                                       "Papayawhip",
                                                       "Peachpuff", "Peru",
                                                       "Pink", "Plum",
                                                       "Powderblue", "Purple",
                                                       "Red", "Rosybrown",
                                                       "Royalblue",
                                                       "Saddlebrown", "Salmon",
                                                       "Sandybrown",
                                                       "Seagreen", "Seashell",
                                                       "Sienna", "Silver",
                                                       "Skyblue", "Slateblue",
                                                       "Slategray", "Snow",
                                                       "Springgreen",
                                                       "Steelblue", "Tan",
                                                       "Teal", "Thistle",
                                                       "Tomato", "Turquoise",
                                                       "Violet", "Wheat",
                                                       "White", "Whitesmoke",
                                                       "Yellow", "Yellowgreen" };

    public static int[] COLOR_VALUES = new int[] { Color.ALICEBLUE,
                                                  Color.ANTIQUEWHITE,
                                                  Color.AQUA, Color.AQUAMARINE,
                                                  Color.AZURE, Color.BEIGE,
                                                  Color.BISQUE, Color.BLACK,
                                                  Color.BLANCHEDALMOND,
                                                  Color.BLUE, Color.BLUEVIOLET,
                                                  Color.BROWN, Color.BURLYWOOD,
                                                  Color.CADETBLUE,
                                                  Color.CHARTREUSE,
                                                  Color.CHOCOLATE, Color.CORAL,
                                                  Color.CORNFLOWERBLUE,
                                                  Color.CORNSILK,
                                                  Color.CRIMSON, Color.CYAN,
                                                  Color.DARKBLUE,
                                                  Color.DARKCYAN,
                                                  Color.DARKGOLDENROD,
                                                  Color.DARKGRAY,
                                                  Color.DARKGREEN,
                                                  Color.DARKKHAKI,
                                                  Color.DARKMAGENTA,
                                                  Color.DARKOLIVEGREEN,
                                                  Color.DARKORANGE,
                                                  Color.DARKORCHID,
                                                  Color.DARKRED,
                                                  Color.DARKSALMON,
                                                  Color.DARKSEAGREEN,
                                                  Color.DARKSLATEBLUE,
                                                  Color.DARKSLATEGRAY,
                                                  Color.DARKTURQUOISE,
                                                  Color.DARKVIOLET,
                                                  Color.DEEPPINK,
                                                  Color.DEEPSKYBLUE,
                                                  Color.DIMGRAY,
                                                  Color.DODGERBLUE,
                                                  Color.FIREBRICK,
                                                  Color.FLORALWHITE,
                                                  Color.FORESTGREEN,
                                                  Color.FUCHSIA,
                                                  Color.GAINSBORO,
                                                  Color.GHOSTWHITE, Color.GOLD,
                                                  Color.GOLDENROD, Color.GRAY,
                                                  Color.GREEN,
                                                  Color.GREENYELLOW,
                                                  Color.HONEYDEW,
                                                  Color.HOTPINK,
                                                  Color.INDIANRED,
                                                  Color.INDIGO, Color.IVORY,
                                                  Color.KHAKI, Color.LAVENDER,
                                                  Color.LAVENDERBLUSH,
                                                  Color.LAWNGREEN,
                                                  Color.LEMONCHIFFON,
                                                  Color.LIGHTBLUE,
                                                  Color.LIGHTCORAL,
                                                  Color.LIGHTCYAN,
                                                  Color.LIGHTGOLDENRODYELLOW,
                                                  Color.LIGHTGREEN,
                                                  Color.LIGHTGREY,
                                                  Color.LIGHTPINK,
                                                  Color.LIGHTSALMON,
                                                  Color.LIGHTSEAGREEN,
                                                  Color.LIGHTSKYBLUE,
                                                  Color.LIGHTSLATEGRAY,
                                                  Color.LIGHTSTEELBLUE,
                                                  Color.LIGHTYELLOW,
                                                  Color.LIME, Color.LIMEGREEN,
                                                  Color.LINEN, Color.MAGENTA,
                                                  Color.MAROON,
                                                  Color.MEDIUMAQUAMARINE,
                                                  Color.MEDIUMBLUE,
                                                  Color.MEDIUMORCHID,
                                                  Color.MEDIUMPURPLE,
                                                  Color.MEDIUMSEAGREEN,
                                                  Color.MEDIUMSLATEBLUE,
                                                  Color.MEDIUMSPRINGGREEN,
                                                  Color.MEDIUMTURQUOISE,
                                                  Color.MEDIUMVIOLETRED,
                                                  Color.MIDNIGHTBLUE,
                                                  Color.MINTCREAM,
                                                  Color.MISTYROSE,
                                                  Color.MOCCASIN,
                                                  Color.NAVAJOWHITE,
                                                  Color.NAVY, Color.OLDLACE,
                                                  Color.OLIVE, Color.OLIVEDRAB,
                                                  Color.ORANGE,
                                                  Color.ORANGERED,
                                                  Color.ORCHID,
                                                  Color.PALEGOLDENROD,
                                                  Color.PALEGREEN,
                                                  Color.PALETURQUOISE,
                                                  Color.PALEVIOLETRED,
                                                  Color.PAPAYAWHIP,
                                                  Color.PEACHPUFF, Color.PERU,
                                                  Color.PINK, Color.PLUM,
                                                  Color.POWDERBLUE,
                                                  Color.PURPLE, Color.RED,
                                                  Color.ROSYBROWN,
                                                  Color.ROYALBLUE,
                                                  Color.SADDLEBROWN,
                                                  Color.SALMON,
                                                  Color.SANDYBROWN,
                                                  Color.SEAGREEN,
                                                  Color.SEASHELL, Color.SIENNA,
                                                  Color.SILVER, Color.SKYBLUE,
                                                  Color.SLATEBLUE,
                                                  Color.SLATEGRAY, Color.SNOW,
                                                  Color.SPRINGGREEN,
                                                  Color.STEELBLUE, Color.TAN,
                                                  Color.TEAL, Color.THISTLE,
                                                  Color.TOMATO,
                                                  Color.TURQUOISE,
                                                  Color.VIOLET, Color.WHEAT,
                                                  Color.WHITE,
                                                  Color.WHITESMOKE,
                                                  Color.YELLOW,
                                                  Color.YELLOWGREEN };

    public ColorChoiceField(String label, int selectedColor) {
        super();
        
        setLabel(label);
        setChoices(COLOR_NAMES);
        for (int i = 0; i < COLOR_VALUES.length; i++) {
            if (selectedColor == COLOR_VALUES[i]) {
                setSelectedIndex(i);
                break;
            }
        }
    }
    
    public int getSelectedColor() {
        return COLOR_VALUES[getSelectedIndex()];
    }
    
    protected void paint(Graphics graphics) {
        graphics.setColor(getSelectedColor());
        super.paint(graphics);
    }
}