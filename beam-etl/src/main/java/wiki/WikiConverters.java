package wiki;

import java.util.function.Function;
import java.util.stream.Collectors;

import wiki.models.WikiPage;
import wiki.models.WikiRevision;
import wiki.models.WikiContributor;
import wiki.models.Revision;
import wiki.models.Page;
import wiki.models.Contributor;

public class WikiConverters {
    public static Function<WikiPage, Page> convertPage = new Function<WikiPage, Page>() {
        public Page apply(WikiPage input) {
            Page result = new Page();
            result.id = input.id;
            result.namespace = input.ns;
            result.title = input.title;
            result.revision = input.revision.stream().map(convertRevision).collect(Collectors.toList());
            return result;
        }
    };

    public static Function<WikiRevision, Revision> convertRevision = new Function<WikiRevision, Revision>() {
        public Revision apply(WikiRevision input) {
            Revision result = new Revision();
            result.id = input.id;
            result.timestamp = input.timestamp;
            result.model = input.model;
            result.format = input.format;
            result.sha1 = input.sha1;
            result.minor = input.minor != null;
            result.comment = input.comment != null ? input.comment : "";
            result.contributor = convertContributor.apply(input.contributor);
            return result;
        }
    };

    public static Function<WikiContributor, Contributor> convertContributor = new Function<WikiContributor, Contributor>() {
        public Contributor apply(WikiContributor input) {
            Contributor result = new Contributor();
            result.id = input.id == null ? 0 : input.id;
            result.username = input.ip != null ? input.ip : input.username != null ? input.username : "";
            return result;
        }
    };
}