package wiki;

import java.io.Serializable;

// import java.io.Serializable;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "page")
public class PageDecorator implements Serializable {
    private static final long serialVersionUID = 7606139006303017414L;

    public Integer id;
    public Integer namespace;
    public String title;
    public Revision revision;

    public boolean equals(Object other) {
        return other == this;
    }
}