package wiki;

// import java.io.Serializable;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "page")
public class Page {
    public Integer id;
    public String ns;
    public String title;
    public Revision revision;
}