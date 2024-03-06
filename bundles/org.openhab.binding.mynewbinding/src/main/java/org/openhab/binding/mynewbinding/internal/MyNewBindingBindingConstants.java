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

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link MyNewBindingBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Anuj - Initial contribution
 */
@NonNullByDefault
public class MyNewBindingBindingConstants {

    private static final String BINDING_ID = "mynewbinding";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_SAMPLE = new ThingTypeUID(BINDING_ID, "BarCodeReader");

    // List of all Channel ids
    public static final String DEVICEINFO_ID = "DeviceInfo";

    public static final String MANUFACTURER_ID = "Manufacturer";

    public static final String MODEL_ID = "Model";

    public static final String DEVICELOCATION_ID = "DeviceLocation";

    public static final String DEVICESTATUS_ID = "DeviceStatus";

    public static final String AUTOIDMODELVERSION_ID = "AutoIdModelVersion";

    public static final String DEVICEMANUAL_ID = "DeviceManual";

    public static final String LASTSCANDATA_ID = "LastScanData";

    public static final String OPTICALVERIFIERSCANRESULT_ID = "OpticalVerifierScanResult";

    public static final String DEVICEREVISION_ID = "DeviceRevision";
}
