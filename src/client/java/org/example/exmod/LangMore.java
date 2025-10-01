package org.example.exmod;

import java.util.Map;

/**
 * @author Crabking
 */
public interface LangMore {

    // add to Lang class
    default Map<String, String> getFontOverride() {
        return null;
    }

}
