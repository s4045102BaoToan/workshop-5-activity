package app;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;


/**
 * Main Application Class.
 * <p>
 * Running this class as regular java application will start the 
 * Javalin HTTP Server and our web application.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class App {

    public static final int         JAVALIN_PORT    = 7001;
    public static final String      CSS_DIR         = "css/";
    public static final String      IMAGES_DIR      = "images/";

    public static void main(String[] args) {
        // Create our HTTP server and listen in port 7000
       
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));
            
            // Uncomment this if you have files in the CSS Directory
            config.addStaticFiles(CSS_DIR);

            // Uncomment this if you have files in the Images Directory
            config.addStaticFiles(IMAGES_DIR);
        }).start(JAVALIN_PORT);


        // Configure Web Routes
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        // All webpages are listed here as GET pages
        app.get(PageIndex.URL, new PageIndex());
        app.get(PageMission.URL, new PageMission());
        app.get(Global.URL, new Global());
        app.get(Country.URL, new Country());
        app.get(State.URL, new State());
        app.get(City.URL, new City());
        app.get(AVGTemp.URL, new AVGTemp());
        app.get(PeriodSimilar.URL, new PeriodSimilar());
        app.get(test.URL, new test());
        app.get(NEW.URL, new NEW());

        //post 
        app.post(Global.URL, new Global());
        app.post(Country.URL, new Country());
        app.post(State.URL, new State());
        app.post(AVGTemp.URL, new AVGTemp());
        app.post(City.URL, new City());
        app.post(PeriodSimilar.URL, new PeriodSimilar());
        app.post(test.URL, new test());
        app.post(NEW.URL, new NEW());
    }
        

}
