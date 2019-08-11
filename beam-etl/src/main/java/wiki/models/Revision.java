package wiki.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "revision")
public class Revision {

    public Integer id;
    public String timestamp;
    public String model;
    public String format;
    public String sha1;
    public boolean minor;
    public Contributor contributor;
    public String comment;

}