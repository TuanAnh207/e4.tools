/*******************************************************************************
 * Copyright (c) 2010 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl <tom.schindl@bestsolution.at> - initial API and implementation
 ******************************************************************************/
package org.eclipse.e4.tools.emf.ui.internal.common;

import org.eclipse.e4.tools.emf.ui.common.component.AbstractComponentEditor;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.TextStyle;

public class ComponentLabelProvider extends StyledCellLabelProvider {
	private Image modelComponentsImage;
	private Image modelComonentImage;
	private Image partsImage;
	private Image menusImage;
	private Image partImage;
	private Image partDescriptorImage;

	private ModelEditor editor;

	private static Styler NOT_RENDERED_STYLER = new Styler() {
		{
			JFaceResources.getColorRegistry().put("NOT_RENDERED_STYLER", new RGB(200, 200, 200));
		}

		@Override
		public void applyStyles(TextStyle textStyle) {
			textStyle.foreground = JFaceResources.getColorRegistry().get("NOT_RENDERED_STYLER");
		}
	};

	public ComponentLabelProvider(ModelEditor editor) {
		this.editor = editor;
	}

	@Override
	public void update(final ViewerCell cell) {
		if (cell.getElement() instanceof EObject) {

			EObject o = (EObject) cell.getElement();
			AbstractComponentEditor elementEditor = editor.getEditor(o.eClass());
			if (elementEditor != null) {
				String label = elementEditor.getLabel(o);
				String detailText = elementEditor.getDetailLabel(o);
				Styler styler = null;

				if (o instanceof MUIElement) {
					if (!((MUIElement) o).isVisible()) {
						label += "<invisible>";
						styler = NOT_RENDERED_STYLER;
					} else if (!((MUIElement) o).isToBeRendered()) {
						label += "<not rendered>";
						styler = NOT_RENDERED_STYLER;
					}
				}

				if (detailText == null) {
					StyledString styledString = new StyledString(label, styler);
					cell.setText(styledString.getString());
					cell.setStyleRanges(styledString.getStyleRanges());
				} else {
					StyledString styledString = new StyledString(label, styler);
					styledString.append(" - " + detailText, StyledString.DECORATIONS_STYLER); //$NON-NLS-1$
					cell.setText(styledString.getString());
					cell.setStyleRanges(styledString.getStyleRanges());
				}
				cell.setImage(elementEditor.getImage(o, cell.getControl().getDisplay()));
			} else {
				cell.setText(cell.getElement().toString());
			}
		} else if (cell.getElement() instanceof VirtualEntry<?>) {
			String s = cell.getElement().toString();
			cell.setText(s);
		} else {
			cell.setText(cell.getElement().toString());
		}
	}

	@Override
	public void dispose() {
		if (modelComponentsImage != null) {
			modelComponentsImage.dispose();
			modelComponentsImage = null;
		}

		if (modelComonentImage != null) {
			modelComonentImage.dispose();
			modelComonentImage = null;
		}

		if (partsImage != null) {
			partsImage.dispose();
			partsImage = null;
		}

		if (menusImage != null) {
			menusImage.dispose();
			menusImage = null;
		}

		if (partImage != null) {
			partImage.dispose();
			partImage = null;
		}

		if (partDescriptorImage != null) {
			partDescriptorImage.dispose();
			partDescriptorImage = null;
		}
		super.dispose();
	}
}