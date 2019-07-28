package wiki;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "revision")
public class Revision {
    public Integer id;
    public Integer parentId;
    public String timestamp;
    public String comment;
    public String model;
    public String format;
    public String sha1;
    // public String text;
    public String minor;
    public Contributor contributor;

}