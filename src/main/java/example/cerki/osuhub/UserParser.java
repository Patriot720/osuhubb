package example.cerki.osuhub;


import org.jsoup.nodes.Element;

import example.cerki.osuhub.API.POJO.User;
import example.cerki.osuhub.List.Player;
import retrofit2.Converter;

public class UserParser {
    @Deprecated
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
           p.setComparable(Columns.ACC,acc);
           p.setComparable(Columns.PP,pp);
           p.setComparable(Columns.PC,pc);
           p.setComparable(Columns.RANK,'-' + rank.substring(1));
           p.setUsername(username);
           p.setCountry(country);
           p.setActivity(activity);
           return p;
    }
    public static User parseUser(Element tr){
        if(tr == null)
            return null;
        String user_id_tmp = tr.select(".ranking-page-table__user-link").attr("href");
        int user_id = Integer.parseInt(user_id_tmp.substring(user_id_tmp.lastIndexOf('/')+1));
        String username = tr.select("span").text();
        int rank = Integer.parseInt(tr.children().first().text().replaceAll("[^0-9]",""));
        float acc = Float.parseFloat(tr.children().get(2).text().replaceAll("[^0-9.]",""));
        int pc = Integer.parseInt(tr.children().get(3).text().replace(",",""));
        int pp = Integer.parseInt(tr.children().get(4).text().replace(",",""));
        String country_tmp = tr.children().get(1).select("span").attr("style");
        String country = country_tmp.substring(country_tmp.indexOf("'") + 1,country_tmp.lastIndexOf("'"));
        country = country.substring(1);
        country = country.substring(country.indexOf("/"));
        country = country.toLowerCase();
        country = country.substring(1);
        User user = new User();
        user.setUserId(user_id);
        user.setUsername(username);
        user.setPpRank(rank);
        user.setPpRaw(pp);
        user.setAccuracy(acc);
        user.setPlaycount(pc);
        user.setCountry(country);
        return user;
    }
    public static Player parsePlayerOld(Element tr){
        if(tr == null)
            return null;
        return null;
    }
}
