/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.calculation19ext;

import junit.framework.Assert;

import org.junit.Test;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.api.patient.PatientCalculationService;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.provider.ClasspathCalculationProvider;
import org.openmrs.module.calculation19ext.result.VisitResult;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Contains behavior tests for patient calculations.
 */
public class PatientCalculationBehaviorTest extends BaseModuleContextSensitiveTest {
	
	public PatientCalculationService getService() {
		return Context.getService(PatientCalculationService.class);
	}
	
	@Test
	public void shouldGetAVisitResultAsADateBasedResult() throws Exception {
		int patientId = 2;
		Visit expectedVisit = Context.getVisitService().getVisit(3);
		VisitResult result = (VisitResult) getService().evaluate(
		    patientId,
		    (PatientCalculation) new ClasspathCalculationProvider().getCalculation(
		        MostRecentVisitCalculation.class.getName(), null));
		
		Assert.assertEquals(expectedVisit, result.asType(Visit.class));
		Assert.assertEquals(expectedVisit.getStartDatetime(), result.getDateOfResult());
	}
}
