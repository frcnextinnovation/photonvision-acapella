/*
 * Copyright (C) Photon Vision.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.photonvision.vision.camera.USBCameras;

import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoException;
import edu.wpi.first.math.MathUtil;
import org.photonvision.common.configuration.CameraConfiguration;

public class Ks1a293CameraSettables extends GenericUSBCameraSettables {
    public Ks1a293CameraSettables(CameraConfiguration configuration, UsbCamera camera) {
        super(configuration, camera);
    }

    @Override
    public void setAllCamDefaults() {
        softSet("power_line_frequency", 1); // Assume 50Hz China
        softSet("white_balance_temperature_auto", 0);
        autoExposureProp.set(PROP_AUTO_EXPOSURE_DISABLED);
    }

    @Override
    protected void setUpExposureProperties() {
        super.setUpExposureProperties();

        this.minExposure = 1;
        this.maxExposure = 1024;
    }

    @Override
    public void setAutoExposure(boolean cameraAutoExposure) {
        super.setAutoExposure(false);
    }

    @Override
    public void setExposureRaw(double exposureRaw) {
        if (exposureRaw >= 0.0) {
            try {
                int propVal = (int) MathUtil.clamp(exposureRaw, minExposure, maxExposure);
                softSet("iris_absolute", propVal);
                this.lastExposureRaw = exposureRaw;
            } catch (VideoException e) {
                logger.error("Failed to set camera exposure!", e);
            }
        }
    }
}
