package com.github.curriculeon;

import com.github.curriculeon.controllers.PersonController;
import com.github.curriculeon.dao.PersonJpaRepository;
import com.github.curriculeon.routers.ControllerRouter;
import com.github.curriculeon.service.PersonService;
import com.github.curriculeon.utils.IOConsole;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * Created by leon on 8/13/2020.
 */
public class ApplicationRunner implements Runnable {
    private IOConsole console = new IOConsole(IOConsole.AnsiColor.GREEN);

    @Override
    public void run() {
        PersonController personController = new PersonController(new PersonService(new PersonJpaRepository("production")));
//        PersonController personController = new PersonController(new PersonService(new PersonJdbcRepository(DatabaseConnection.PRODUCTION_DATABASE));

        String userInput;
        do {
            userInput = console.getStringInput(new StringJoiner("\n")
                    .add("\n\nWelcome to the application request router.")
                    .add("From here, you can select any of the following options:")
                    .add(Arrays
                            .toString(ControllerRouter.values())
                            .replaceAll("_", "-")
                            .toLowerCase())
                    .toString());
            try {
                ControllerRouter.getValueOf(userInput).perform(personController);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!"quit".equalsIgnoreCase(userInput));
    }
}
