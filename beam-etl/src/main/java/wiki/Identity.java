package wiki;

import java.io.IOException;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.io.xml.XmlIO;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;

import wiki.models.WikiPage;
import wiki.models.Page;

public class Identity {

    static String ROOT_ELEMENT = "mediawiki";
    static String RECORD_ELEMENT = "page";
    private static PCollection<Page> setCoder;

    public interface IdentityOptions extends PipelineOptions {
        @Description("Path of the file to read from")
        @Default.String("../tnwiki-20190801-pages-meta-history.xml")
        String getInputFile();

        void setInputFile(String value);

        @Description("Path of the file to write to")
        @Default.String("../result")
        String getOutput();

        void setOutput(String value);
    }

    public static class ArticleNamespaceFilterFn extends SimpleFunction<WikiPage, Boolean> {
        private static final long serialVersionUID = 1L;

        @Override
        public Boolean apply(WikiPage input) {
            return input.ns == 0;
        }
    }

    public static class DecoratePagesFn extends SimpleFunction<WikiPage, Page> {
        private static final long serialVersionUID = 1L;

        public Page apply(WikiPage input) {
            return WikiConverters.convertPage.apply(input);
        }
    }

    static void runIdentity(IdentityOptions options) throws IOException {
        Pipeline pipeline = Pipeline.create(options);

        XmlIO.Read<WikiPage> xmlRead = XmlIO.<WikiPage>read().from(options.getInputFile()).withRootElement(ROOT_ELEMENT)
                .withRecordElement(RECORD_ELEMENT).withRecordClass(WikiPage.class);

        XmlIO.Write<Page> xmlWrite = XmlIO.<Page>write().withRootElement(ROOT_ELEMENT).withRecordClass(Page.class)
                .to(options.getOutput());

        PCollection<WikiPage> input = pipeline.apply("Read XML", xmlRead).apply("Filter Namespaces",
                Filter.by(new ArticleNamespaceFilterFn()));

        setCoder = input.apply("Add Attributes", MapElements.via(new DecoratePagesFn()))
                .setCoder(AvroCoder.of(Page.class));

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