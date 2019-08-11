package wiki;

import wiki.Page;

import org.apache.beam.sdk.io.xml.XmlIO;

import java.io.IOException;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;

public class Identity {

    static String ROOT_ELEMENT = "mediawiki";
    static String RECORD_ELEMENT = "page";
    private static PCollection<PageDecorator> setCoder;

    public interface IdentityOptions extends PipelineOptions {
        @Description("Path of the file to read from")
        @Default.String("../tnwiki-20190720-pages-articles-multistream.xml")
        String getInputFile();

        void setInputFile(String value);

        @Description("Path of the file to write to")
        @Default.String("../result")
        String getOutput();

        void setOutput(String value);
    }

    public static class ArticleNamespaceFilterFn extends SimpleFunction<Page, Boolean> {
        private static final long serialVersionUID = 1L;

        @Override
        public Boolean apply(Page input) {
            return input.ns == 0;
        }
    }

    public static class DecoratePagesFn extends SimpleFunction<Page, PageDecorator> {
        private static final long serialVersionUID = 1L;

        public PageDecorator apply(Page input) {
            return PageDecoratorFactory.create(input);
        }
    }

    static void runIdentity(IdentityOptions options) throws IOException {
        Pipeline pipeline = Pipeline.create(options);

        XmlIO.Read<Page> xmlRead = XmlIO.<Page>read().from(options.getInputFile()).withRootElement(ROOT_ELEMENT)
                .withRecordElement(RECORD_ELEMENT).withRecordClass(Page.class);

        XmlIO.Write<PageDecorator> xmlWrite = XmlIO.<PageDecorator>write().withRootElement(ROOT_ELEMENT)
                .withRecordClass(PageDecorator.class).to(options.getOutput());

        PCollection<Page> input = pipeline.apply("Read XML", xmlRead).apply("Filter Namespaces",
                Filter.by(new ArticleNamespaceFilterFn()));

        setCoder = input.apply("Add Attributes", MapElements.via(new DecoratePagesFn()))
                .setCoder(AvroCoder.of(PageDecorator.class));

        setCoder.apply("Write XML", xmlWrite);

        PipelineResult result = pipeline.run();

        try {
            result.waitUntilFinish();
        } catch (Exception exc) {
            result.cancel();
        }
    }

    public static void main(String[] args) throws IOException {
        IdentityOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(IdentityOptions.class);
        runIdentity(options);
    }
}