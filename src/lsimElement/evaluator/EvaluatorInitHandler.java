/*
* Copyright (c) Joan-Manuel Marques 2013. All rights reserved.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
*
* This file is part of the practical assignment of Distributed Systems course.
*
* This code is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This code is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this code.  If not, see <http://www.gnu.org/licenses/>.
*/

package lsimElement.evaluator;

import edu.uoc.dpcs.lsim.utils.LSimParameters;

import lsim.application.handler.Handler;

/**
 * @author Joan-Manuel Marques
 * December 2012
 *
 */
public class EvaluatorInitHandler implements Handler {
	
	LSimParameters values;
	
	@Override
	public Object execute(Object obj) {
		values = (LSimParameters) obj;
		
		return null;
	}
	
	public int getPercentageRequiredResults(){
		return Integer.parseInt((String)values.get("percentageRequieredResults"));
	}

	public int getNumNodes(){
		return ((Integer) values.get("numServers")).intValue();
	}
	
	public String getGroupId(){
		return ((String) values.get("groupId"));
	}
	
	public String getPhase(){
		return ((String) values.get("phase"));
	}
	
	public String getRecipeDeadTitle(){
		return ((String) values.get("recipeDeadTitle"));
	}

	public String getRecipeSurviveTitle(){
		return ((String) values.get("recipeSurviveTitle"));
	}
}