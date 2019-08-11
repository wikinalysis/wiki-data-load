package wiki;

import javax.xml.bind.annotation.*;

import org.apache.avro.reflect.Nullable;

@XmlRootElement(name = "revision")
public class Revision {

    public Integer id;
    public String timestamp;
    public String model;
    public String format;
    public String sha1;
    // public String text;
    // public Contributor contributor;

    @Nullable
    public Integer parentId;

    @Nullable
    public String minor;

    @Nullable
    public String comment;

}