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

    /**
     * schedule tasks to run after a specified delay or to execute repeatedly at fixed intervals
     */
    private @Nullable ScheduledFuture<?> scheduledFuture = null;

    public MyNewBindingHandler(Thing thing) {
        super(thing);
    }

    /**
     * Handles commands for updating various device information states based on the provided channel ID and command.
     * Updates static device information such as device info, manufacturer name, model ID, device location, device
     * status,
     * model and version ID, device manual for new users, country of origin, device revision ID, and generates a random
     * new
     * barcode.
     *
     * @param channelUID The unique identifier for the channel.
     * @param command The command to be handled.
     */
    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {

        // static Device Information
        if (DEVICEINFO_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                String DeviceInfo = getDeviceInfo();
                updateState(channelUID, new StringType(DeviceInfo));
            }
        }

        // static Manufacturer name
        if (MANUFACTURER_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                String manufacturerName = getManufacturerName();
                updateState(channelUID, new StringType(manufacturerName));
            }
        }

        // static model Id Name
        if (MODEL_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                String modelId = getModelId();
                updateState(channelUID, new StringType(modelId));
            }
        }

        // location of device
        if (DEVICELOCATION_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                String deviceLocation = getDeviceLocation();
                updateState(channelUID, new StringType(deviceLocation));

            }

        }
        // device Status
        if (DEVICESTATUS_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                String deviceStatus = getDeviceStatus();
                updateState(channelUID, new StringType(deviceStatus));

            }

        }

        // model and version Id
        if (AUTOIDMODELVERSION_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                String autoIdModelVersion = getAutoIdModelVersion();
                updateState(channelUID, new StringType(autoIdModelVersion));
            }
        }

        // device manual for new Users
        if (DEVICEMANUAL_ID.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                String deviceManual = getDeviceManual();
                updateState(channelUID, new StringType(deviceManual));

            }

        }

        // origin of product
        if (COUNTRY_ORIGIN.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {

                String country = countryOrigin();
                updateState(channelUID, new StringType(country));
            }

        }
        // Random new Barcode
        if (OPTICALVERIFIERSCANRESULT_ID.equals(channelUID.getId())) {

            if (command instanceof RefreshType) {

                updateRandom_BarCode();
            }

        }
        // device revision Id
        if (DEVICEREVISION_ID.equals(channelUID.getId())) {

            if (command instanceof RefreshType) {

                String deviceRevision = getDeviceRevision();
                updateState(channelUID, new StringType(deviceRevision));

            }
        }
    }

    /**
     * Initializes the binding by retrieving configuration settings, setting the initial Thing status to UNKNOWN,
     * and executing background initialization tasks. Once initialization is complete and the Thing is confirmed
     * as reachable, the Thing status is updated to ONLINE, and a task is scheduled to update a random barcode at
     * regular intervals.
     */
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
                scheduledFuture = scheduler.scheduleAtFixedRate(this::updateRandom_BarCode, 0, 3, TimeUnit.SECONDS);
            } else {
                updateStatus(ThingStatus.OFFLINE);
            }
        });
    }

    /**
     * Retrieves the device information for the current device.
     * 
     * @return A String that represents the device information,
     *         for our case Device Info is "Hyper Scanner 101".
     */
    private String getDeviceInfo() {

        // Gives the Device Information
        return "Hyper Scanner 101";
    }

    /**
     * Retrieves the name of the device manufacturer.
     *
     * @return A String representing the name of the device manufacturer
     *
     */
    private String getManufacturerName() {
        // Gives the manufacture name
        return "THM Industrie GmbH";
    }

    /**
     * Retrieves the name of the origin of a country.
     *
     * @return A String representing the name of country name
     *
     */
    private String countryOrigin() {
        // Generate simulated data
        // This is a placeholder, and you'd replace this with your simulation logic
        return "Germany";
    }

    /**
     * Generates a random barcode number and updates the channel state with this new value.
     * The method creates a random number between 100,000 and 10,000,000, which simulates
     * a unique barcode. It then calls {@code updateState} to update the channel state of
     * the optical verifier scan result with this random barcode number.
     *
     * It directly interacts with the system's channel state, ensuring the optical
     * verifier's scan result is updated with a new, randomly generated barcode.
     */

    private void updateRandom_BarCode() {
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
        return "THM A12";
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
     * Returns the device revision.
     * The device revision should be retrieved from the device in a real implementation.
     * In this case, it's the combination of the model id and the version number of the device.
     *
     * @return The device revision.
     */
    private String getDeviceRevision() {
        return "Q34E234";
    }

    /**
     * Retrieves the Device Version Number of the device.
     *
     * @return The Device Version Number of the device.
     */
    private String getDeviceVersionNumber() {
        return "248IC";
    }

    /**
     * Cleans up resources before the object is destroyed. Specifically, this
     * implementation ensures that any scheduled tasks are cancelled to prevent
     * resource leaks or unwanted execution after the object is disposed of.
     * If there is an active scheduled task associated with this handler, it is
     * checked for its current state. If the task is not already cancelled, it
     * is cancelled explicitly.
     *
     * This method should be called when the handler is no longer needed and is
     * being disposed of. It calls the {@code dispose} method of the superclass
     * to ensure proper resource management and cleanup across the hierarchy.
     */
    @Override
    public void dispose() {
        // Ensure we cancel the scheduled task when the handler is disposed
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
        super.dispose();
    }
}
