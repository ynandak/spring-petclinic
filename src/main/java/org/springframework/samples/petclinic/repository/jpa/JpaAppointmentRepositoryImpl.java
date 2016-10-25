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
package org.springframework.samples.petclinic.repository.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.AppointmentTime;
import org.springframework.samples.petclinic.repository.AppointmentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaAppointmentRepositoryImpl implements AppointmentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Appointment appointment) {
        if (appointment.getId() == null) {
            this.em.persist(appointment);
        } else {
            this.em.merge(appointment);
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> findByVetForDates(Integer vetId, List<Date> dates) throws DataAccessException {
		Query query = this.em.createQuery("SELECT a FROM Appointment a WHERE a.vet.id= :vetId AND a.date BETWEEN :startDate AND :endDate");

		Date startDate = dates.get(0);
		Date endDate = dates.get(dates.size()-1);

		query.setParameter("vetId", vetId);
		query.setParameter("startDate", startDate, TemporalType.DATE);
		query.setParameter("endDate", endDate, TemporalType.DATE);
		return query.getResultList();
	}

	@Override
	public AppointmentTime findAppointmentTimeById(int id) {
		return this.em.find(AppointmentTime.class, id);
	}

	@Override
	public void delete(Appointment app) {
		this.em.remove(app);
	}

	@Override
	public Appointment findAppointmentByDateTime(Date date, AppointmentTime time) {
		Query query = this.em.createQuery("SELECT a FROM Appointment a WHERE a.date= :date AND a.time= :time");
		query.setParameter("date", date, TemporalType.DATE);
		query.setParameter("time", time);
		return (Appointment) query.getSingleResult();
	}

}
