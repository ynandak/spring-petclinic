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
                	<select name="ownerID" form="pageForm" onchange='if(this.value != -1) { this.form.submit(); }'>
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
                	<select name="petID" form="pageForm">
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
    
	<c:if test="${vetID != null}">
		<h2>Select appointment time</h2>
	    <table id="vets" class="table table-striped">
	        <thead>
	        <tr>
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
	            <tr>
	                <td>
	                    <c:out value="${date}"/>
	                </td>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
	</c:if>
</petclinic:layout>
