package example.cerki.osuhub;

import android.support.annotation.NonNull;
import android.widget.GridLayout;
import android.widget.GridView;

import com.github.zagum.switchicon.SwitchIconView;

/**
 * Created by cerki on 06-Dec-17.
 */

public class Mods {
   private static final int None           = 0;
   private static final int NoFail         = 1;
   private static final int Easy           = 2;
   private static final int NoVideo        = 4; // Not used anymore, but can be found on old plays like Mesita on b/7823;
   private static final int Hidden         = 8;
   private static final int HardRock       = 16;
   private static final int SuddenDeath    = 32;
   private static final int DoubleTime     = 64;
   private static final int Relax          = 128;
   private static final int HalfTime       = 256;
   private static final int Nightcore      = 512; // Only set along with DoubleTime. i.e: NC only gives 57;
   private static final int Flashlight     = 1024;
   private static final int Autoplay       = 2048;
   private static final int SpunOut        = 4096;
   private static final int Relax2         = 8192;	// Autopilot;
   private static final int Perfect        = 16384; // Only set along with SuddenDeath. i.e: PF only gives 1641;
    // TODO add mania stuff later
    @NonNull
    public static String parseFlags(int flags){
       StringBuilder builder = new StringBuilder();
       if(flags == 0)
           builder.append("NoMod");
        else builder.append("+");
       if((flags & NoFail) == NoFail)builder.append("NF");
       if((flags & Easy) == Easy)builder.append("EZ");
       if((flags & Hidden) == Hidden)builder.append("HD");
       if((flags & HardRock) == HardRock)builder.append("HR");
       if((flags & SuddenDeath) == SuddenDeath){
           if((flags & Perfect) == Perfect)builder.append("PF");
           else builder.append("SD");
       }
       if((flags & DoubleTime) == DoubleTime){
           if((flags & Nightcore) == Nightcore)builder.append("NC");
           else builder.append("DT");
       }
       if((flags & Relax) == Relax)builder.append("RX");
       if((flags & HalfTime) == HalfTime)builder.append("HF");
       if((flags & Flashlight) == Flashlight)builder.append("FL");
       if((flags & Autoplay) == Autoplay)builder.append("AP");
       if((flags & SpunOut) == SpunOut)builder.append("SO");
       if((flags & Relax2) == Relax2)builder.append("RX");
       return builder.toString();
    }
    @NonNull
    public static String parseFlags(String flags){
        return parseFlags(Integer.parseInt(flags));
    }
    public static int getModsIntegerValue(GridLayout modsView){
        int childCount = modsView.getChildCount();
        int mods = 0;
        for (int i = 0; i < childCount; i++) {
            SwitchIconView mod = (SwitchIconView) modsView.getChildAt(i);
            if(mod.isIconEnabled()) {
                int flag = Integer.parseInt((String) mod.getTag());
                mods += flag;
            }
        }
        return mods;
    }
}
