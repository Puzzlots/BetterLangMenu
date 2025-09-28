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
     * @author Mr Ollie
     * @reason because i wanted to
     */
    @Overwrite
    static FontTexture createFontTexture(int unicodeStart, String fileName) {

        LangMore lang =  (LangMore) Lang.currentLang;

        Map<String , String> fontOverride = lang.getFontOverride();

        if (fontOverride.containsKey(fileName)){
            fileName = fontOverride.get(fileName);
        }

        int index = unicodeStart / 256;
        System.out.println("Unicode: "+unicodeStart);
        FileHandle fontFile = GameAssetLoader.loadAsset("font/" + fileName);
        System.out.println("Font file: " + fontFile);
        addedFontIndices.add(index);
        if (fontFile == null) {
            return null;
        } else {
            FontTexture texture = new FontTexture(unicodeStart, fontFile);
            allFontTextures.remove(index);
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

    /**
     * @author I and I
     * @reason Overwriting font texture
     */
//    @Overwrite
//    public static FontTexture getFontTexOfChar(char c) {
//        int fontIndex = c / 256;
//        FontTexture t = (FontTexture)allFontTextures.get(fontIndex);
//        if (t == null && !addedFontIndices.contains(fontIndex)) {
//            int unicodeStart = fontIndex * 256;
//            String var10000;
//            switch (unicodeStart) {
//                case 0 -> var10000 = "cosmic-reach-font-0000-basic.png";
//                case 256 -> var10000 = "cosmic-reach-font-0100-extended-A.png";
//                case 512 -> var10000 = "cosmic-reach-font-0200.png";
//                case 768 -> var10000 = "cosmic-reach-font-0300-diacritics-greek-coptic.png";
//                case 1024 -> var10000 = "cosmic-reach-font-0400-cyrillic.png";
//                case 12288 -> var10000 = "cosmic-reach-font-3000-kana.png";
//                default -> var10000 = "cosmic-reach-font-" + Integer.toHexString(unicodeStart).toUpperCase() + ".png";
//            }
//
//            String fontName = var10000;
//            createFontTexture(unicodeStart, fontName);
//        }
//
//        return t;
//    }

}
