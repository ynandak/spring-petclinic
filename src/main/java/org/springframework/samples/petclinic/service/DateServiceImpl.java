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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
	public Map<String, List<AppointmentTime>> datesWithAppointmentsForVet(Integer vetId) {
		List<Date> days = nextTwoWorkWeeks();
		Map<String, List<AppointmentTime>> dates = new LinkedHashMap<String, List<AppointmentTime>>();
		List<Appointment> apps = appointmentRepository.findByVetForDates(vetId, days);
		
		List<AppointmentTime> validTimes = new ArrayList<AppointmentTime>();
		AppointmentTime time;
		time = new AppointmentTime(); time.setTime("09:00:00"); time.setId(1); validTimes.add(time);
		time = new AppointmentTime(); time.setTime("10:00:00"); time.setId(2); validTimes.add(time);
		time = new AppointmentTime(); time.setTime("11:00:00"); time.setId(3); validTimes.add(time);
		time = new AppointmentTime(); time.setTime("12:00:00"); time.setId(4); validTimes.add(time);
		time = new AppointmentTime(); time.setTime("13:00:00"); time.setId(5); validTimes.add(time);
		time = new AppointmentTime(); time.setTime("14:00:00"); time.setId(6); validTimes.add(time);
		time = new AppointmentTime(); time.setTime("15:00:00"); time.setId(7); validTimes.add(time);
		time = new AppointmentTime(); time.setTime("16:00:00"); time.setId(8); validTimes.add(time);
		
		for(Date day : days) {
			dates.put(new SimpleDateFormat("yyyy-MM-dd").format(day), new ArrayList<AppointmentTime>(validTimes));
		}
		System.err.println("apps size is " + apps.size());
		for(Appointment app : apps) {
			String appDate = new SimpleDateFormat("yyyy-MM-dd").format(app.getDate());
			List<AppointmentTime> temp = dates.get(appDate);
			for(int i=0; i<temp.size(); i++) {
				if(temp.get(i)!=null && app.getTime().getTime().equals(temp.get(i).getTime())) {
					temp.set(i, null);
					System.err.println("inside");
				}
			}
		}
		System.err.println("done");
		return dates;
	}
}
