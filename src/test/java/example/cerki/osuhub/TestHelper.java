package example.cerki.osuhub;


import java.io.File;

import example.cerki.osuhub.List.Player;

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
    public static void assertFakePlayer(Player p){
        assertFakePlayer(p,1);
    }
    public static void assertFakePlayer(Player p, int multiplier){
        String value = String.valueOf(1000 * multiplier);
        String floatVal = String.valueOf(10.32 * multiplier);
        assertEquals(value,p.getString("pp"));
        assertEquals(floatVal,p.getString("acc"));
        assertEquals(value,p.getString("rank"));
        assertEquals("username",p.getUsername());
        assertEquals("country",p.getCountry());
        assertEquals(Player.ACTIVE,p.getActivity());
    }

    public static File getFromResources(String fileName){
        String path = new File("").getAbsolutePath();
        File f = new File(path + "/app/src/test/resources/" + fileName);
        return f;
    }
}
