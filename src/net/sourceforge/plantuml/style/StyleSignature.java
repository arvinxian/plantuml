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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.sourceforge.plantuml.Url;
import net.sourceforge.plantuml.cucadiagram.Stereostyles;
import net.sourceforge.plantuml.cucadiagram.Stereotype;

public class StyleSignature {

	private final Set<String> names = new LinkedHashSet<>();
	private final boolean withDot;

	public StyleSignature(String s) {
		if (s.contains("*") || s.contains("&") || s.contains("-"))
			throw new IllegalArgumentException();

		this.withDot = s.contains(".");
		this.names.add(clean(s));
	}

	public static StyleSignature empty() {
		return new StyleSignature(false);
	}

	private StyleSignature(boolean withDot) {
		this.withDot = withDot;
	}

	private StyleSignature(boolean withDot, Collection<String> copy) {
		this.names.addAll(copy);
		this.withDot = withDot;
	}

	public StyleSignature addClickable(Url url) {
		if (url == null)
			return this;

		final Set<String> result = new LinkedHashSet<>(names);
		result.add(SName.clickable.name());
		return new StyleSignature(withDot, result);

	}

	public StyleSignature add(String s) {
		if (s == null)
			return this;

		if (s.contains("*") || s.contains("&") || s.contains("-"))
			throw new IllegalArgumentException();

		final Set<String> result = new LinkedHashSet<>(names);
		result.add(clean(s));
		return new StyleSignature(withDot || s.contains("."), result);
	}

	public StyleSignature add(SName name) {
		return add(name.name().toLowerCase().replace("_", ""));
	}

	public StyleSignature addStar() {
		final Set<String> result = new LinkedHashSet<>(names);
		result.add("*");
		return new StyleSignature(withDot, result);
	}

	public boolean isStarred() {
		return names.contains("*");
	}

	@Override
	public boolean equals(Object arg) {
		final StyleSignature other = (StyleSignature) arg;
		return this.names.equals(other.names);
	}

	@Override
	public int hashCode() {
		return names.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		for (String n : names) {
			if (result.length() > 0)
				result.append('.');

			result.append(n);
		}
		return result.toString() + " " + withDot;
	}

	public boolean matchAll(StyleSignature other) {
		if (other.isStarred() && names.contains("*") == false)
			return false;

		for (String token : names) {
			if (token.equals("*"))
				continue;

			if (other.names.contains(token) == false)
				return false;

		}
		return true;
	}

	public final Set<String> getNames() {
		return Collections.unmodifiableSet(names);
	}

	public static StyleSignature of(SName... names) {
		final List<String> result = new ArrayList<>();
		for (SName name : names)
			result.add(name.name().toLowerCase().replace("_", ""));

		return new StyleSignature(false, result);
	}

	public StyleSignature forStereotypeItself(Stereotype stereotype) {
		final List<String> result = new ArrayList<>(names);
		if (stereotype != null)
			for (String name : stereotype.getStyleNames())
				result.add(clean(name));

		result.add(SName.stereotype.name().toLowerCase().replace("_", ""));
		return new StyleSignature(false, result);
	}

	public StyleSignature with(Stereotype stereotype) {
		final List<String> result = new ArrayList<>(names);
		if (stereotype != null)
			for (String name : stereotype.getStyleNames())
				result.add(clean(name));

		return new StyleSignature(true, result);
	}

	public StyleSignature with(Stereostyles stereostyles) {
		if (stereostyles.isEmpty())
			return this;
		final List<String> result = new ArrayList<>(names);
		for (String name : stereostyles.getStyleNames())
			result.add(clean(name));

		return new StyleSignature(true, result);
	}

	private String clean(String name) {
		return name.toLowerCase().replace("_", "").replace(".", "");
	}

	public StyleSignature mergeWith(List<Style> others) {
		final List<String> copy = new ArrayList<>(names);
		for (Style other : others)
			for (String s : other.getSignature().getNames())
				copy.add(s);

		return new StyleSignature(withDot, copy);
	}

	public StyleSignature mergeWith(StyleSignature other) {
		final List<String> copy = new ArrayList<>(names);
		copy.addAll(other.names);
		return new StyleSignature(withDot || other.withDot, copy);
	}

	public Style getMergedStyle(StyleBuilder styleBuilder) {
		if (styleBuilder == null)
			return null;

		return styleBuilder.getMergedStyle(this);
	}

	public boolean match(Stereotype stereotype) {
		for (String s : stereotype.getMultipleLabels())
			if (names.contains(clean(s)))
				return true;

		return false;
	}

	public final boolean isWithDot() {
		return withDot;
	}

}