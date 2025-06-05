package it.unibo.smartcity.controller.api;

import java.util.Optional;

import it.unibo.smartcity.view.api.View;

public interface Controller {

    void attachView(View v);

    void handleEvent(SmartCityEvent e, Optional<?> data);

}