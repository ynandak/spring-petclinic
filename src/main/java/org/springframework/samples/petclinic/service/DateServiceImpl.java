/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.AppointmentTime;
import org.springframework.samples.petclinic.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DateServiceImpl implements DateService {
	
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	public DateServiceImpl(AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@Override
	public List<Date> nextTwoWorkWeeks() {
	    Calendar calendar = Calendar.getInstance();
		List<Date> dates = new ArrayList<Date>();
		
		for(int i=0; i<14; i++) {
	        if(calendar.get(Calendar.DAY_OF_WEEK)<=6 && calendar.get(Calendar.DAY_OF_WEEK)>=2)
	        {
	        	dates.add(calendar.getTime());
	        }
	        calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return dates;
	}

	@Override
	public Map<String, Set<AppointmentTime>> datesWithAppointmentsForVet(Integer vetId) {
		List<Date> days = nextTwoWorkWeeks();
		Map<String, Set<AppointmentTime>> dates = new LinkedHashMap<String, Set<AppointmentTime>>();
		List<Appointment> apps = appointmentRepository.findByVetForDates(vetId, days);
		
		for(Date day : days) {
			dates.put(new SimpleDateFormat("yyyy-MM-dd").format(day), null);
		}
		
		for(Appointment app : apps) {
			String appDate = new SimpleDateFormat("yyyy-MM-dd").format(app.getDate());
			Set<AppointmentTime> temp = dates.get(appDate);
			if(temp == null) {
				temp = new HashSet<AppointmentTime>();
			}
			temp.add(app.getTime());
			dates.put(appDate, temp);
		}
		
		return dates;
	}
}
