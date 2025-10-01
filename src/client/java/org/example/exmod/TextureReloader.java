package org.example.exmod;

import com.badlogic.gdx.utils.IntMap;
import finalforeach.cosmicreach.CosmicReachFont;
import finalforeach.cosmicreach.FontTexture;

public class TextureReloader {

    /**
     * @author Spicylemon, Replet
     */
    public static void reloadAllFontTextures() {
        for (IntMap.Entry<FontTexture> entry : FontTexture.allFontTextures.entries()) {
            FontTexture block = entry.value;
            if (block == null) continue;

            int unicodeStart = block.unicodeStart;
            String fileName = getFontFileName(unicodeStart);

            if (block.fontTexture != null) block.fontTexture.dispose();
            var text = FontTexture.createFontTexture(unicodeStart,fileName);
            entry.value.fontTexture = text.fontTexture;
            entry.value.fontTextureRegions = text.fontTextureRegions;
            entry.value.fontCharStartPos = text.fontCharStartPos;
            entry.value.fontCharSizes = text.fontCharSizes;
        }

        CosmicReachFont.setAllFontsDirty();
    }


    /**
     * @author YOU FFE
     * @reason helper function from your code, info not stored anywhere :shrug:
     */
    public static String getFontFileName(int unicodeStart) {
        String fileName;
        switch (unicodeStart) {
            case 0 -> fileName = "cosmic-reach-font-0000-basic.png";
            case 256 -> fileName = "cosmic-reach-font-0100-extended-A.png";
            case 512 -> fileName = "cosmic-reach-font-0200.png";
            case 768 -> fileName = "cosmic-reach-font-0300-diacritics-greek-coptic.png";
            case 1024 -> fileName = "cosmic-reach-font-0400-cyrillic.png";
            case 12288 -> fileName = "cosmic-reach-font-3000-kana.png";
            default -> fileName = "cosmic-reach-font-" +
                    Integer.toHexString(unicodeStart).toUpperCase() + ".png";
        }
        return fileName;
    }

}
