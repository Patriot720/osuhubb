package example.cerki.osuhub;


import org.jsoup.nodes.Element;

import example.cerki.osuhub.API.POJO.User;
public class UserParser {
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

}
