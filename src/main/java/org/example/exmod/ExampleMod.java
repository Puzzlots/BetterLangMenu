package org.example.exmod;

import dev.puzzleshq.puzzleloader.loader.mod.entrypoint.common.ModInit;

import static org.example.exmod.Constants.MOD_ID;

public class ExampleMod implements ModInit {
    @Override
    public void onInit() {

        Constants.LOGGER.info("Hello From INIT");
    }

}
