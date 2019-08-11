package wiki.models;

// import java.io.Serializable;
import wiki.models.Revision;

import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "page")
public class Page {

    public Integer id;
    public Integer namespace;
    public String title;

    @XmlElement(name = "revision")
    public List<Revision> revision;

}