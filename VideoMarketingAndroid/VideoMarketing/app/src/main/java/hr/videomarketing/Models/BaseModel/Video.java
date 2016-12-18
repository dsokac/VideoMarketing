package hr.videomarketing.Models.BaseModel;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.videomarketing.Models.Converts;
import hr.videomarketing.MyWebService.Interfaces.OnVideoThumbnailDownloaded;
import hr.videomarketing.VideoMarketingApp;

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
    private String thumbnail_url;
    private Bitmap thumbnail;
    private String created_at;
    List<OnVideoThumbnailDownloaded> list;
    public Video(){
        createNullDefinedObject();
    }

    public Video(int id, String dmId, String title, String link, int likes, int dislikes, int points, int sponsored, int seen, int user_like, String thumbnail_url, Bitmap thumbnail, String created_at) {
        this.id = id;
        this.dmId = dmId;
        this.title = title;
        this.link = link;
        this.likes = likes;
        this.dislikes = dislikes;
        this.points = points;
        this.sponsored = sponsored;
        this.seen = seen;
        this.user_like = user_like;
        this.thumbnail_url = thumbnail_url;
        this.thumbnail = thumbnail;
        this.created_at = created_at;
    }

    public Video(int id, String title, String link, int likes, int dislikes, int points, int seen, int sponsored, String created_at, int user_like, String thumbnail_url) {
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
        this.thumbnail_url = thumbnail_url;
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
        Video video = new Video();
        if(json.length() > 0){
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
                video.setThumbnailUrl(json.getString("thumbnail_url"));
                video.setCreated_at(json.getString("created_at"));
                return video;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new Video().createNullDefinedObject();
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

    public List<OnVideoThumbnailDownloaded> getMyView(){
        return list;
    }


    public void setTitle(String title) {
        this.title = title;
    }
    public void setDmId(String dmId){
        this.dmId = dmId;
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


    public void setMyViewListener(OnVideoThumbnailDownloaded l){
        this.list.add(l);
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailUrl() {
        return thumbnail_url;
    }

    public void setThumbnailUrl(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
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
        if (!getDmID().equals(video.getDmID()))return false;
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
        log("title>"+title);
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
                ", \"thumbnail_url\":\""+thumbnail_url+'\"'+
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
    private void setListenerList(List<OnVideoThumbnailDownloaded> list){
        this.list = list;
    }

    @Override
    public String toJSON() {
        log("toJson>"+toString());
        return toString();
    }

    @Override
    public Video createNullDefinedObject() {
        setId(0);
        setTitle("");
        setLink("");
        setDmId("");
        setUserLike(0);
        setSeen(0);
        setUserLike(0);
        setLikes(0);
        setListenerList(new ArrayList<OnVideoThumbnailDownloaded>());
        return this;
    }

    @Override
    public boolean isNullObject() {
        return equals(new Video().createNullDefinedObject());
    }

    public void thumbnailDownloaded() {
        for (OnVideoThumbnailDownloaded l:list) {
            l.onImageDownloadedSuccessful();
        }
    }
    private void log(String t){
        VideoMarketingApp.log("VideoClass>"+t);
    }
}
