package org.kea.nicolainielsen.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GeoTools {

    // Haversine-formula ... something clever rom ChatGPT ... A method to calculate distances between two coordinates ...
    public double distanceInKm(
            double latFire, double lonFire,
            double latSiren, double lonSiren) {

        final int R = 6371; // Radius of the earth in km

        double latDistance = Math.toRadians(latSiren - latFire);
        double lonDistance = Math.toRadians(lonSiren - lonFire);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latFire)) * Math.cos(Math.toRadians(latSiren))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // distance in kilometers
    }

}
