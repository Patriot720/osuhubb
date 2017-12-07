package example.cerki.osuhub;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;

import example.cerki.osuhub.Feed.Beatmap;
import example.cerki.osuhub.List.Player;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;

public class TestHelper {
    public static Player getFakePlayer(int multiplier){
        Player player = new Player(1);
        player.setUsername("username");
        player.setCountry("country");
        player.setComparable("pp", (double) (1000 * multiplier));
        player.setComparable("pc", (double) (1000 * multiplier));
        player.setComparable("acc", (double) (10.32 * multiplier));
        player.setComparable("rank", (double) (1000 * multiplier));
        return player;
    }
    public static Player getFakePlayer(){
        return getFakePlayer(1);
    }
    public static void assertPlayer(Player p){
        assertPlayer(p,1);
    }
    public static void assertPlayer(Player p,int multiplier){
        String value = String.valueOf(1000 * multiplier);
        String floatVal = String.valueOf(10.32 * multiplier);
        assertEquals(value,p.getString("pp"));
        assertEquals(floatVal,p.getString("acc"));
        assertEquals(value,p.getString("rank"));
        assertEquals("username",p.getUsername());
        assertEquals("country",p.getCountry());
        assertEquals(Player.ACTIVE,p.getActivity());
    }
     public static boolean isImageEqualToRes(ImageView actualImageView,
                                             int expectedDrawable) {

        Drawable expected = getTargetContext().getResources().getDrawable(
                expectedDrawable);
        Drawable actual = actualImageView.getDrawable();

        if (expected != null && actual != null) {
            Bitmap expectedBitmap = getBitmapOfDrawable(expected);
            Bitmap actualBitmap = getBitmapOfDrawable(actual);

            return areBitmapsEqual(expectedBitmap, actualBitmap);
        }
        return false;
    }
    static Bitmap getBitmapOfDrawable(Drawable db){
        BitmapDrawable bd = ((BitmapDrawable) db);
        Bitmap bitmap = bd.getBitmap();
        return bitmap;
    }
     public static boolean areBitmapsEqual(Bitmap bm1, Bitmap bm2){
        byte[] array1 =BitmapToByteArray(bm1);
        byte[] array2 = BitmapToByteArray(bm2);
        return Arrays.equals(array1, array2);
    }

     static byte[] BitmapToByteArray(Bitmap bm2) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm2.compress(Bitmap.CompressFormat.PNG,100,bos);
        return bos.toByteArray();
    }
        public static StringBuilder getFromResources(String fileName) throws Exception{
            URL resource = getTargetContext().getClassLoader().getResource(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null)
                builder.append(line).append("\n");
            return builder;
    }

    public static Beatmap getFakeBeatmap(){
        Beatmap beatmap = new Beatmap();
        Field[] fields = beatmap.getClass().getFields();
        for (Field field : fields) {
            if(Modifier.isStatic(field.getModifiers())){
                try {
                    String key = (String) field.get(field.getName());
                    beatmap.put(key,"1");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return beatmap;
    }
    public static void assertFakeBeatmap(Beatmap beatmap){
        Beatmap fakeBeatmap = getFakeBeatmap();
        for(String s : fakeBeatmap.keySet()){
            assertEquals(beatmap.get(s),fakeBeatmap.get(s));
        }
    }
}
