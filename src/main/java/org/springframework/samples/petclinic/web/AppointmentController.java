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
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppointmentController {

    private final ClinicService clinicService;

    @Autowired
    public AppointmentController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @RequestMapping(value = { "/appointment.html"})
    public String showAppointments(@RequestParam(required=false) String ownerID, @RequestParam(required=false) String petID, @RequestParam(required=false) String vetID, Map<String, Object> model) {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet objects
        // so it is simpler for Object-Xml mapping
        Vets vets = new Vets();
        vets.getVetList().addAll(this.clinicService.findVets());
        model.put("vets", vets);
        List<Owner> owners = (ArrayList<Owner>)this.clinicService.findAllOwners();
        model.put("owners", owners);
        
        List<Pet> pets = new ArrayList<Pet>();
        if(ownerID!=null) {
        	pets = this.clinicService.findOwnerById(Integer.parseInt(ownerID)).getPets();
        	model.put("ownerID", ownerID);
        }
        if(petID!=null) {
        	model.put("petID", petID);
        }
        if(vetID!=null) {
        	model.put("vetID", vetID);
        }
        
        model.put("pets", pets);
        
        return "appointments/appointment";
    }

}
