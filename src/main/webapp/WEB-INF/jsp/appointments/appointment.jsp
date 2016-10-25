<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="appointments">
    <h2>Make an appointment</h2>
    
	<form action="${fn:escapeXml(formUrl)}" id="pageForm" method="get">
	</form>
    
    <table id="select" class="table table-striped">
        <thead>
        <tr>
            <th>Select Owner</th>
            <th>Select Pet</th>
            <th>Select Vet</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                	<select id="owners" form="pageForm" name="ownerID" onchange='if(this.value != -1) { this.form.submit(); }'>
                		<option value="-1" selected disabled>Select Owner</option>
	                    <c:forEach items="${owners}" var="owner">
	                    	<option 
	                    		<c:if test="${owner.id == ownerID}">
	                    			<c:out value="selected "/>
	                    		</c:if>
	                    		value="${owner.id}">${owner.firstName}&nbsp;${owner.lastName}
	                    	</option>
	                    </c:forEach>
	                </select>
                </td>
                <td>
                	<select id="pets" form="pageForm" name="petID">
                		<option selected disabled>Select Pet</option>
	                    <c:forEach items="${pets}" var="pet">
	                    	<option 
	                    		<c:if test="${pet.id == petID}">
	                    			<c:out value="selected "/>
	                    		</c:if>
	                    		value="${pet.id}">${pet.name}
	                    	</option>
	                    </c:forEach>
	                </select>
                </td>
                <td>
                	<select name="vetID" form="pageForm" onchange='if(this.value != -1) { this.form.submit(); }'>
                		<option value="-1" selected disabled>Select Vet</option>
	                    <c:forEach items="${vets.vetList}" var="vet">
	                    	<option 
	                    		<c:if test="${vet.id == vetID}">
	                    			<c:out value="selected "/>
	                    		</c:if>
	                    		value="${vet.id}">${vet.firstName}&nbsp;${vet.lastName}
	                    	</option>
	                    </c:forEach>
	                </select>
                </td>            
            </tr>
        </tbody>
    </table>
    
	<c:if test="${vetID != null && ownerID != null}">
		<h2>Select appointment time</h2>
	    <table border="1" id="vets" class="table table-striped">
	        <thead>
	        <tr align="center">
	        	<th></th>
	            <th>9 am</th>
	            <th>10 am</th>
	            <th>11 am</th>
	            <th>12 pm</th>
				<th>1 pm</th>
				<th>2 pm</th>
				<th>3 pm</th>
				<th>4 pm</th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${dates}" var="date">
	            <tr align="center">
	                <td>
	                    <c:out value="${date.key}"/>
	                </td>
	                <c:forEach items="${date.value}" var="time">
<%-- 		            <c:out value="${time.time}"/> --%>
						<c:if test="${time != null && time.time!='owner'}">
							<td bgcolor="SpringGreen">
								<button type="submit" form="pageForm" name="appointmentTime" value="${date.key}_${time.id}_create">Book</button>
							</td>
						</c:if>
						<c:if test="${time != null && time.time=='owner'}">
							<td bgcolor="DeepSkyBlue">	
								<button type="submit" form="pageForm" name="appointmentTime" value="${date.key}_${time.id}_delete">Cancel</button>
							</td>
						</c:if>
						<c:if test="${time == null}">
							<td bgcolor="Tomato">	
								<c:out value="N/A"/>
							</td>
						</c:if>
	                </c:forEach>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
	</c:if>
	
</petclinic:layout>
