package org.example.exmod.mixins;

import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.widgets.CRButton;
import org.example.exmod.menu.NewLanguagesMenu;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = {"finalforeach/cosmicreach/gamestates/MainMenu$9"})
public class LangButtonMixin extends CRButton {

    @Override
    public void onClick() {
        super.onClick();
        GameState.switchToGameState(new NewLanguagesMenu());
    }
}
