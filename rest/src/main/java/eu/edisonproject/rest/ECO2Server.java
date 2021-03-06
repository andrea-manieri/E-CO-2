/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.edisonproject.rest;

import static eu.edisonproject.rest.ECO2Controller.courseAverageFolder;
import static eu.edisonproject.rest.ECO2Controller.courseClassisifcationFolder;
import static eu.edisonproject.rest.ECO2Controller.courseProfileFolder;
import static eu.edisonproject.rest.ECO2Controller.cvAverageFolder;
import static eu.edisonproject.rest.ECO2Controller.cvClassisifcationFolder;
import static eu.edisonproject.rest.ECO2Controller.cvProfileFolder;
import static eu.edisonproject.rest.ECO2Controller.jobAverageFolder;
import static eu.edisonproject.rest.ECO2Controller.jobClassisifcationFolder;
import static eu.edisonproject.rest.ECO2Controller.jobProfileFolder;
import static eu.edisonproject.rest.ECO2Controller.propertiesFile;

import eu.edisonproject.utility.file.ConfigHelper;
import eu.edisonproject.utility.file.MyProperties;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 *
 * @author S. Koulouzis
 */
public class ECO2Server {

    public static void main(String[] args) {
        Thread jobWatcher, jobAvgWatcher, cvWatcher, courseWatcher, jobProfileWatcher, cvAvgWatcher, cvProfileWatcher, courseAvgWatcher, courseProfileWatcher;
        Server server = null;
        try {
            ECO2Controller.initPaths();
            jobWatcher = startTaskWatcher(jobClassisifcationFolder.getAbsolutePath());
            jobWatcher.start();

            jobAvgWatcher = startTaskWatcher(jobAverageFolder.getAbsolutePath());
            jobAvgWatcher.start();

            jobProfileWatcher = startTaskWatcher(jobProfileFolder.getAbsolutePath());
            jobProfileWatcher.start();

            cvWatcher = startTaskWatcher(cvClassisifcationFolder.getAbsolutePath());
            cvWatcher.start();

            cvAvgWatcher = startTaskWatcher(cvAverageFolder.getAbsolutePath());
            cvAvgWatcher.start();

            cvProfileWatcher = startTaskWatcher(cvProfileFolder.getAbsolutePath());
            cvProfileWatcher.start();

            courseWatcher = startTaskWatcher(courseClassisifcationFolder.getAbsolutePath());
            courseWatcher.start();

            courseAvgWatcher = startTaskWatcher(courseAverageFolder.getAbsolutePath());
            courseAvgWatcher.start();

            courseProfileWatcher = startTaskWatcher(courseProfileFolder.getAbsolutePath());
            courseProfileWatcher.start();

            server = startServer(args);
            server.start();
            jobWatcher.join();
            cvWatcher.join();
            courseWatcher.join();
            jobAvgWatcher.join();
            jobProfileWatcher.join();
            server.join();
        } catch (IOException ex) {
            Logger.getLogger(ECO2Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ECO2Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ECO2Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (server != null) {
                server.destroy();
            }
        }
    }

    private static Server startServer(String[] args) throws IOException {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        MyProperties props = null;
        Integer port;
        String path;
        if (args != null) {
            path = args[0];
        } else {
            path = System.getProperty("user.home") + File.separator + "workspace"
                    + File.separator + "E-CO-2" + File.separator + "etc" + File.separator + "configure.properties";
        }
        propertiesFile = new File(path);
        props = ConfigHelper.getProperties(path);
        port = Integer.valueOf(props.getProperty("e-co-2.server.port", "9999"));

        Server jettyServer = new Server(port);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "eu.edisonproject.rest");

        // The filesystem paths we will map
        String docPath = System.getProperty("user.home") + File.separator + "workspace"
                + File.separator + "E-CO-2" + File.separator + "rest" + File.separator + "target" + File.separator + "docs" + File.separator + "apidocs";
//    String pwdPath = System.getProperty("user.dir");

//    // Setup the basic application "context" for this application at "/"
//    // This is also known as the handler tree (in jetty speak)
        context.setResourceBase(docPath);
        context.setContextPath("/");
        jettyServer.setHandler(context);
        // add special pathspec of "/home/" content mapped to the homePath
        ServletHolder holderHome = new ServletHolder("static-home", DefaultServlet.class);
        jerseyServlet.setInitParameter("resourceBase", docPath);
        holderHome.setInitParameter("dirAllowed", "true");
        holderHome.setInitParameter("pathInfoOnly", "true");
        context.addServlet(holderHome, "/doc/*");

        // Lastly, the default servlet for root content (always needed, to satisfy servlet spec)
        // It is important that this is last.
        ServletHolder holderPwd = new ServletHolder("default", DefaultServlet.class);
        holderPwd.setInitParameter("dirAllowed", "true");
        context.addServlet(holderPwd, "/");

        return jettyServer;
    }

    private static Thread startTaskWatcher(String dir) throws IOException, InterruptedException {

        Runnable folderWatcherRunnable = new FolderWatcherRunnable(dir);

        return new Thread(folderWatcherRunnable);

    }

}
