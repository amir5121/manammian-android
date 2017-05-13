package com.amir.manammiam.services;

import com.amir.manammiam.base.ManamMiamApplication;

public class Module {
    public static void register (ManamMiamApplication application) {
        new LiveManamMiamServices(application);
    }
}