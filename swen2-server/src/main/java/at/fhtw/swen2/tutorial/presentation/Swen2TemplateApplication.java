package at.fhtw.swen2.tutorial.presentation;

import at.fhtw.swen2.tutorial.Swen2TemplateApplicationBoot;
import at.fhtw.swen2.tutorial.controller.Controller;
import at.fhtw.swen2.tutorial.presentation.events.ApplicationStartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Component
public class Swen2TemplateApplication extends Application {

    private Logger logger = LoggerFactory.getLogger(Swen2TemplateApplication.class);
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void start(Stage stage) throws Exception {
        logger.debug("Starting TutorialApplication");

        applicationContext.publishEvent(new ApplicationStartupEvent(this, stage));
    }

    @Override
    public void init() {
        logger.debug("Initializing Spring ApplicationContext");

        applicationContext = new SpringApplicationBuilder(Swen2TemplateApplicationBoot.class)
            .sources(Swen2TemplateApplicationBoot.class)
            .initializers(initializers())
            .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void stop() throws Exception {
        logger.debug("Stopping TutorialApplication");
        
        applicationContext.close();
        Platform.exit();
    }     

    ApplicationContextInitializer<GenericApplicationContext> initializers() { 
        return ac -> {
            ac.registerBean(Parameters.class, this::getParameters);
            ac.registerBean(HostServices.class, this::getHostServices);
        };
    }



    @Autowired
    Controller controller;

}
