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
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DateServiceImpl implements DateService {

	@Override
	public List<String> nextTwoWorkWeeks() {
	    Calendar calendar = Calendar.getInstance();
		List<String> dates = new ArrayList<String>();
		
		for(int i=0; i<14; i++) {
	        if(calendar.get(Calendar.DAY_OF_WEEK)<=6 && calendar.get(Calendar.DAY_OF_WEEK)>=2)
	        {
	        	dates.add(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
	        }
	        calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return dates;
	}
}
