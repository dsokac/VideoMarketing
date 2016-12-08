package hr.videomarketing.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import hr.videomarketing.Models.Converts;

/**
 * Created by bagy on 24.11.2016..
 */

public class Author implements Converts{
    private long id;
    private String name;
    private String surname;

    public Author() {
        createNullDefinedObject();
    }

    public Author(long id, String name, String surname) {
        setId(id);
        setName(name);
        setSurname(surname);
    }

    public static Author newInstance(JSONObject jsonAuthor) throws JSONException {
        if (jsonAuthor == null) return null;
        Author author = new Author();
        author.setId(Long.getLong(jsonAuthor.getString("id")));
        author.setName(jsonAuthor.getString("name"));
        author.setSurname(jsonAuthor.getString("surname"));
        return author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (getId() != author.getId()) return false;
        if (!getName().equals(author.getName())) return false;
        return getSurname().equals(author.getSurname());

    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toJSON() {
        return "Author{" +
                "\"id\":" + id +
                ", \"name\":\"" + name + '\"'+
                ",\"surname\":\"" + surname + '\"' +
                "}";
    }


    @Override
    public Author createNullDefinedObject() {
        setId(0);
        setName("");
        setSurname("");
        return this;
    }

    @Override
    public boolean isNullObject() {
        return this.equals(new Author().createNullDefinedObject());
    }
}
