package wiki.models;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "contributor")
public class WikiContributor {
    public Integer id;
    public String username;
    public String ip;
}