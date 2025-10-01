package org.example.exmod.menu;

import com.badlogic.gdx.graphics.Color;
import finalforeach.cosmicreach.ui.widgets.CRButton;


/**
 * @author Crabking
 * @reason to update the text on the button when we reload the lang and font,
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

    // Crab insisted on keeping this
    public void setDisabledTextColor(Color color){
        this.disabledColor = color;
    }

}
