package at.fhtw.swen2.tutorial.persistence;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInitializer implements InitializingBean {


    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
