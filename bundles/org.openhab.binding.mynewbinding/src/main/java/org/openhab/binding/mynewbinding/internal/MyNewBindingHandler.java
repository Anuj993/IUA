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
import org.openhab.core.thing.ThingStatusInfo;
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

        // Gives static Device Information
        if (DEVICEINFO_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                String DeviceInfo = getDeviceInfo();
                updateState(channelUID, new StringType(DeviceInfo));
            }
        }

        // Gives static Manufacturer name
        if (MANUFACTURER_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                String manufacturerName = getManufacturerName();
                updateState(channelUID, new StringType(manufacturerName));
            }
        }

        if (MODEL_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

            }
            String modelId = getModelId();
            updateState(channelUID, new StringType(modelId));
        }
        if (DEVICELOCATION_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

            }
            String deviceLocation = getDeviceLocation();
            updateState(channelUID, new StringType(deviceLocation));

        }

        if (DEVICESTATUS_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {
            }
            String deviceStatus = getDeviceStatus();
            updateState(channelUID, new StringType(deviceStatus));

        }

        if (AUTOIDMODELVERSION_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {
            }
            String autoIdModelVersion = getAutoIdModelVersion();
            updateState(channelUID, new StringType(autoIdModelVersion));
        }

        if (DEVICEMANUAL_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {
            }
            String deviceManual = getDeviceManual();
            updateState(channelUID, new StringType(deviceManual));
        }
        if (ActualTime.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                // Simulate data retrieval
                String simulatedData = getSimulatedScanData();
                // Update the channel with the simulated data
                updateState(channelUID, new StringType(simulatedData));
            }

        }

        // this gives new Barcode
        if (OPTICALVERIFIERSCANRESULT_ID.equals(channelUID.getId())) {

            if (command instanceof RefreshType) {

                updateRandomNumber();
            }

        }
        if (DEVICEREVISION_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

            }
            String deviceRevision = getDeviceRevision();
            updateState(channelUID, new StringType(deviceRevision));
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

        // Gives the Device Information
        return "Hyper Scanner 101";
    }

    private String getManufacturerName() {
        // Gives the manufacture name
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

    /**
     * Retrieves the model ID of the device.
     *
     * @return The model ID of the device.
     */
    private String getModelId() {
        return "248IC";
    }

    /**
     * Retrieves the device location.
     *
     * @return The device location.
     */
    private String getDeviceLocation() {
        return "somewhere in the thm";
    }

    /**
     * Retrieves the device status as a string.
     * could be ONLINE, OFFLINE, UNKNOWN, UNINITIALIZED, INITIALIZING, REMOVING, REMOVED, or DISPOSED.
     *
     * @return The device status.
     */
    private String getDeviceStatus() {
        ThingStatusInfo statusInfo = thing.getStatusInfo();
        return statusInfo.getStatus().toString();
    }

    /**
     * Retrieves the auto ID model version by combining device information and device version number.
     * this is a simulation of what the real results might look like.
     * the real Value should be retrieved from the OPC UA server.
     *
     * @return The auto ID model version.
     */
    private String getAutoIdModelVersion() {
        return getDeviceInfo() + getDeviceVersionNumber();
    }

    /**
     * Returns the device manual instructions for using the scanner.
     * the manual should be retrieved from the scanner in a real implementation.
     * 
     * @return The instructions for using the scanner.
     */
    private String getDeviceManual() {
        String instructions = "";
        instructions = "Press the power button to turn on the scanner.\n "
                + "Place the document to be scanned on the glass.\n " + "Press the scan button to start scanning. \n "
                + "Press the color option if you want a color scan.\n "
                + "Press the black and white option if you want a B/W scan.\n";
        return instructions;
    }

    /**
     * Returns the optical verifier scan result.
     * The optical verifier scan result should be retrieved from the device in a real implementation.
     *
     * @return The optical verifier scan result.
     */
    private int getOpticalVerifierScanResult() {
        Random random = new Random();
        return random.nextInt(100000, 1000000);
    }

    /**
     * Returns the device revision.
     * The device revision should be retrieved from the device in a real implementation.
     * In this case, it's the combination of the model id and the version number of the device.
     *
     * @return The device revision.
     */
    private String getDeviceRevision() {
        return getModelId() + getDeviceVersionNumber();
    }

    private String getDeviceVersionNumber() {
        return "248IC";
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
