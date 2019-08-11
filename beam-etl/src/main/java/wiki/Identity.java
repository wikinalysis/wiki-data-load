package wiki;

import wiki.Page;

import org.apache.beam.sdk.io.xml.XmlIO;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.SerializableFunction;

public class Identity {

    static String ROOT_ELEMENT = "mediawiki";
    static String RECORD_ELEMENT = "page";

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

    static void runIdentity(IdentityOptions options) {
        Pipeline p = Pipeline.create(options);

        XmlIO.Read<Page> xmlRead = XmlIO.<Page>read().from(options.getInputFile()).withRootElement(ROOT_ELEMENT)
                .withRecordElement(RECORD_ELEMENT).withRecordClass(Page.class);

        XmlIO.Write<Page> xmlWrite = XmlIO.<Page>write().withRootElement(ROOT_ELEMENT).withRecordClass(Page.class)
                .to(options.getOutput());

        p.apply(xmlRead).apply(Filter.by((SerializableFunction<Page, Boolean>) input -> input.ns == 0)).apply(xmlWrite);
        // p.apply(xmlRead).apply(xmlWrite);
        p.run().waitUntilFinish();
    }

    public static void main(String[] args) {
        IdentityOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(IdentityOptions.class);
        runIdentity(options);
    }
}

// PCollection<String> allStrings = Create.of("Hello", "world", "hi");
// PCollection<String> longStrings = allStrings
// .apply(Filter.by(new SerializableFunction<String, Boolean>() {
// @Override
// public Boolean apply(String input) {
// return input.length() > 3;
// }
// }));