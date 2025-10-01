package org.example.exmod.mixins;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import finalforeach.cosmicreach.lang.Lang;
import org.example.exmod.LangMore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.Map;

@Mixin(Lang.class)
public class LangMixin implements LangMore {

    @Shadow
    private String name;

    @Shadow
    private String[] fallbackTags;

    @Shadow
    private Map<String, String> mappedStrings;

    @Unique
    private Map<String, String> fontOverride = new HashMap<>();

    // gets the font to override for the lang class
    @Unique
    public Map<String, String> getFontOverride() {
        return this.fontOverride;
    }

    /**
     * @author Spicylemon, CrabKing
     * @reason Adds the override function to get font overrides
     */
    @Overwrite
    public void read(Json json, JsonValue jsonData) {
        JsonValue value = jsonData.child;

        while(value != null) {
            if (value.name.equals("$schema")) {
                value = value.next;
            } else {
                if (value.name.equals("metadata")) {
                    for(JsonValue metadataValue = value.child; metadataValue != null; metadataValue = metadataValue.next) {
                        switch (metadataValue.name) {
                            case "name":
                                this.name = metadataValue.asString();
                                break;
                            case "fallbacks":
                                this.fallbackTags = metadataValue.asStringArray();
                                break;
                            // adds the override to game.json
                            case "override": {
                                JsonValue override = metadataValue.child;
                                while (override != null) {
                                    this.fontOverride.put(override.name, override.asString());
                                    override = override.next;
                                }
                                break;
                            }
                            //
                        }
                    }
                } else {
                    this.mappedStrings.put(value.name, value.asString());
                }

                value = value.next;
            }
        }

    }

}
