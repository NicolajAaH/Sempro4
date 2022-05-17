package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import java.util.Arrays;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class ApplicationITest {
    @javax.inject.Inject
    BundleContext bundleContext;

    @ProbeBuilder
    public TestProbeBuilder probeConfiguration(TestProbeBuilder probe) {
        System.out.println("TestProbeBuilder gets called");
        probe.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");
        probe.setHeader(Constants.EXPORT_PACKAGE, "com.bssys.ebpp.paxexam");
        return probe;
    }

    @Configuration
    public Option[] config() {
        return options(
               // mavenBundle().artifactId("osgi.core").groupId("org.osgi").version("8.0.0"),
                //mavenBundle().artifactId("org.osgi.compendium").groupId("org.osgi").version("5.0.0"),
                mavenBundle().artifactId("OSGiLibGDX").groupId("dk.sdu.mmmi.build").version("1.0-SNAPSHOT"),
                mavenBundle().artifactId("OSGiCommon").groupId("dk.sdu.mmmi.build").version("1.0-SNAPSHOT")
                );
    }

    @Test
    public void testtt() {
        System.out.println("BUNDLES");
        System.out.println(Arrays.toString(bundleContext.getBundles()));
        System.out.println(bundleContext.getService(bundleContext.getServiceReference(IEntityProcessingService.class)));
    }

}
