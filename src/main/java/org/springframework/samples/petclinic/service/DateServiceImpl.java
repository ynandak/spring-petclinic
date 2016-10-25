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
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DateServiceImpl implements DateService {
	
	private AppointmentRepository appointmentRepository;
	private ClinicService clinicService;
	
	@Autowired
	public DateServiceImpl(AppointmentRepository appointmentRepository, ClinicService clinicService) {
		this.appointmentRepository = appointmentRepository;
		this.clinicService = clinicService;
	}

	@Override
	public List<Date> nextTwoWorkWeeks() {
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DAY_OF_MONTH, 1);
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
	public Map<String, List<AppointmentTime>> datesWithAppointmentsForVet(Integer vetId, Integer ownerID) {
		Owner owner = clinicService.findOwnerById(ownerID);
		List<Pet> pets = owner.getPets();
		Set<Integer> petIDs = new HashSet<Integer>();
		for(Pet p : pets) {
			petIDs.add(p.getId());
		}
		List<Date> days = nextTwoWorkWeeks();
		Map<String, List<AppointmentTime>> dates = new LinkedHashMap<String, List<AppointmentTime>>();
		List<Appointment> apps = appointmentRepository.findByVetForDates(vetId, days);
		
		List<AppointmentTime> validTimes = new ArrayList<AppointmentTime>();
		AppointmentTime time;
		for(int i=1; i<=8; i++) {
			time = new AppointmentTime();
			if(i==1)
				time.setTime("0" + (i+8) +":00:00");
			else
				time.setTime((i+8) +":00:00");
			time.setId(i);
			validTimes.add(time);
		}
		
		for(Date day : days) {
			dates.put(new SimpleDateFormat("yyyy-MM-dd").format(day), new ArrayList<AppointmentTime>(validTimes));
		}
		for(Appointment app : apps) {
			String appDate = new SimpleDateFormat("yyyy-MM-dd").format(app.getDate());
			List<AppointmentTime> temp = dates.get(appDate);
			for(int i=0; i<temp.size(); i++) {
				if(temp.get(i)!=null && app.getTime().getTime().equals(temp.get(i).getTime())) {
					if(petIDs.contains(app.getPet().getId())) {
						AppointmentTime t = new AppointmentTime();
						t.setId(temp.get(i).getId());
						t.setTime("owner");
						temp.set(i, t);
						System.err.println("inside");
					}
					else {
						temp.set(i, null);
					}
				}
			}
		}
		return dates;
	}
}
