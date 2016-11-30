package hr.videomarketing.Models.BaseModel;

import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.videomarketing.DMVideoView.DMWebVideoView;
import hr.videomarketing.Models.Converts;

import static hr.videomarketing.VideoMarketingApp.log;

/**
 * Created by bagy on 7.11.2016..
 */

public class Video implements Converts{
    private int id;
    private String dmId;
    private String title;
    private String link;
    private int likes;
    private int dislikes;
    private int points;
    private int sponsored;
    private int seen;
    private int user_like;
    private String created_at;
    public Video(){

    }

    public Video(int id, String title, String link, int likes, int dislikes, int points, int seen, int sponsored, String created_at, int user_like) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.likes = likes;
        this.dislikes = dislikes;
        this.points = points;
        this.user_like = user_like;
        this.seen = seen;
        this.sponsored = sponsored;
        this.created_at = created_at;
    }
    public static Video newInstance(String json) {
        try {
            return newInstance(new JSONObject(json));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Video newInstance(JSONObject json) {
        if(json.length() > 0){
            Video video = new Video();
            try {
                video.setId(json.getInt("id"));
                video.setTitle(json.getString("title"));
                video.setLink(json.getString("link"));
                video.setLikes(json.getInt("likes"));
                video.setDislikes(json.getInt("dislikes"));
                video.setPoints(json.getInt("points"));
                video.setSeen(json.getInt("seen"));
                video.setUserLike(json.getInt("user_like"));
                video.setSponsored(json.getInt("sponsored"));
                video.setCreated_at(json.getString("created_at"));
                return video;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;

        Pattern pattern = Pattern.compile("(x.{6})");
        Matcher match1 = pattern.matcher(link);
        if(match1.find()){
            this.dmId = match1.group();
        }
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getUserLike() {
        return user_like;
    }

    public void setUserLike(int user_like) {
        this.user_like = user_like;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getDmID() {return dmId;}

    public int getSponsored() {
        return sponsored;
    }

    public void setSponsored(int sponsored) {
        this.sponsored = sponsored;
    }

    public int getSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Video video = (Video) o;

        if (getId() != video.getId()) return false;
        if (!getTitle().equals(video.getTitle())) return false;
        if (!getLink().equals(video.getLink())) return false;
        return getCreated_at() != null ? getCreated_at().equals(video.getCreated_at()) : video.getCreated_at() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + (getCreated_at() != null ? getCreated_at().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"dmId\":\""+dmId+'\"'+
                ", \"title\":\"" + title + '\"' +
                ", \"link\":\"" + link + '\"' +
                ", \"likes\":\"" + likes + '\"' +
                ", \"user_like\":\""+user_like+'\"'+
                ", \"seen\":\""+seen+'\"'+
                ", \"dislikes\":\"" + dislikes + '\"' +
                ", \"points\":\"" + points + '\"' +
                ", \"sponsored\":\""+sponsored+'\"'+
                ", \"created_at\":\"" + created_at + '\"' +
                '}';
    }
    public static List<Video> getVideoList(JSONArray jsonArray){
        List<Video> list = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(newInstance(jsonArray.getJSONObject(i)));
            }
        }catch (JSONException jsonExc){
            jsonExc.printStackTrace();
        }
        return list;
    }

    @Override
    public String toJSON() {
        return toString();
    }
}
