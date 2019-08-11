package wiki.models;

import javax.xml.bind.annotation.*;

import org.apache.avro.reflect.Nullable;

@XmlRootElement(name = "revision")
public class WikiRevision {

    public Integer id;
    public String timestamp;
    public String model;
    public String format;
    public String sha1;
    public String minor;
    public String text;
    public WikiContributor contributor;
    public String comment;

    @Nullable
    public Integer parentId;


}