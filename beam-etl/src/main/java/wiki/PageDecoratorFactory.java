package wiki;

import java.util.function.Function;

import wiki.Page;
import wiki.PageDecorator;

public class PageDecoratorFactory {
    public static PageDecorator create(Page input) {
        return convert.apply(input);
    }

    private static Function<Page, PageDecorator> convert = new Function<Page, PageDecorator>() {
        public PageDecorator apply(Page input) {
            PageDecorator result = new PageDecorator();
            result.id = input.id;
            result.namespace = input.ns;
            result.revision = input.revision;
            result.title = input.title.toUpperCase();
            return new PageDecorator();
        }
    };
}