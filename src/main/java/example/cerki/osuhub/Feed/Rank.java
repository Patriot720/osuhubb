package example.cerki.osuhub.Feed;

import example.cerki.osuhub.R;

/**
 * Created by cerki on 11-Dec-17.
 */

public class Rank {
    public static int getRankResourceId(String rank){
        if(rank.equals("S"))
            return R.drawable.s;
        if(rank.equals("SH"))
            return R.drawable.sh;
        if(rank.equals("XH"))
            return R.drawable.xh;
        if(rank.equals("SS"))
            return R.drawable.ss;
        if(rank.equals("A"))
            return R.drawable.a;
        if(rank.equals("B"))
            return R.drawable.b;
        if(rank.equals("C"))
            return R.drawable.c;
        if(rank.equals("D"))
            return R.drawable.d;
        return 0;
    }
}
