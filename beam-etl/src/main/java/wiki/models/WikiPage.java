package wiki.models;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "page")
public class WikiPage {
    public Integer id;
    public Integer ns;
    public String title;

    @XmlElement(name = "revision")
    public List<WikiRevision> revision;
}