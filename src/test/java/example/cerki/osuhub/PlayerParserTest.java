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

import example.cerki.osuhub.Data.POJO.User;
import example.cerki.osuhub.Logic.UserParser;

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
    public void UserParse() throws Exception {
        File f = getFromResources("osu.html");
        Document doc = Jsoup.parse(f,"utf8");
        Elements table = doc.select("tbody").first().children();
        Element tr = table.get(0);
        User user = UserParser.parseUser(tr);
        assertEquals(user.getUserId(),124493);
        assertEquals(user.getAccuracy(),(float)98.76,2);
        assertEquals(user.getPpRaw(),14039,0);
        assertEquals(user.getPlaycount(),19586);
        assertEquals(user.getPpRank(),1);
        assertEquals(user.getUsername(),"Cookiezi");
        assertEquals(user.getCountry(),"flags/kr.png");
    }

    @Test
    public void parseEmpty() throws Exception{
        User player = UserParser.parseUser(null);
        assertEquals(null,player);
    }
}