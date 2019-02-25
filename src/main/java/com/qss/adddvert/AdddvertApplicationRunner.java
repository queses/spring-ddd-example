package com.qss.adddvert;

import com.qss.adddvert.core.ModuleRegisterer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdddvertApplicationRunner implements ApplicationRunner {
	@Autowired
    private List<ModuleRegisterer> moduleRegisterers;

	public void run(ApplicationArguments arguments) throws Exception {
        for (ModuleRegisterer registerer : moduleRegisterers) {
            registerer.register();
        }
	}
}

