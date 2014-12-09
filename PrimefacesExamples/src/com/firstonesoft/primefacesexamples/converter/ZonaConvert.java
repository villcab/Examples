/**
 * 
 */
package com.firstonesoft.primefacesexamples.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.firstonesoft.primefacesexamples.model.User;

/**
 * 
 * @author Bismarck Villca Soliz
 *
 */
@FacesConverter("userConvert")
public class ZonaConvert implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return getObjectFromUIPickListComponent(component, value);
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object object) {
		String string;
		if (object == null) {
			string = "";
		} else {
			try {
				string = String.valueOf(((User) object).getId());
			} catch (ClassCastException cce) {
				throw new ConverterException();
			}
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	private User getObjectFromUIPickListComponent(UIComponent component,
			String value) {
		final DualListModel<User> dualList;
		try {
			dualList = (DualListModel<User>) ((PickList) component).getValue();
			User team = getObjectFromList(dualList.getSource(),
					Long.valueOf(value));
			if (team == null) {
				team = getObjectFromList(dualList.getTarget(),
						Long.valueOf(value));
			}

			return team;
		} catch (ClassCastException cce) {
			throw new ConverterException();
		} catch (NumberFormatException nfe) {
			throw new ConverterException();
		}
	}

	private User getObjectFromList(final List<?> list, final long identifier) {
		for (final Object object : list) {
			final User team = (User) object;
			if (team.getId() == identifier) {
				return team;
			}
		}
		return null;
	}

}
