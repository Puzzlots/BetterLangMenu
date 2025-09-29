package org.example.exmod.mixins;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

@Mixin(FontTexture.class)
public abstract class FontTextureMixin {


    @Shadow
    public static IntMap<FontTexture> allFontTextures;

    @Shadow
    private static IntSet addedFontIndices;

    @Shadow public int unicodeStart;
    @Shadow public Texture fontTexture;
    @Shadow TextureRegion[] fontTextureRegions;
    @Shadow Vector2[] fontCharStartPos;
    @Shadow Vector2[] fontCharSizes;

    /**
     * @author Ollie
     * @reason because he wanted to
     */
    @Overwrite
    static FontTexture createFontTexture(int unicodeStart, String fileName) {

        LangMore lang =  (LangMore) Lang.currentLang;
        Map<String , String> fontOverride = lang.getFontOverride();
        fileName = fontOverride.getOrDefault(fileName, fileName);

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


    /*
    public void swapTexture(FileHandle newFontFile) {
    Texture newTex = new Texture(newFontFile);

    for (TextureRegion region : fontTextureRegions) {
        region.setTexture(newTex);
    }

    if (this.fontTexture != null) {
        this.fontTexture.dispose(); // optional, depends if you want to free old texture
    }
    this.fontTexture = newTex;
}*/

}
