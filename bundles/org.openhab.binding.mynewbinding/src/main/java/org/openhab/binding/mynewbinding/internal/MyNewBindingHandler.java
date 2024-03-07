/**
 * Copyright (c) 2010-2024 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.mynewbinding.internal;

import static org.openhab.binding.mynewbinding.internal.MyNewBindingBindingConstants.*;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link MyNewBindingHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Anuj - Initial contribution
 */
@NonNullByDefault
public class MyNewBindingHandler extends BaseThingHandler {

    // logger is normally used to show debug, info, warn and the error
    private final Logger logger = LoggerFactory.getLogger(MyNewBindingHandler.class);

    private @Nullable MyNewBindingConfiguration config;

    private @Nullable ScheduledFuture<?> scheduledFuture = null;

    public MyNewBindingHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {


        //Gives static Device Information
        if (DEVICEINFO_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                String DeviceInfo = getDeviceInfo();
                updateState(channelUID, new StringType(DeviceInfo));
            }
        }

        //Gives static Manufacturer name
        if (MANUFACTURER_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                String manufacturerName = getManufacturerName();
                updateState(channelUID, new StringType(manufacturerName));
            }
        }

        if (MODEL_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

            }

        }
        if (DEVICELOCATION_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

            }

        }

        if (DEVICESTATUS_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

            }

        }


        if (AUTOIDMODELVERSION_ID.equals(channelUID.getId())) {

            if (command instanceof RefreshType) {

            }

        }

        if (DEVICEMANUAL_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

            }

        }
        if (ActualTime.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                // Simulate data retrieval
                String simulatedData = getSimulatedScanData();
                // Update the channel with the simulated data
                updateState(channelUID, new StringType(simulatedData));
            }

        }

        //this gives new Barcode
        if (OPTICALVERIFIERSCANRESULT_ID.equals(channelUID.getId())) {

            if (command instanceof RefreshType) {

                updateRandomNumber();
            }

        }
        if (DEVICEREVISION_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

            }

        }
    }

    @Override
    public void initialize() {

        config = getConfigAs(MyNewBindingConfiguration.class);

        updateStatus(ThingStatus.UNKNOWN);

        // Example for background initialization:
        scheduler.execute(() -> {
            boolean thingReachable = true; // <background task with long running initialization here>
            // when done do:
            if (thingReachable) {

                updateStatus(ThingStatus.ONLINE);

                // Schedule the task to update random number after confirming thing is reachable/ONLINE
                scheduledFuture = scheduler.scheduleAtFixedRate(this::updateRandomNumber, 0, 3, TimeUnit.SECONDS);
            } else {
                updateStatus(ThingStatus.OFFLINE);
            }
        });

    }

    private String getDeviceInfo() {

        //Gives the Device Information
        return "Hyper Scanner 101";
    }
    private String getManufacturerName() {
         //Gives the manufacture name
        return "THM Industrie GmbH";
    }

    private String getSimulatedScanData() {
        // Generate simulated data
        // This is a placeholder, and you'd replace this with your simulation logic
        return "Simulated scan data at " + new Date().toString();
    }

    private void updateRandomNumber() {
        // Generate a random number between 100,000 and 10,000,000
        int randomNumber = new Random().nextInt((10000000 - 100000) + 1) + 100000;

        // Update the channel state with the new random number
        updateState(new ChannelUID(getThing().getUID(), OPTICALVERIFIERSCANRESULT_ID), new DecimalType(randomNumber));
    }

    @Override
    public void dispose() {
        // Ensure we cancel the scheduled task when the handler is disposed
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
        super.dispose();
    }
}
