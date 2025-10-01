package org.example.exmod.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import finalforeach.cosmicreach.lang.Lang;
import finalforeach.cosmicreach.settings.GraphicsSettings;
import finalforeach.cosmicreach.ui.GameStyles;
import finalforeach.cosmicreach.ui.widgets.CRButton;
import finalforeach.cosmicreach.world.Sky;
import org.example.exmod.TextureReloader;

/**
 * @author Crabking, Spicylemon, YOU we stole from WorldSelectionMenu
 */
public class NewLanguagesMenu extends GameState {

    TextField searchBar;
    ScrollPane langListScrollPane;
    boolean searchBarFocused = false;
    Array<Lang> Langs;
    Table langButtonsTable = new Table();
    private String oldSearch = "";
    private float oldScrollPos = 0;
    private Camera skyCamera;

    public void updateAllText(Array<Actor> actors){
        for (Actor actor : actors){
            if (actor instanceof LangButton langButton) {
                langButton.updateText();
            } else if (actor instanceof Table table) {
                updateAllText(table.getChildren());
            } else if (actor instanceof ScrollPane scrollPane) {
                updateAllText(scrollPane.getChildren());
            }
        }
    }

    public void updateAllText(){
        updateAllText(this.stage.getActors());
    }

    public void setupLangList() {
        String searchText = this.searchBar.getText();
        this.langButtonsTable.clear();

        int i = 0;
        for (Lang lang : this.Langs) {
            String langName = lang.getName();
            if (searchText == null || searchText.isEmpty() || langName.toLowerCase().contains(searchText.toLowerCase())) {
                CRButton selectLangButton = new LangButton(langName) {
                    public void onClick() {
                        lang.select();
                        updateAllText();

                        TextureReloader.reloadAllFontTextures(); //<- very important, does what the name says (with overrided fonts)

                        refresh();
                    }

                    @Override
                    public void updateText() {
                        super.updateText();
                        this.setTextColor(lang.isCurrentLanguage() ? Color.GOLD : Color.WHITE);
                    }
                };
                ((LangButton) selectLangButton).setTextColor(lang.isCurrentLanguage() ? Color.GOLD : Color.WHITE);
                this.langButtonsTable.add(selectLangButton).width(250.0F).height(50.0F).pad(16.0F);
                if (i >= 2){
                    this.langButtonsTable.row();
                    i = 0;
                } else {
                    i++;
                }
            }
        }

    }

    public void refresh(){
        this.stage.clear();
        Table layoutTable = new Table();
        layoutTable.setFillParent(true);
        if (this.searchBar != null) this.oldSearch = this.searchBar.getText();
        this.searchBar = new TextField(oldSearch, GameStyles.textstyle);

        EventListener listener = event -> {
            if (event instanceof InputEvent inputEvent) {
                switch (inputEvent.getType()) {
                    case enter:
                        searchBarFocused = true;
                        break;
                    case exit:
                        searchBarFocused = false;
                        break;
                    case keyUp:
                        if (inputEvent.getKeyCode() == 111 || inputEvent.getKeyCode() == 66 && stage.getKeyboardFocus() == searchBar) {
                            stage.setKeyboardFocus(null);
                            searchBarFocused = false;
                        }
                        break;
                    case keyTyped:
                        stage.setKeyboardFocus(searchBar);
                        setupLangList();
                        break;
                    case touchDown:
                        stage.setKeyboardFocus(searchBar);
                        searchBarFocused = true;
                        return true;
                }
            }

            return false;
        };

        this.searchBar.setMessageText(Lang.get("langSelectionSearch"));
        this.searchBar.setAlignment(1);
        this.searchBar.addListener(listener);
        layoutTable.add(this.searchBar).height(50.0F).width(500.0F).top().pad(16.0F);
        layoutTable.row();

        this.setupLangList();

        if (this.langListScrollPane != null) this.oldScrollPos = this.langListScrollPane.getScrollY();
        this.langListScrollPane = new ScrollPane(this.langButtonsTable);

        this.langListScrollPane.layout();
        this.langListScrollPane.setScrollingDisabled(true, false);
        this.langListScrollPane.setScrollY(this.oldScrollPos);
        this.langListScrollPane.updateVisualScroll();

        layoutTable.add(this.langListScrollPane).expand().fill();
        stage.setScrollFocus(this.langListScrollPane);


        Table table = new Table();
        table.setTouchable(Touchable.childrenOnly);
        table.setRound(true);
        CRButton returnButton = new LangButton(Lang.get("Return_to_Main_Menu")) {
            @Override
            public void onClick() {
                super.onClick();
                GameState.switchToGameState(new MainMenu());
            }

            @Override
            public void updateText() {
                setText(Lang.get("Return_to_Main_Menu"));
            }
        };
        table.add(returnButton).expand().bottom().pad(16.0F).size(250.0F, 50.0F);
        layoutTable.row();
        layoutTable.add(table);
        this.stage.addActor(layoutTable);
    }

    public void create() {
        super.create();
        this.stage.clear();
        this.skyCamera = new PerspectiveCamera(GraphicsSettings.fieldOfView.getValue(), (float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight());
        this.skyCamera.near = 0.1F;
        this.skyCamera.far = 2500.0F;
        Lang.loadLanguages(true);
        this.Langs = Lang.getLanguages();

        refresh();

        Gdx.input.setInputProcessor(this.stage);
    }

    public void switchAwayTo(GameState gameState) {
        super.switchAwayTo(gameState);
        Gdx.input.setInputProcessor(null);
    }

    public void onSwitchTo() {
        super.onSwitchTo();
        Gdx.input.setInputProcessor(this.stage);
    }

    public void render() {
        super.render();
        this.stage.act();
        ScreenUtils.clear(0, 0, 0F, 1.0F, true);
        
        Gdx.gl.glEnable(3042);
        Gdx.gl.glBlendFunc(770, 771);
        Gdx.gl.glDepthFunc(513);
        Gdx.gl.glEnable(2929);
        Gdx.gl.glDisable(2884);
        Sky.SPACE_DAY.drawSky(this.skyCamera);
        this.skyCamera.rotate(Vector3.Z, Gdx.graphics.getDeltaTime() * 0.25F);
        this.stage.draw();
        Gdx.gl.glCullFace(1029);
        Gdx.gl.glEnable(2884);
        if (Gdx.input.isButtonPressed(0) && !this.searchBarFocused && this.stage.getKeyboardFocus() == this.searchBar) {
            this.stage.setKeyboardFocus(null);
            this.searchBarFocused = false;
        }

    }
}
