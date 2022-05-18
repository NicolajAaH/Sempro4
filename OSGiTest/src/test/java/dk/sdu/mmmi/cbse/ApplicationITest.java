package dk.sdu.mmmi.cbse;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class ApplicationITest {
    @javax.inject.Inject
    BundleContext bundleContext;

    @ProbeBuilder
    public TestProbeBuilder probeConfiguration(TestProbeBuilder probe) {
        log.info("TestProbeBuilder gets called");
        probe.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");
        probe.setHeader(Constants.EXPORT_PACKAGE, "*");
        return probe;
    }

    /**
     * Tests that loading and unloading dynamically is possible, and that it contains the player-bundle
     * @throws Exception thrown if it fails
     */
    @Test
    public void Test_Loading_Unloading_Contains_Player() throws Exception {
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiPlayer")) {
                log.info("Bundle found: OSGiPlayer");
                if (bundle_started.getState() != Bundle.ACTIVE) {
                    log.error("BUNDLE NOT ACTIVE");
                    throw new Exception("NOT ACTIVE");
                }
                log.info("Bundle is active");
                bundle_started.stop();
                log.info("Bundle stopped");
                if (bundle_started.getState() != Bundle.RESOLVED) {
                    log.error("BUNDLE NOT RESOLVED");
                    throw new Exception("NOT RESOLVED");
                }
                bundle_started.start();
                log.info("Bundle started");
                if (bundle_started.getState() != Bundle.ACTIVE) {
                    log.error("BUNDLE NOT ACTIVE");
                    throw new Exception("NOT ACTIVE");
                }
                bundle_started.uninstall();
                log.info("Bundle uninstalled");
            }
        }
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiPlayer")) {
                log.error("BUNDLE IS NOT UNINSTALLED");
                throw new Exception("IS NOT UNINSTALLED");
            }
        }
        bundleContext.installBundle("file:bundles/OSGiPlayer.jar");
        log.info("Bundle installed");
        boolean installed = false;
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiPlayer")) {
                installed = true;
                break;
            }
        }
        if(!installed){
            log.error("BUNDLE NOT INSTALLED");
            throw new Exception("NOT INSTALLED");
        }
    }

    /**
     * Tests that loading and unloading dynamically is possible, and that it contains the enemy-bundle
     * @throws Exception thrown if it fails
     */
    @Test
    public void Test_Loading_Unloading_Contains_Enemy() throws Exception {
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiEnemy")) {
                log.info("Bundle found: OSGiEnemy");
                if (bundle_started.getState() != Bundle.ACTIVE) {
                    log.error("BUNDLE NOT ACTIVE");
                    throw new Exception("NOT ACTIVE");
                }
                bundle_started.stop();
                log.info("Bundle stopped");
                if (bundle_started.getState() != Bundle.RESOLVED) {
                    log.error("BUNDLE NOT RESOLVED");
                    throw new Exception("NOT RESOLVED");
                }
                bundle_started.start();
                log.info("Bundle started");
                if (bundle_started.getState() != Bundle.ACTIVE) {
                    log.error("BUNDLE NOT ACTIVE");
                    throw new Exception("NOT ACTIVE");
                }
                bundle_started.uninstall();
                log.info("Bundle uninstalled");
            }
        }
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiEnemy")) {
                log.error("BUNDLE IS NOT UNINSTALLED");
                throw new Exception("IS NOT UNINSTALLED");
            }
        }
        bundleContext.installBundle("file:bundles/OSGiEnemy.jar");
        log.info("Bundle installed");
        boolean installed = false;
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiEnemy")) {
                installed = true;
                break;
            }
        }
        if(!installed){
            log.error("BUNDLE NOT INSTALLED");
            throw new Exception("NOT INSTALLED");
        }
    }

    /**
     * Tests that loading and unloading dynamically is possible, and that it contains the projectile-bundle
     * @throws Exception thrown if it fails
     */
    @Test
    public void Test_Loading_Unloading_Contains_Projectile() throws Exception {
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiProjectile")) {
                if (bundle_started.getState() != Bundle.ACTIVE) {
                    log.error("BUNDLE NOT ACTIVE");
                    throw new Exception("NOT ACTIVE");
                }
                bundle_started.stop();
                log.info("Bundle stopped");
                if (bundle_started.getState() != Bundle.RESOLVED) {
                    log.error("BUNDLE NOT RESOLVED");
                    throw new Exception("NOT RESOLVED");
                }
                bundle_started.start();
                log.info("Bundle started");
                if (bundle_started.getState() != Bundle.ACTIVE) {
                    log.error("BUNDLE NOT ACTIVE");
                    throw new Exception("NOT ACTIVE");
                }
                bundle_started.uninstall();
                log.info("Bundle uninstalled");
            }
        }
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiProjectile")) {
                log.error("BUNDLE IS NOT UNINSTALLED");
                throw new Exception("IS NOT UNINSTALLED");
            }
        }
        bundleContext.installBundle("file:bundles/OSGiProjectile.jar");
        log.info("Bundle installed");
        boolean installed = false;
        for (Bundle bundle_started : bundleContext.getBundles()) {
            if (bundle_started.getHeaders().get("Bundle-Name") != null && bundle_started.getHeaders().get("Bundle-Name").equals("OSGiProjectile")) {
                installed = true;
                break;
            }
        }
        if(!installed){
            log.error("BUNDLE NOT INSTALLED");
            throw new Exception("NOT INSTALLED");
        }
    }

    /**
     * Checks that the map-bundle exists
     * @throws Exception thrown if it fails
     */
    @Test
    public void containsMap() throws Exception {
        for (Bundle bundle : bundleContext.getBundles()){
            if(bundle.getHeaders().get("Bundle-Name") != null && bundle.getHeaders().get("Bundle-Name").equals("OSGiMap")){
                log.info("CONTAINS OSGiMAP");
                return;
            }
        }
        log.error("DOES NOT CONTAIN OSGiMAP");
        throw new Exception("DOES NOT CONTAIN MAP");
    }

    /**
     * Tests that the Core-bundle exists
     * @throws Exception thrown if fails
     */
    @Test
    public void containsCore() throws Exception {
        for (Bundle bundle : bundleContext.getBundles()){
            if(bundle.getHeaders().get("Bundle-Name") != null && bundle.getHeaders().get("Bundle-Name").equals("OSGiCore")){
                log.info("CONTAINS OSGiCORE");
                return;
            }
        }
        log.error("DOES NOT CONTAIN OSGiCORE");
        throw new Exception("DOES NOT CONTAIN CORE");
    }

    private static String toFileURI(String path) {
        return new File(path).toURI().toString();
    }

    //importing all the bundles
    @Configuration
    public static Option[] configuration() {
        return options(
                provision(
                        bundle(toFileURI("bundles/OSGiLibGDX.jar")),
                        bundle(toFileURI("bundles/OSGiPlayer.jar")),
                        bundle(toFileURI("bundles/OSGiEnemy.jar")),
                        bundle(toFileURI("bundles/OSGiProjectile.jar")),
                        bundle(toFileURI("bundles/OSGiCore.jar")),
                        bundle(toFileURI("bundles/OSGiMap.jar")),
                        mavenBundle("org.osgi", "org.osgi.compendium", "4.2.0"),
                        mavenBundle("org.ops4j.pax.swissbox", "pax-swissbox-tinybundles",
                                "1.0.0")
                )
        );
    }

}
