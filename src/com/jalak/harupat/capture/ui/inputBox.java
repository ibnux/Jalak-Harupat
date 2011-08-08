package com.jalak.harupat.capture.ui;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.NumericChoiceField;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.DialogFieldManager;
import net.rim.device.api.ui.container.PopupScreen;

import com.jalak.harupat.capture.ui.field.ColorChoiceField;
import com.jalak.harupat.capture.ui.field.ColorEditField;
import com.jalak.harupat.capture.util.DBStor;
import com.jalak.harupat.capture.util.Settings;


public class inputBox extends PopupScreen implements FieldChangeListener {
	ColorEditField nama = new ColorEditField("", "", 300, EditField.NO_NEWLINE);
	ButtonField bf = new ButtonField("Add", ButtonField.CONSUME_CLICK|FIELD_RIGHT);
	ColorChoiceField color = new ColorChoiceField("", DBStor.getSettings().getColor());
	ObjectChoiceField family = new ObjectChoiceField("", FontFamily.getFontFamilies(),DBStor.getSettings().getFamily());
	ObjectChoiceField tipe = new ObjectChoiceField("", new String[]{"PLAIN","BOLD","ITALIC"},DBStor.getSettings().getTipe());
	NumericChoiceField size = new NumericChoiceField("Size", 7, 100, 1,DBStor.getSettings().getSize());
	
	public inputBox(String Judul, String str) {
		super(new DialogFieldManager(VERTICAL_SCROLL));
		DialogFieldManager vfm = (DialogFieldManager)getDelegate();
		vfm.setIcon(new BitmapField(Bitmap.getPredefinedBitmap(Bitmap.QUESTION)));
		vfm.setMessage(new RichTextField(Judul,NON_FOCUSABLE));
		vfm.addCustomField(nama);
		nama.setColor(DBStor.getSettings().getColor());
		nama.setText(str);
		vfm.addCustomField(new SeparatorField());
		vfm.addCustomField(color);color.setChangeListener(this);
		vfm.addCustomField(family);family.setChangeListener(this);
		vfm.addCustomField(tipe);tipe.setChangeListener(this);
		vfm.addCustomField(size);size.setChangeListener(this);
		vfm.addCustomField(new SeparatorField());
		vfm.addCustomField(bf);
		bf.setChangeListener(this);
		nama.setFont(getFont());
        UiApplication.getUiApplication().pushModalScreen(this);
	}
	
	protected boolean keyChar(char c, int status, int time)
    {
    	//Close this screen if escape is selected.
    	if (c == Characters.ESCAPE)
    	{
    		nama.setText("");
    		this.close();
    		return true;
    	}    	
    	return super.keyChar(c, status, time);
    }
	
	public String getNama() {
		return nama.getText();
	}
	
	public int getColor() {
		return color.getSelectedColor();
	}
	public int getSize() {
		return size.getSelectedValue();
	}
	public Font getFont() {
		try{
			FontFamily _fontFamily = FontFamily.forName(FontFamily.getFontFamilies()[family.getSelectedIndex()].getName());
			return _fontFamily.getFont(FontFamily.SCALABLE_FONT, size.getSelectedValue()).derive(tipe.getSelectedIndex());
		}catch(Exception e){}
		return null;
	}
	
	protected boolean keyDown(int in_nKeyCode, int in_nTime)
	{
		int nKeyPressed = Keypad.key(in_nKeyCode);
		if(nKeyPressed == Keypad.KEY_MENU)
		{
			Menu apa = this.getMenu(Menu.INSTANCE_DEFAULT);
			apa.show();
			return true;
		}
		return false;
	}	
	public void fieldChanged(Field field, int context) {
		if(field==bf){
			Settings st = DBStor.getSettings();
			st.setColor(color.getSelectedColor());
			st.setFamily(family.getSelectedIndex());
			st.setSize(size.getSelectedIndex());
			st.setTipe(tipe.getSelectedIndex());
			close();
		}else if(field==family || field==size || field==tipe){
			try{
				FontFamily _fontFamily = FontFamily.forName(FontFamily.getFontFamilies()[family.getSelectedIndex()].getName());
				nama.setFont(_fontFamily.getFont(FontFamily.SCALABLE_FONT, size.getSelectedValue()).derive(tipe.getSelectedIndex()));
			}catch(Exception e){}
		}else if(field==color){
			nama.setColor(color.getSelectedColor());
		}
	}
}
