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
package org.eclipse.e4.tools.emf.liveeditor;


import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.tools.emf.ui.common.IModelResource;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

public class MemoryModelResource implements IModelResource {
	private WritableList list = new WritableList();
	private EditingDomain editingDomain;
	
	public MemoryModelResource(MApplication application) {
		list.add(application);
		BasicCommandStack commandStack = new BasicCommandStack();
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack);
	}
	
	public IObservableList getRoot() {
		return list;
	}

	public boolean isSaveable() {
		return false;
	}

	public IStatus save() {
		return Status.OK_STATUS;
	}

	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	public boolean isDirty() {
		return false;
	}

	public void addModelListener(ModelListener listener) {
		
	}

	public void removeModelListener(ModelListener listener) {
		
	}

	public IMarker createMarker() {
		return null;
	}

	public void clearMarkers() {
		
	}

	public void replaceRoot(EObject eobject) {
		
	}
}