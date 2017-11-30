package example.cerki.osuhub;


import org.jsoup.nodes.Element;

import example.cerki.osuhub.List.Player;

public class PlayerParser {
    public static Player parsePlayer(Element tr){
            if(tr == null)
                return null;
            int isActive =  tr.classNames().toString().indexOf("inactive");
            Boolean activity;
            if(isActive == -1)
                activity = Player.ACTIVE;
            else
                activity = Player.INACTIVE;
            String user_id = tr.select(".ranking-page-table__user-link").attr("href");
            Player p = new Player(user_id);
            String username = tr.select("span").text();
            String rank = tr.children().first().text();
            String acc = tr.children().get(2).text();
            String pc = tr.children().get(3).text();
            String pp = tr.children().get(4).text();
            String country_tmp = tr.children().get(1).select("span").attr("style");
            String country = country_tmp.substring(country_tmp.indexOf("'") + 1,country_tmp.lastIndexOf("'"));
            country = country.substring(1);
            country = country.substring(country.indexOf("/"));
            country = country.toLowerCase();
            country = country.substring(1);
           p.set(Columns.ACC,acc);
           p.set(Columns.PP,pp);
           p.set(Columns.PC,pc);
           p.set(Columns.RANK,'-' + rank.substring(1));
           p.setUsername(username);
           p.setCountry(country);
           p.setActivity(activity);
           return p;
    }
}
