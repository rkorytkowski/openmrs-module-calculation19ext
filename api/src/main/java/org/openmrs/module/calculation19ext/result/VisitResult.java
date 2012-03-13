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
package org.openmrs.module.calculation19ext.result;

import java.util.Date;

import org.openmrs.Visit;
import org.openmrs.calculation.api.patient.PatientCalculationContext;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.result.DateBasedResult;
import org.openmrs.calculation.result.Result;
import org.openmrs.calculation.result.SimpleResult;

/**
 * Represents a {@link Result} for a {@link Visit}
 */
public class VisitResult extends SimpleResult implements DateBasedResult {
	
	/**
	 * @param visit
	 * @param patientCalculation
	 */
	public VisitResult(Visit visit, PatientCalculation patientCalculation) {
		super(visit, patientCalculation);
	}
	
	/**
	 * @param visit
	 * @param patientCalculation
	 * @param patientCalculationContext
	 */
	public VisitResult(Visit visit, PatientCalculation patientCalculation,
	    PatientCalculationContext patientCalculationContext) {
		super(visit, patientCalculation, patientCalculationContext);
	}
	
	/**
	 * @see org.openmrs.calculation.result.DateBasedResult#getDateOfResult()
	 */
	@Override
	public Date getDateOfResult() {
		if (getValue() != null)
			return ((Visit) getValue()).getStartDatetime();
		return null;
	}
	
}
