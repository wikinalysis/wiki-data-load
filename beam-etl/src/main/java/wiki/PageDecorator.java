package wiki;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "page")
public class PageDecorator {
    public Integer id;
    public Integer ns;
    public String title;
    public Revision revision;
}