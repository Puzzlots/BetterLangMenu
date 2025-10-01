package org.example.exmod.mixins;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntSet;
import finalforeach.cosmicreach.CosmicReachFont;
import finalforeach.cosmicreach.FontTexture;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.lang.Lang;
import org.example.exmod.LangMore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(FontTexture.class)
public abstract class FontTextureMixin {


    @Shadow
    public static IntMap<FontTexture> allFontTextures;

    @Shadow
    private static IntSet addedFontIndices;

    /**
     * @author Spicylemon, CrabKing
     * @reason adds the override function
     */
    @Overwrite
    public static FontTexture createFontTexture(int unicodeStart, String fileName) {

        // Gets the correct overrides if applicable, else original texture
        LangMore lang =  (LangMore) Lang.currentLang;
        Map<String , String> fontOverride = lang.getFontOverride();
        fileName = fontOverride.getOrDefault(fileName, fileName);
        // end of our code

        // the rest is unchanged, (I hope)
        int index = unicodeStart / 256;
        FileHandle fontFile = GameAssetLoader.loadAsset("font/" + fileName);
        addedFontIndices.add(index);

        if (fontFile == null) {
            return null;
        } else {
            FontTexture texture = new FontTexture(unicodeStart, fontFile);
            allFontTextures.put(index, texture);
            CosmicReachFont.setAllFontsDirty();
            return texture;
        }
    }
}
