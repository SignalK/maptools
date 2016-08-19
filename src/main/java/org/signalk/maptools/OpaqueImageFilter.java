/*
 * Copyright 2012,2013 Robert Huitema robert@42.co.nz
 * 
  * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.signalk.maptools;

import java.awt.image.RGBImageFilter;

/**
 * Change transparent pixels to black
 * @author robert
 *
 */
public class OpaqueImageFilter extends RGBImageFilter {
	// the color we are setting... Alpha bits are set to opaque
	//private int markerRGB = Color.white.getRGB() | 0xFF000000;


	public final int filterRGB(int x, int y, int rgb) {

		//if ((rgb | 0xFF000000) == markerRGB) {
		if( (rgb>>24) == 0x00 ){
			// Mark the colour as black
			return 0xFF000000;
		} else {
			// nothing to do
			return rgb;
		}
	}

}
