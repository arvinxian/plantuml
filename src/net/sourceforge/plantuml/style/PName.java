/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2020, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * http://plantuml.com/patreon (only 1$ per month!)
 * http://plantuml.com/paypal
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 *
 *
 */
package net.sourceforge.plantuml.style;

public enum PName {
	Shadowing, //
	FontName, //
	FontColor, //
	DARK_FontColor, //
	FontSize, //
	FontStyle, //
	BackGroundColor, //
	DARK_BackGroundColor, //
	RoundCorner, //
	LineThickness, //
	DiagonalCorner, //
	HyperLinkColor, //
	DARK_HyperLinkColor, //
	LineColor, //
	DARK_LineColor, //
	LineStyle, //
	Padding, //
	Margin, //
	MaximumWidth, //
	MinimumWidth, //
	ExportedName, //
	Image, //
	HorizontalAlignment, //
	ShowStereotype, //
	ImagePosition;

	public static PName getFromName(String name, StyleScheme scheme) {
		for (PName prop : values()) {
			if (prop.name().equalsIgnoreCase(name)) {
				if (scheme == StyleScheme.DARK)
					return dark(prop);
				return prop;
			}
		}
		return null;
	}

	private static PName dark(PName name) {
		switch (name) {
		case FontColor:
			return DARK_FontColor;
		case BackGroundColor:
			return DARK_BackGroundColor;
		case HyperLinkColor:
			return DARK_HyperLinkColor;
		case LineColor:
			return DARK_LineColor;
		default:
			return name;
		}
	}

}