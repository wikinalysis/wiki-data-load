package wiki;

public class PageDecoratorFactory {
    public static PageDecorator create(Page input) {
        return new PageDecorator();
    }
}