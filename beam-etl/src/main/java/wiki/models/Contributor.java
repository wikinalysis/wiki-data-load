package wiki.models;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "contributor")
public class Contributor {
    public Integer id;
    public String username;
}