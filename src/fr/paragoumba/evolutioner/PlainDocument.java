package fr.paragoumba.evolutioner;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class PlainDocument extends javax.swing.text.PlainDocument {

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

        for (char c : str.toCharArray()){

            if (!Character.isDigit(c)){

                return;

            }
        }

        super.insertString(offs, str, a);

    }
}
