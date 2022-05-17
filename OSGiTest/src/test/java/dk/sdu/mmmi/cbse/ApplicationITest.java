package dk.sdu.mmmi.cbse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import java.io.File;

import static org.ops4j.pax.exam.CoreOptions.*;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class ApplicationITest {
    @javax.inject.Inject
    BundleContext bundleContext;

    @ProbeBuilder
    public TestProbeBuilder probeConfiguration(TestProbeBuilder probe) {
        System.out.println("TestProbeBuilder gets called");
        probe.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");
        probe.setHeader(Constants.EXPORT_PACKAGE, "*");
        return probe;
    }


    @Test
    public void Test_Loading_Unloading_Player() throws Exception {
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiPlayer")) {
                if (bundle_started.getState() != Bundle.ACTIVE) {
                    throw new Exception("NOT ACTIVE");
                }
                bundle_started.stop();
                if (bundle_started.getState() != Bundle.RESOLVED) {
                    throw new Exception("NOT RESOLVED");
                }
                bundle_started.start();
                if (bundle_started.getState() != Bundle.ACTIVE) {
                    throw new Exception("NOT ACTIVE");
                }
                bundle_started.uninstall();
            }
        }
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiPlayer")) {
                throw new Exception("IS NOT UNINSTALLED");
            }
        }
        bundleContext.installBundle("file:bundles/OSGiPlayer.jar");
        boolean installed = false;
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiPlayer")) {
                installed = true;
                break;
            }
        }
        if(!installed)
            throw new Exception("NOT INSTALLED");
    }

    @Test
    public void Test_Loading_Unloading_Enemy() throws Exception {
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiEnemy")) {
                if (bundle_started.getState() != Bundle.ACTIVE) {
                    throw new Exception("NOT ACTIVE");
                }
                bundle_started.stop();
                if (bundle_started.getState() != Bundle.RESOLVED) {
                    throw new Exception("NOT RESOLVED");
                }
                bundle_started.start();
                if (bundle_started.getState() != Bundle.ACTIVE) {
                    throw new Exception("NOT ACTIVE");
                }
                bundle_started.uninstall();
            }
        }
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiEnemy")) {
                throw new Exception("IS NOT UNINSTALLED");
            }
        }
        bundleContext.installBundle("file:bundles/OSGiEnemy.jar");
        boolean installed = false;
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiEnemy")) {
                installed = true;
                break;
            }
        }
        if(!installed)
            throw new Exception("NOT INSTALLED");
    }

    @Test
    public void Test_Loading_Unloading_Projectile() throws Exception {
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiProjectile")) {
                if (bundle_started.getState() != Bundle.ACTIVE) {
                    throw new Exception("NOT ACTIVE");
                }
                bundle_started.stop();
                if (bundle_started.getState() != Bundle.RESOLVED) {
                    throw new Exception("NOT RESOLVED");
                }
                bundle_started.start();
                if (bundle_started.getState() != Bundle.ACTIVE) {
                    throw new Exception("NOT ACTIVE");
                }
                bundle_started.uninstall();
            }
        }
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiProjectile")) {
                throw new Exception("IS NOT UNINSTALLED");
            }
        }
        bundleContext.installBundle("file:bundles/OSGiProjectile.jar");
        boolean installed = false;
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiProjectile")) {
                installed = true;
                break;
            }
        }
        if(!installed)
            throw new Exception("NOT INSTALLED");
    }

    private static String toFileURI(String path) {
        return new File(path).toURI().toString();
    }
    @Configuration
    public static Option[] configuration() {
        return options(
                provision(
                        bundle(toFileURI("bundles/OSGiPlayer.jar")),
                        bundle(toFileURI("bundles/OSGiEnemy.jar")),
                        bundle(toFileURI("bundles/OSGiProjectile.jar")),
                        mavenBundle("org.osgi", "org.osgi.compendium", "4.2.0"),
                        mavenBundle("org.ops4j.pax.swissbox", "pax-swissbox-tinybundles",
                                "1.0.0")
                )
        );
    }

}
