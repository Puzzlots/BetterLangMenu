package org.example.exmod;

import java.util.Map;

public interface LangMore {

    default Map<String, String> getFontOverride() {
        return null;
    }

}
