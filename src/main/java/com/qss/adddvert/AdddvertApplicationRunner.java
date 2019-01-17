package com.qss.adddvert;

import com.qss.adddvert.core.ModuleRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdddvertApplicationRunner implements ApplicationRunner {
	@Autowired
    private List<ModuleRegistrar> moduleRegistrars;

	public void run(ApplicationArguments arguments) throws Exception {
        for (ModuleRegistrar registrar : moduleRegistrars) {
            registrar.register();
        }
	}
}

