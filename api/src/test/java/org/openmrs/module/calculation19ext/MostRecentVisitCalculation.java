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

import java.util.List;
import java.util.Map;

import org.openmrs.Cohort;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.annotation.Handler;
import org.openmrs.api.PatientService;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.api.patient.PatientCalculationContext;
import org.openmrs.calculation.definition.ParameterDefinitionSet;
import org.openmrs.calculation.evaluator.patient.PatientCalculationEvaluator;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.calculation.result.EmptyResult;
import org.openmrs.module.calculation19ext.result.VisitResult;
import org.openmrs.util.OpenmrsUtil;

/**
 * Calculation for most recent obs, this calculation also evaluates itself
 */
@Handler(supports = { MostRecentVisitCalculation.class }, order = 50)
public class MostRecentVisitCalculation implements PatientCalculation, PatientCalculationEvaluator {
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#getParameterDefinitionSet()
	 */
	@Override
	public ParameterDefinitionSet getParameterDefinitionSet() {
		return null;
	}
	
	/**
	 * @see org.openmrs.calculation.Calculation#setConfiguration(java.lang.String)
	 */
	@Override
	public void setConfiguration(String configuration) {
	}
	
	/**
	 * @see org.openmrs.calculation.evaluator.patient.PatientCalculationEvaluator#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.patient.PatientCalculation, java.util.Map,
	 *      org.openmrs.calculation.api.patient.PatientCalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, PatientCalculation calculation, Map<String, Object> parameterValues,
	                             PatientCalculationContext context) {
		CohortResult results = new CohortResult();
		if (cohort != null) {
			PatientService ps = Context.getPatientService();
			VisitService vs = Context.getVisitService();
			for (Integer patientId : cohort.getMemberIds()) {
				Patient patient = ps.getPatient(patientId);
				if (patient != null) {
					Visit mostRecentVisitFound = null;
					
					List<Visit> visits = vs.getVisitsByPatient(patient);
					
					if (visits != null) {
						for (Visit visit : visits) {
							if (mostRecentVisitFound == null
							        || OpenmrsUtil
							                .compare(visit.getStartDatetime(), mostRecentVisitFound.getStartDatetime()) > 0) {
								mostRecentVisitFound = visit;
							}
						}
					}
					
					if (mostRecentVisitFound != null) {
						results.put(patientId, new VisitResult(mostRecentVisitFound, calculation, context));
					} else
						results.put(patientId, new EmptyResult());
				}
			}
		}
		
		return results;
	}
}
