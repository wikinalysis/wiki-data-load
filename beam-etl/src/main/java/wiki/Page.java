package wiki;

// import java.io.Serializable;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "page")
public class Page {
    public Integer id;
    public Integer ns;
    public String title;
    public Revision revision;
}