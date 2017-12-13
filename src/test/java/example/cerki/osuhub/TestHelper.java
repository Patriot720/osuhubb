package example.cerki.osuhub;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;

import example.cerki.osuhub.Feed.Beatmap;

import static org.junit.Assert.assertEquals;

public class TestHelper {
    public static boolean isImageEqualToRes(Context context,ImageView actualImageView,
                                            int expectedDrawable) {

        Drawable expected = context.getResources().getDrawable(
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

     static byte[] BitmapToByteArray(Bitmap bm2) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm2.compress(Bitmap.CompressFormat.PNG,100,bos);
        return bos.toByteArray();
    }
    public static StringBuilder getFromResources(Context context, String fileName) throws Exception {
        URL resource = context.getClassLoader().getResource(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            builder.append(line).append("\n");
        return builder;
    }

    public static File getFromResources(String fileName) {
        String path = new File("").getAbsolutePath();
        File f = new File(path + "/app/src/test/resources/" + fileName);
        return f;
    }
}
