package org.example.exmod.menu;

import com.badlogic.gdx.graphics.Color;
import finalforeach.cosmicreach.ui.widgets.CRButton;


/**
 * we would like this added to CRButton :)
 */
public class LangButton extends CRButton {

    public LangButton(String text) {
        super(text);
    }

    public void updateText(){
    }

    public void setTextColor(Color color){
        this.enabledColor = color;
    }

    public void setDisabledTextColor(Color color){
        this.disabledColor = color;
    }

}
