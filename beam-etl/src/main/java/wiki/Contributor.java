package wiki;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "contributor")
class Contributor {
    public Integer id;
    public String username;
}