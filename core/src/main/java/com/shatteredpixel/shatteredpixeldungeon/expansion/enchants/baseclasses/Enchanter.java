package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ItemSlot;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndInfoItem;
import com.watabou.noosa.Game;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Component;

public class Enchanter extends Item {
    {
        unique = true;
        defaultAction = AC_ENCHANT;
    }

    private static final String AC_ENCHANT = "ac_enchant";

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }




    public static class WndEnchant extends Window {
        private static final int BTN_SIZE = 24;
        private static final int WIDTH = 120;
        private static final int GAP = 2;

        private ItemButton[] inputs = new ItemButton[3];
        private ItemButton output;

        public WndEnchant(){
            super();

            resize(WIDTH, 100);

            RenderedTextBlock title = PixelScene.renderTextBlock(M.L(Enchanter.class, "title"), 10);
            title.setPos(WIDTH/2-title.width()/2, GAP);
            add(title);

            synchronized (inputs){
                for(int i=0; i< inputs.length; ++i){
                    inputs[i]=new ItemButton(){
                        @Override
                        protected void onClick() {
                            super.onClick();
                            if(item!=null){
                                item = null;
                                slot.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
                            }

                        }

                        @Override
                        protected boolean onLongClick() {
                            if (item != null){
                                Game.scene().addToFront(new WndInfoItem(item));
                                return true;
                            }
                            return false;
                        }
                    };
                    //inputs[i].setRect(WIDTH, BTN_SIZE, BTN_SIZE)

                }
            }



        }





    }

    public static class ItemButton extends Component {

        protected NinePatch bg;
        protected ItemSlot slot;

        public Item item = null;

        @Override
        protected void createChildren() {
            super.createChildren();

            bg = Chrome.get( Chrome.Type.RED_BUTTON);
            add( bg );

            slot = new ItemSlot() {
                @Override
                protected void onPointerDown() {
                    bg.brightness( 1.2f );
                    Sample.INSTANCE.play( Assets.Sounds.CLICK );
                }
                @Override
                protected void onPointerUp() {
                    bg.resetColor();
                }
                @Override
                protected void onClick() {
                    ItemButton.this.onClick();
                }

                @Override
                protected boolean onLongClick() {
                    return ItemButton.this.onLongClick();
                }
            };
            slot.enable(true);
            add( slot );
        }

        protected void onClick() {}
        protected boolean onLongClick() {
            return false;
        }

        @Override
        protected void layout() {
            super.layout();

            bg.x = x;
            bg.y = y;
            bg.size( width, height );

            slot.setRect( x + 2, y + 2, width - 4, height - 4 );
        }

        public void item( Item item ) {
            slot.item( this.item = item );
        }
    }
}
