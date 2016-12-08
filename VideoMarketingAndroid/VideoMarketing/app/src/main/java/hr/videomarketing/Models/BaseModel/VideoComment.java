package hr.videomarketing.Models.BaseModel;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import hr.videomarketing.Models.Converts;

/**
 * Created by bagy on 24.11.2016..
 */

public class VideoComment implements Converts{
    private long id;
    private Author author;
    private String content;
    private String created_at;

    public VideoComment() {
        createNullDefinedObject();
    }

    public VideoComment(long id, Author author, String content, String created_at) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public static VideoComment newInstance(JSONObject jsonComment) throws JSONException{
        if(jsonComment == null) return null;
        VideoComment comment = new VideoComment();
        comment.setId(Long.getLong(jsonComment.getString("id")));
        comment.setAuthor(Author.newInstance(jsonComment.getJSONObject("author")));
        comment.setContent(jsonComment.getString("content"));
        comment.setCreated_at(jsonComment.getString("created_at"));
        return comment;
    }

    @Override
    public String toJSON() {
        return !this.isNullObject()?"{\"id\":"+Long.toString(id)+
        ", \"author\":\""+author.toJSON()+
        ", \"content\":\""+content+'\"'+
        ", \"created_at\":\""+created_at+'\"'+ "}":"";
    }

    @Override
    public VideoComment createNullDefinedObject() {
        setId(0);
        setAuthor(new Author());
        setContent("");
        setCreated_at("");
        return this;
    }

    @Override
    public boolean isNullObject() {
        return this.equals(new VideoComment());
    }
}
