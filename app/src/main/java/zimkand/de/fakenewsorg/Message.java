package zimkand.de.fakenewsorg;

public class Message {

    private String caption;
    private String content;
    private String pictureName;

    public Message(String caption)
    {
        this.caption = caption;
    }

    public Message(String caption,String content)
    {
        this.caption = caption;
        this.content = content;
    }

    public Message(String caption,String content, String pictureName)
    {
        this.caption = caption;
        this.content = content;
        this.pictureName = pictureName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
