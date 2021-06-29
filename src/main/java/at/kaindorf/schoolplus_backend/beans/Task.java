package at.kaindorf.schoolplus_backend.beans;

import java.sql.SQLOutput;

/**
 *
 * @author Luca Kern BHIF17
 */
public class Task implements Comparable<Task>
{
    private int id;
    private String name;
    private String subject;
    private String date;
    private String type;
    private boolean done;
    private int note=0;
    private int time;

    public Task(int id, String name, String subject, String date, String type, boolean done, int note, int time) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.date = date;
        this.type = type;
        this.done = done;
        this.note = note;
        this.time = time;
    }

    public Task() {
    }

    public Task(String str)
    {
        //id;name;subject;date;type;done;note;time
        String[] tokens = str.split(";");

        id=Integer.parseInt(tokens[0]);
        name=tokens[1];
        subject=tokens[2];
        date=tokens[3];
        type=tokens[4];
        done=Boolean.parseBoolean(tokens[5]);
        note=Integer.parseInt(tokens[6]);
        time=Integer.parseInt(tokens[7]);
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public boolean isDone() {
        return done;
    }

    public int getNote() {
        return note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @Override
    public String toString()
    {
        //id;name;subject;date;type;done;note;time
        return id+";"+name+";"+subject+";"+date+";"+type+";"+done+";"+note+";"+time;
    }

    public String fancyDate()
    {
        try
        {
            return date.substring(0,4)+"."+ date.substring(4,6)+"."+ date.substring(6);
        }
        catch(Exception e)
        {
            return date;
        }
    }

    public String fancyDone()
    {
        return (done?"JA":"NEIN");
    }

    public String toFrontend()
    {
        return "{\n" +
                "  \"id\":\"" + id + "\",\n" +
                "  \"name\":\"" + name + "\",\n" +
                "  \"subject\":\"" + subject + "\",\n" +
                "  \"date\":\"" + fancyDate() + "\",\n" +
                "  \"type\":\"" + type + "\",\n" +
                "  \"done\":\"" + fancyDone() + "\",\n" +
                "  \"note\":\"" + note + "\",\n" +
                "  \"time\":\"" + time + "\"\n" +
                "}\n";
    }

    @Override
    public int compareTo(Task o)
    {
        return this.getDate().compareTo(o.getDate());
    }


}
