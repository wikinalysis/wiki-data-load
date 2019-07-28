package wiki;

import wiki.Page;
import org.apache.beam.sdk.io.xml.XmlIO;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public class Identity {

    public interface IdentityOptions extends PipelineOptions {

        /**
         * By default, this example reads from a public dataset containing the text of
         * King Lear. Set this option to choose a different input file or glob.
         */
        @Description("Path of the file to read from")
        @Default.String("../tnwiki-20190720-pages-articles-multistream.xml")
        String getInputFile();

        void setInputFile(String value);

        /** Set this required option to specify where to write the output. */
        @Description("Path of the file to write to")
        @Default.String("../result")
        String getOutput();

        void setOutput(String value);
    }

    public static void main(String[] args) {
        IdentityOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(IdentityOptions.class);
        Pipeline p = Pipeline.create(options);

        p.apply(XmlIO.<Page>read().from(options.getInputFile()).withRootElement("mediawiki").withRecordElement("page")
                .withRecordClass(Page.class))
                .apply(XmlIO.<Page>write().withRootElement("mediawiki").withRecordClass(Page.class)
                        .to(options.getOutput()));
        p.run().waitUntilFinish();
    }
}