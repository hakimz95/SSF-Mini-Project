package ssf.miniproject.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    private String username;
    private Map<Movies, String> watchlist = new HashMap<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<Movies, String> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(Map<Movies, String> watchlist) {
        this.watchlist = watchlist;
    }
}
