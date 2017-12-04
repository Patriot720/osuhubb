package example.cerki.osuhub;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;

import example.cerki.osuhub.List.Player;

import static example.cerki.osuhub.TestHelper.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class PlayerParserTest {
    Elements tbody;
    @Before
    public void setUp() throws Exception {
        File fromResources = getFromResources("osu.html");
        Document doc = Jsoup.parse(fromResources,"utf8");
         tbody = doc.select("tbody").first().children();
    }

    @Test
    public void parse() throws Exception {
        Element tr = tbody.get(0);
        Player player = PlayerParser.parsePlayer(tr);
        assertPlayer(player);
    }

    private void assertPlayer(Player player) {
        assertEquals("Cookiezi",player.getUsername());
        assertEquals("flags/kr.png",player.getCountry());
        assertNotNull(player.getString("pp"));
        assertNotNull(player.getString("acc"));
        assertEquals(Player.ACTIVE,player.getActivity());
    }

    @Test
    public void parseInactivePlayer() throws Exception{
        Element tr = tbody.get(43);
        Player player = PlayerParser.parsePlayer(tr);
        assertEquals(Player.INACTIVE,player.getActivity());
    }
    // TODO
//    @Test
    public void parseOldPage() throws IOException {
        File f = getFromResources("oldOsu.html");
        Document doc = Jsoup.parse(f,"utf8");
        Elements table = doc.select("tbody").first().children();
        Element tr = table.get(0);
        Player player = PlayerParser.parsePlayer(tr);
        assertPlayer(player);
    }



    @Test
    public void parseEmpty() throws Exception{
        Player player = PlayerParser.parsePlayer(null);
        assertEquals(null,player);
    }
}