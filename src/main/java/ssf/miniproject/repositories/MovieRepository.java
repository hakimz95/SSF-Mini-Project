package ssf.miniproject.repositories;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ssf.miniproject.model.Movies;
import ssf.miniproject.model.User;

@Repository
public class MovieRepository {
    private static final Logger logger = LoggerFactory.getLogger(MovieRepository.class);

    @Autowired
    @Qualifier("redis")
    RedisTemplate<String, Object> redisTemplate;

    public Optional<User> getUser(String username) {
        User user = (User) redisTemplate.opsForValue().get(username);
        if (null != user) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public void createUser(String username) {
        boolean userExists = redisTemplate.hasKey(username);
        logger.info(username + " exists:" + userExists);
        if (!userExists) {
            User user = new User();
            redisTemplate.opsForValue().set(username, user);
        }
    }

    public Map<Movies, String> getWatchlist(String username) {
        Optional<User> optCurrentUser = this.getUser(username);
        User currentUser = optCurrentUser.get();
        Map<Movies, String> currentWatchList = currentUser.getWatchlist();
        List<Entry<Movies, String>> entryList = new ArrayList<>(currentWatchList.entrySet());
        entryList.sort(Entry.comparingByValue());

        Map<Movies, String> sortedWatchList = new LinkedHashMap<>();
        for (int i = entryList.size() - 1; i >=0; i--) {
            sortedWatchList.put(entryList.get(i).getKey(), entryList.get(i).getValue());
        }

        return sortedWatchList;
    }

    public void addMovie(String username, Movies movies) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        date.setTime(date.getTime() + TimeUnit.HOURS.toMillis(8));
        String dateTime = dateFormat.format(date);
        logger.info("dateTime: " + dateTime);
        movies.setAddedDateTime(dateTime);
        boolean userExists = redisTemplate.hasKey(username);
        logger.info(username + " exists:" + userExists);

        if (!userExists) {
            Map<Movies, String> watchlist = new HashMap<>();
            watchlist.put(movies, dateTime);
            User user = new User();
            user.setWatchlist(watchlist);
            redisTemplate.opsForValue().set(username, user);
        }
        else {
            Optional<User> optCurrentUser = this.getUser(username);
            User currentUser = optCurrentUser.get();
            Map<Movies, String> currentWatchlist = currentUser.getWatchlist();
            List<Movies> movieList = new ArrayList<Movies>(currentWatchlist.keySet());
            boolean movieExists = false;
            for (Movies m : movieList) {
                if (m.getId().equals(movies.getId())) {
                    movieExists = true;
                }
            }
            logger.info("Movie exists?: " + movieExists);

            if (!movieExists) {
                currentWatchlist.put(movies, dateTime);
                currentUser.setWatchlist(currentWatchlist);
                redisTemplate.opsForValue().set(username, currentUser);
            }
        }
    }

    public void deleteMovie(String username, Movies movies) {
        Optional<User> optCurrentUser = this.getUser(username);
        User currentUser = optCurrentUser.get();
        Map<Movies, String> currentWatchlist = currentUser.getWatchlist();
        List<Movies> movieList = new ArrayList<Movies>(currentWatchlist.keySet());
        for ( Movies m: movieList) {
            if (m.getId().equals(movies.getId())) {
                currentWatchlist.remove(m);
                logger.info("Movie deleted: " + m.toString());
            }
        }

        currentUser.setWatchlist(currentWatchlist);
        redisTemplate.opsForValue().setIfPresent(username, currentUser);
    }
}
