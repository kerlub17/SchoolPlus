package at.kaindorf.schoolplus_backend.beans;

public class Task
{
    private final long id;
    private final String content;

    public Task(long id, String content)
    {
        this.id = id;
        this.content = content;
    }

    public long getId()
    {
        return id;
    }

    public String getContent()
    {
        return content;
    }
}
