package com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Collection;

public class TypeModifier extends Buff {
    public enum Mode{
        REPLACE,    //if this type exists, override the value
        MULTI,      //multiply new value with the old value.
        LINEAR,     //add new value to the old.
        NEVER       //keep old value
    }
    private ArrayList<SingleType> types = new ArrayList<>();

    private boolean affectSingleModifier(int type, float modVal, Mode howToOverride){
        Class<? extends SingleType> cls = TypeConsts.typeFinder.get(type);
        if(cls == null) return false;
        SingleType st = null;
        for(SingleType singleType: types){
            if(singleType.getClass() == cls){
                st = singleType;
            }
        }
        if(st != null){
            if(howToOverride == Mode.REPLACE){
                st.setModifier(modVal);
            }else if(howToOverride == Mode.MULTI){
                st.setModifier(modVal * st.getModifier());
            }else if(howToOverride == Mode.LINEAR){
                st.setModifier(modVal + st.getModifier());
            }//else do nothing
        }else{
            types.add(Reflection.newInstance(cls));
        }
        return true;
    }

    public static boolean affectModifier(Char ch, int type, float modVal, Mode howToOverride){
        TypeModifier tm = Buff.affect(ch, TypeModifier.class);
        if(tm != null){
            return tm.affectSingleModifier(type, modVal, howToOverride);
        }
        return false;
    }

    public static boolean detachModifier(Char ch, int type){
        TypeModifier tm = ch.buff(TypeModifier.class);
        if(tm != null){
            Class<? extends SingleType> cls = TypeConsts.typeFinder.get(type);
            if(cls != null) {
                int toRemove = -1;
                for (int i = 0, L = tm.types.size(); i < L; ++i){
                    if(tm.types.get(i).getClass() == cls){
                        toRemove = i;
                        break;
                    }
                }
                tm.types.remove(toRemove);
            }
        }
        return false;
    }


    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("resType", this.types);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        Collection<Bundlable> storedTypes = bundle.getCollection("resType");
        for(Bundlable b: storedTypes){
            SingleType ty = (SingleType) b;
            types.add(ty);
        }
    }


}
